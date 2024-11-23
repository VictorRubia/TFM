import sys
import pickle
import pandas as pd
import heartpy as hp
import numpy as np
import math
from datetime import datetime, timedelta
import json
from flask import Flask, request, jsonify
from io import StringIO
import joblib
from sklearn.pipeline import Pipeline
import pytz

app = Flask(__name__)

class PipelineWithYTransformations(Pipeline):
    def __init__(self, steps, transformer=None):
        super().__init__(steps)
        self.transformer = transformer or LabelEncoder()
        self._classes_ = None

    def fit(self, X, y=None, **fit_params):
        y_transformed = self.transformer.fit_transform(y)
        self.classes_ = self.transformer.classes_
        return super().fit(X, y_transformed, **fit_params)

    def predict(self, X):
        y_pred = super().predict(X)
        y_pred = self.transformer.inverse_transform(y_pred)
        return y_pred

    def predict_proba(self, X):
        return super().predict_proba(X)

    @property
    def classes_(self):
        return self._classes_

    @classes_.setter
    def classes_(self, value):
        self._classes_ = value

# Register the class in the main module namespace before loading the model
sys.modules['__main__'].PipelineWithYTransformations = PipelineWithYTransformations

model = joblib.load('/home/app/function/model_wesad_swell.pkl')

def get_sample_rate(timer):
    return hp.get_samplerate_mstimer(timer)

def filter_signal(signal, sample_rate):
    # Filtrar la señal sin remuestreo
    filtered_signal = hp.filter_signal(signal, [0.7, 3.5], sample_rate=sample_rate, order=3, filtertype='bandpass')
    return filtered_signal

def split_signal(filtered_signal, sample_rate, segment_duration=60):
    # Dividir la señal filtrada en segmentos
    segment_length = int(sample_rate * segment_duration)
    segments = [filtered_signal[i:i + segment_length] for i in range(0, len(filtered_signal), segment_length)]
    return segments

def extract_features_using_segmentwise(filtered_signal, sample_rate, start_time, segment_duration=60):
    data = []
    error_records = []
    
    try:
        # Use process_segmentwise for segment-wise processing
        wd, measures = hp.process_segmentwise(
            filtered_signal,
            sample_rate=sample_rate,
            segment_width=segment_duration,  # Segment duration in seconds
            segment_overlap=0,  # No overlap, adjust if needed
            high_precision=True,
            clean_rr=True
        )
        
        # Iterate through the measures for each segment
        num_segments = len(measures['bpm'])  # Assuming 'bpm' is a key in all segments
        for idx in range(num_segments):
            try:
                # Extract features for the current segment
                sdsd = measures['sdsd'][idx]
                rmssd = measures['rmssd'][idx]
                hr = measures['bpm'][idx]
                pnn50 = measures['pnn50'][idx]
                sd1 = measures['sd1'][idx]
                sd2 = measures['sd2'][idx]
                mean_rr = measures['ibi'][idx]
                median_rr = np.median(measures['ibi'])
                sdnn = measures['sdnn'][idx]
                sdnn_rmssd = sdnn / rmssd
                
                # Store the features if no NaN values
                features = [sdsd, rmssd, hr, pnn50, sd1, sd2, mean_rr, median_rr, sdnn, sdnn_rmssd]
                if not any(math.isnan(x) for x in features):
                    # Calculate record time in milliseconds
                    record_time = start_time + (idx * segment_duration * 1000)
                    
                    data.append([
                        record_time,
                        mean_rr, median_rr, sdnn, rmssd, sdsd, sdnn_rmssd, hr, pnn50, sd1, sd2,
                        measures.get('MEAN_REL_RR', [None])[idx],
                        measures.get('MEDIAN_REL_RR', [None])[idx],
                        measures.get('SDRR_REL_RR', [None])[idx],
                        measures.get('RMSSD_REL_RR', [None])[idx],
                        measures.get('SDRR_RMSSD_REL_RR', [None])[idx]
                    ])
            except Exception as e:
                # Handle errors for a specific segment
                record_time = start_time + (idx * segment_duration * 1000)
                error_records.append({
                    "date": record_time,
                    "measure": -1,
                    "error": str(e)
                })
    except Exception as e:
        # Handle errors for the entire signal processing
        error_records.append({
            "date": start_time,
            "measure": -1,
            "error": str(e)
        })
    
    return data, error_records

def predict_stress(data_frame, model):
    results = []

    madrid_tz = pytz.timezone('Europe/Madrid')  # Zona horaria de Madrid

    data_frame['DATETIME'] = pd.to_datetime(data_frame['DATETIME'], unit='ms', errors='coerce').dt.tz_localize('UTC').dt.tz_convert(madrid_tz)
    
    # Check for and handle NaT values
    data_frame = data_frame.dropna(subset=['DATETIME']).reset_index(drop=True)

    for idx in range(len(data_frame)):
        fecha = data_frame.iloc[idx]['DATETIME'].strftime("%d/%m/%Y %H:%M")
        
        # Pass the full row to the model
        row_data = data_frame.iloc[idx:idx+1]  # Keep as DataFrame slice
        prediction = model.predict(row_data)
        
        measure_value = {'low': -2, 'medium': 0, 'high': 2}.get(prediction[0], None)
        if measure_value is not None:
            results.append({"date": fecha, "measure": measure_value})
    
    return results

def handle(data):
    try:
        csv_data = data  # Aquí se asume que `data` es ya una cadena de texto.
        if not csv_data:
            return jsonify({"error": "No CSV data provided"}), 400

        # Leer los datos CSV en un DataFrame de pandas
        df = pd.read_csv(StringIO(csv_data))

        if df.empty:
            return jsonify({"error": "Empty CSV data provided"}), 400

        # Preprocesar los datos
        timer = df['timestamp'].to_numpy()
        signal = df['ppg'].to_numpy()

        # Obtener la tasa de muestreo
        sample_rate = get_sample_rate(timer)

        # Filtrar la señal
        filtered_signal = filter_signal(signal, sample_rate)

        # Extraer características
        start_time = timer[0]
        data_features, error_records = extract_features_using_segmentwise(filtered_signal, sample_rate, start_time)

        # Preparar el DataFrame para la predicción
        header = ['DATETIME', 'MEAN_RR', 'MEDIAN_RR', 'SDRR', 'RMSSD', 'SDSD', 'SDRR_RMSSD', 'HR', 'pNN50', 'SD1', 'SD2',
                  'MEAN_REL_RR', 'MEDIAN_REL_RR', 'SDRR_REL_RR', 'RMSSD_REL_RR', 'SDRR_RMSSD_REL_RR']
        data_frame = pd.DataFrame(data_features, columns=header)
        data_frame = data_frame.rename(columns={
            'MEAN_REL_RR': 'mean_rel_rr',
            'MEDIAN_REL_RR': 'median_rel_rr',
            'SDRR_REL_RR': 'sdrr_rel_rr',
            'RMSSD_REL_RR': 'rmssd_rel_rr',
            'SDRR_RMSSD_REL_RR': 'sdrr_rmssd_rel_rr',
            'MEAN_RR': 'mean_rr',
            'MEDIAN_RR': 'median_rr',
            'SDRR': 'sdnn',
            'RMSSD': 'rmssd',
            'SDSD': 'sdsd',
            'SDRR_RMSSD': 'sdnn_rmssd',
            'HR': 'hr',
            'pNN50': 'pnn50',
            'SD1': 'sd1',
            'SD2': 'sd2'
        })

        if data_frame.empty:
            return jsonify({"error": "No valid data for prediction"}), 500

        # Predecir los niveles de estrés
        stress_results = predict_stress(data_frame, model)

        # Combinar los resultados de estrés con los registros de error
        all_results = stress_results + error_records
        # Ordenar los resultados por fecha
        sorted_results = sorted(all_results, key=lambda x: datetime.strptime(x['date'], '%d/%m/%Y %H:%M'))

        # Devolver los resultados en formato JSON
        return jsonify(sorted_results)
    except Exception as e:
        return jsonify({"error": str(e)}), 500
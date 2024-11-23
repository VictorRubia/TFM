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
from scipy.signal import resample, butter, filtfilt

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

model = joblib.load('/home/app/function/modelo_chana.pkl')


def get_sample_rate(timer):
    return hp.get_samplerate_mstimer(timer)

# Definir función para crear y aplicar un filtro pasa-bajos de Butterworth
def butter_lowpass_filter(data, cutoff, fs, order=4):
    nyquist = 0.5 * fs
    normal_cutoff = cutoff / nyquist
    b, a = butter(order, normal_cutoff, btype='low', analog=False)
    y = filtfilt(b, a, data)
    return y


def filter_signal(data, sample_rate):
    # Parámetros del filtro Butterworth
    cutoff_frequency_butter = 4.0  # Frecuencia de corte en Hz
    sampling_rate_butter = 20  # Frecuencia de muestreo en Hz

    # Parámetros del filtro HeartPy
    cutoff_frequency_hp = [0.75, 3.5]  # Frecuencias cardíacas típicas (pasa banda)
    filter_order_hp = 3
    filter_type_hp = 'bandpass'  # Tipo de filtro
    target_sample_rate = 100  # Frecuencia de muestreo deseada en Hz

    # Ordenar los datos por timestamp para asegurar consistencia
    data = data.sort_values(by='timestamp')

    # **Eliminar valores atípicos en la señal PPG**
    # Calcular el rango intercuartílico (IQR)
    Q1 = data['ppg'].quantile(0.25)
    Q3 = data['ppg'].quantile(0.75)
    IQR = Q3 - Q1

    # Definir límites para los valores atípicos
    lower_bound = Q1 - 1.5 * IQR
    upper_bound = Q3 + 1.5 * IQR

    # Limitar los valores de PPG dentro de los límites definidos
    data['ppg_clipped'] = data['ppg'].clip(lower=lower_bound, upper=upper_bound)

    # Aplicar filtro pasa-bajos de Butterworth a la señal PPG sin valores atípicos
    data['ppg_butter'] = butter_lowpass_filter(
        data['ppg_clipped'], 
        cutoff_frequency_butter, 
        sampling_rate_butter
    )

    # Calcular la frecuencia de muestreo original usando get_samplerate_mstimer
    def get_samplerate_mstimer(timerdata):
        sample_rate = ((len(timerdata) / (timerdata[-1] - timerdata[0])) * 1000)
        return sample_rate

    timestamps = data['timestamp'].values
    sample_rate = get_samplerate_mstimer(timestamps)
    print(f"Frecuencia de muestreo original: {sample_rate:.3f} Hz")

    # Aplicar filtro de HeartPy a la señal filtrada por Butterworth
    filtered_ppg_hp = hp.filter_signal(
        data['ppg_butter'].values, 
        cutoff=cutoff_frequency_hp, 
        sample_rate=sample_rate, 
        order=filter_order_hp, 
        filtertype=filter_type_hp
    )

    # Generar un nuevo índice de tiempo para el remuestreo
    freq = pd.Timedelta(seconds=1 / target_sample_rate)
    new_time_index = pd.date_range(
        start=pd.Timestamp(timestamps[0], unit='ms'),
        end=pd.Timestamp(timestamps[-1], unit='ms'),
        freq=freq
    )

    # Ajustar la longitud de la señal resampleada
    resampled_length = len(new_time_index)

    # Resamplear la señal filtrada para que coincida con la nueva frecuencia de muestreo
    resampled_ppg = resample(filtered_ppg_hp, resampled_length)

    # Crear un nuevo DataFrame para los datos resampleados
    resampled_data = pd.DataFrame({
        'timestamp': new_time_index,
        'ppg_filtered': resampled_ppg
    })

    # Verificar la nueva frecuencia de muestreo
    elapsed = resampled_data['timestamp'].diff().dt.total_seconds().dropna()
    new_sample_rate = 1 / elapsed.mean()
    print(f"Frecuencia de muestreo tras el remuestreo: {new_sample_rate:.3f} Hz")

    return resampled_data, new_sample_rate

def analyze_hrv(signal, sample_rate, interp_threshold=None):
    """
    Analiza la señal PPG y calcula métricas HRV usando HeartPy.

    Parámetros:
        signal: array-like
            Señal PPG.
        sample_rate: float
            Frecuencia de muestreo en Hz.
        interp_threshold: float, opcional
            Umbral para interpolación de clipping, si es necesario.

    Retorna:
        metrics: dict
            Diccionario con métricas HRV calculadas.
    """
    # Opciones de interpolación si se proporciona un umbral
    if interp_threshold:
        wd, m = hp.process(
            signal, 
            sample_rate=sample_rate, 
            interp_clipping=True, 
            interp_threshold=interp_threshold,
            calc_freq=True,  # Calcular métricas en dominio de frecuencia
            high_precision=True,  # Modo de alta precisión
            high_precision_fs=1000.0  # Muestreo virtual a 1000 Hz para precisión
        )
    else:
        wd, m = hp.process(
            signal, 
            sample_rate=sample_rate, 
            calc_freq=True, 
            high_precision=True, 
            high_precision_fs=1000.0
        )

    # Calcular métricas adicionales
    metrics = {
        'MEAN_RR': m['ibi'],
        'MEDIAN_RR': np.median(m['ibi']),
        'SDRR': m['sdnn'],
        'RMSSD': m['rmssd'],
        'SDSD': m['sdsd'],
        'SDRR_RMSSD': m['sdnn'] / m['rmssd'] if m['rmssd'] > 0 else np.nan,
        'HR': m['bpm'],
        'PNN50': m['pnn50'],
        'SD1': m['sd1'],
        'SD2': m['sd2'],
        'MEAN_REL_RR': m['MEAN_REL_RR'],
        'MEDIAN_REL_RR': m['MEDIAN_REL_RR'],
        'SDRR_REL_RR': m['SDRR_REL_RR'],
        'RMSSD_REL_RR': m['RMSSD_REL_RR'],
        'SDRR_RMSSD_REL_RR': m['SDRR_RMSSD_REL_RR'],
    }

    return metrics, wd

def predict_stress(data_frame, model):
    # Obtener las clases predichas
    predicted_classes = model.predict(data_frame)
    
    # Obtener las probabilidades para cada clase
    probabilities = model.predict_proba(data_frame)
    
    # Seleccionar las probabilidades de pertenecer a la clase 1
    class_1_probabilities = probabilities[:, 1]
    
    # Devolver ambos resultados como un diccionario o una lista de tuplas
    results = {
        "prediccion": int(predicted_classes[0]),
        "probabilidad": float(class_1_probabilities[0])
    }
    
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

        # Obtener la tasa de muestreo
        sample_rate = get_sample_rate(timer)

        # Filtrar la señal
        filtered_signal, new_sample_rate = filter_signal(df, sample_rate)

        # Extraer características
        metrics, wd = analyze_hrv(filtered_signal['ppg_filtered'], new_sample_rate, interp_threshold=975)

        # Crear el DataFrame a partir del diccionario
        data_frame = pd.DataFrame([metrics])  # Convertir a un DataFrame de una fila

        # Renombrar las columnas
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
            'PNN50': 'pnn50',
            'SD1': 'sd1',
            'SD2': 'sd2'
        })

        if data_frame.empty:
            return jsonify({"error": "No valid data for prediction"}), 500
        
        # Predecir los niveles de estrés
        stress_results = predict_stress(data_frame, model)

        # Devolver los resultados en formato JSON
        return jsonify(stress_results)
    except Exception as e:
        return jsonify({"error": str(e)}), 500
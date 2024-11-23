import sys
import math
import pandas as pd
import json
import urllib.request
import numpy as np
import heartpy as hp
from datetime import datetime, timedelta
import pickle
from socket import timeout
from sklearn.pipeline import Pipeline
import joblib

def load_model(model_path):
    with open(model_path, 'rb') as handle:
        model = pickle.load(handle)
    return model

def fetch_data(activity_id, timeout_duration=90):
    try:
        url = f"http://localhost:3000/activities/{activity_id}/ppg_measures/"
        response = urllib.request.urlopen(url, timeout=timeout_duration)
        data_frame = pd.read_json(response)
        return data_frame
    except timeout:
        print('socket timed out')
        sys.exit(1)

def preprocess_data(df):
    # Ensure timestamp column is in the correct format
    df['timestamp'] = pd.to_datetime(df['timestamp'])  # Convert to datetime if not already
    df['timestamp'] = df['timestamp'].dt.strftime('%Y-%m-%dT%H:%M:%S.%f')
    return df

def get_sample_rate(timer):
    return hp.get_samplerate_datetime(timer, timeformat='%Y-%m-%dT%H:%M:%S.%f')

def filter_signal(signal, sample_rate):
    # Filter signal without resampling
    filtered_signal = hp.filter_signal(signal, [0.7, 3.5], sample_rate=sample_rate, order=3, filtertype='bandpass')
    return filtered_signal

def split_signal(filtered_signal, sample_rate, segment_duration=40):
    # Split the filtered signal into segments
    segment_length = int(sample_rate * segment_duration)
    segments = [filtered_signal[i:i + segment_length] for i in range(0, len(filtered_signal), segment_length)]
    return segments

def extract_features(segments, sample_rate, start_time):
    data = []
    error_records = []
    for idx, segment in enumerate(segments):
        try:
            wd, measures = hp.process(segment, sample_rate=sample_rate, report_time=False, high_precision=True, clean_rr=True)
            sdsd = measures['sdsd']
            rmssd = measures['rmssd']
            hr = measures['bpm']
            pnn50 = measures['pnn50']
            sd1 = measures['sd1']
            sd2 = measures['sd2']
            mean_rr = measures['ibi']
            median_rr = np.median(measures['ibi'])
            sdnn = measures['sdnn']
            sdnn_rmssd = sdnn / rmssd

            features = [sdsd, rmssd, hr, pnn50, sd1, sd2, mean_rr, median_rr, sdnn, sdnn_rmssd]
            if not any(math.isnan(x) for x in features):
                record_time = datetime.strptime(start_time, "%Y-%m-%dT%H:%M:%S.%f") + timedelta(minutes=idx)
                data.append([
                    record_time,
                    mean_rr, median_rr, sdnn, rmssd, sdsd, sdnn_rmssd, hr, pnn50, sd1, sd2,
                    measures['MEAN_REL_RR'], measures['MEDIAN_REL_RR'], measures['SDRR_REL_RR'],
                    measures['RMSSD_REL_RR'], measures['SDRR_RMSSD_REL_RR']
                ])
        except Exception:
            record_time = datetime.strptime(start_time, "%Y-%m-%dT%H:%M:%S.%f") + timedelta(minutes=idx)
            error_records.append({
                "date": record_time.strftime("%d/%m/%Y %H:%M"),
                "measure": -1
            })
    return data, error_records

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

def predict_stress(data_frame, model):
    results = []
    for idx in range(len(data_frame)):
        fecha = data_frame.iloc[idx]['DATETIME'].strftime("%d/%m/%Y %H:%M")
        features = data_frame.drop(columns="DATETIME").iloc[idx].values.reshape(1, -1)
        prediction = model.predict(features)
#         measure_value = {'no stress': -2, 'interruption': 0, 'time pressure': 2}.get(prediction[0], None)
        measure_value = {'low': -2, 'medium': 0, 'high': 2}.get(prediction[0], None)
        if measure_value is not None:
            results.append({"date": fecha, "measure": measure_value})
    return results

def main():
    # Parse command line arguments
    if len(sys.argv) < 2:
        print("Usage: script.py <activity_id>")
        sys.exit(1)
    activity_id = sys.argv[1]

    # Load model
#     model_path = '/tfg/lib/python/model.pickle'
#     model = load_model(model_path)
    model = joblib.load('/tfg/lib/python/model_wesad_swell.pkl')

    # Fetch data
    df = fetch_data(activity_id)

    # Preprocess data
    df = preprocess_data(df)
    timer = df['timestamp']
    signal = df['ppg']

    # Get sample rate
    sample_rate = get_sample_rate(timer)

    # Filter signal
    filtered_signal = filter_signal(signal, sample_rate)

    # Split signal into segments
    segments = split_signal(filtered_signal, sample_rate)

    # Extract features
    start_time = timer.iloc[0]
    data, error_records = extract_features(segments, sample_rate, start_time)

    # Prepare DataFrame for prediction
    header = ['DATETIME', 'MEAN_RR', 'MEDIAN_RR', 'SDRR', 'RMSSD', 'SDSD', 'SDRR_RMSSD', 'HR', 'pNN50', 'SD1', 'SD2',
              'MEAN_REL_RR', 'MEDIAN_REL_RR', 'SDRR_REL_RR', 'RMSSD_REL_RR', 'SDRR_RMSSD_REL_RR']
    data_frame = pd.DataFrame(data, columns=header)

    # Predict stress levels
    stress_results = predict_stress(data_frame, model)

    # Combine stress results with error records
    all_results = stress_results + error_records
    # Sort results by date
    sorted_results = sorted(all_results, key=lambda x: datetime.strptime(x['date'], '%d/%m/%Y %H:%M'))

    # Output results in JSON format
    print(json.dumps(sorted_results))

if __name__ == "__main__":
    main()
import csv
import math
import pandas as pd
import json
from socket import timeout
import sys
sys.path.insert(1, '/tfg/lib/python/')

import numpy as np
import heartpy as hp
import pandas as pd
from datetime import datetime, timedelta
import matplotlib.pyplot as plt
import urllib.request
from scipy.signal import resample
import pickle

x = []

def procesar(data):

    with open('/tfg/lib/python/model.pickle', 'rb') as handle:
        b = pickle.load(handle)

    for i in range(0,len(data)):
        fecha = data.iloc[i].DATETIME.strftime("%d/%m/%Y %H:%M")
        dato_estres = b.predict([data.drop(columns="DATETIME").iloc[i]])
        if(dato_estres[0] == 'no stress'):
            x.append({
                "date": fecha,
                "measure": -2
            })
        if(dato_estres[0] == 'interruption'):
            x.append({
                "date": fecha,
                "measure": 0
            })
        if(dato_estres[0] == 'time pressure'):
            x.append({
                "date": fecha,
                "measure": 2
            })

    result_ordered = sorted(x, key=lambda y: datetime.strptime(y['date'], '%d/%m/%Y %H:%M'))
    print(json.dumps(result_ordered))

idActividad = sys.argv[1]

try:
    file = urllib.request.urlopen("http://localhost:3000/activities/"+ idActividad +"/ppg_measures/", timeout = 90)
except timeout:
    print('socket timed out')

df = pd.read_json(file)

timer = df['timer']
signal = df['ppg']

sample_rate = hp.get_samplerate_datetime(timer, timeformat='%d/%m/%Y %H:%M:%S.%f')

# Let's run it through a standard butterworth bandpass implementation to remove everything < 0.8 and > 3.5 Hz.
filtered = hp.filter_signal(signal, [0.7, 3.5], sample_rate=sample_rate,
                            order=3, filtertype='bandpass')

resampled = resample(filtered, len(filtered) * 10)

# #don't forget to compute the new sampling rate
new_sample_rate = sample_rate * 10
# #run HeartPy over a few segments, fingers crossed, and plot results of each

numero_elementos = (int)(new_sample_rate * 60)

a_splited = [resampled[x:x + numero_elementos] for x in range(0, len(resampled), numero_elementos)]

resample_partio = np.array_split(resampled, numero_elementos)

data = []
contador = 0

for s in a_splited:
    try:
        # Process the data with the updated process function
        wd, m = hp.process(s, sample_rate=new_sample_rate, report_time=False,
                           high_precision=True, clean_rr=True, calc_freq=True)
        
        # Extract the measures from the measures dictionary 'm'
        # Time-Domain Measures
        mean_rr = m['mean_rr']
        median_rr = m['median_rr']
        sdnn = m['sdnn']  # Standard deviation of NN intervals (equivalent to SDRR)
        rmssd = m['rmssd']
        sdsd = m['sdsd']
        sdnn_rmssd = sdnn / rmssd if rmssd != 0 else np.nan
        hr = m['bpm']
        pnn25 = m['pnn25']
        pnn50 = m['pnn50']
        sd1 = m['sd1']
        sd2 = m['sd2']
        skew_rr = m['SKEW']
        kurt_rr = m['KURT']
        hr_sqrt = np.sqrt(hr)

        # Relative RR Measures
        mean_rel_rr = m['MEAN_REL_RR']
        median_rel_rr = m['MEDIAN_REL_RR']
        sdrr_rel_rr = m['SDRR_REL_RR']
        rmssd_rel_rr = m['RMSSD_REL_RR']
        sdsd_rel_rr = m['SDSD_REL_RR']
        sdrr_rmssd_rel_rr = m['SDRR_RMSSD_REL_RR']
        skew_rel_rr = m['SKEW_REL_RR']
        kurt_rel_rr = m['KURT_REL_RR']
        mean_rr_mean_mean_rel_rr = np.mean([mean_rr, mean_rel_rr])

        # Frequency-Domain Measures
        vlf = m['vlf']
        vlf_pct = m['vlf_pct']
        lf = m['lf']
        lf_pct = m['lf_pct']
        lf_nu = m['lf_nu']
        hf = m['hf']
        hf_pct = m['hf_pct']
        hf_nu = m['hf_nu']
        tp = m['total_power']
        lf_hf = m['lf/hf']
        hf_lf = m['hf/lf']
        hf_vlf = hf / vlf if vlf != 0 else np.nan
        sd2_lf = sd2 * lf
        hr_lf = hr * lf
        hr_hf = hr * hf

        # Nonlinear Measures
        sampen = m['sampen']
        higuci = m['higuci']

        # Transformed Measures
        mean_rr_log = m['MEAN_RR_LOG']
        mean_rr_sqrt = m['MEAN_RR_SQRT']
        tp_sqrt = m['TP_SQRT']
        tp_log = m['TP_LOG']
        vlf_log = m['VLF_LOG']
        lf_log = m['LF_LOG']
        hf_log = m['HF_LOG']
        lf_hf_log = m['LF_HF_LOG']
        rmssd_log = m['RMSSD_LOG']
        sdrr_rmssd_log = m['SDRR_RMSSD_LOG']
        pnn25_log = m['pNN25_LOG']
        pnn50_log = m['pNN50_LOG']
        sd1_log = m['SD1_LOG']
        median_rel_rr_log = m['MEDIAN_REL_RR_LOG']
        rmssd_rel_rr_log = m['RMSSD_REL_RR_LOG']
        sdsd_rel_rr_log = m['SDSD_REL_RR_LOG']
        kurt_square = m['KURT_SQUARE']
        kurt_yeo_johnson = m['KURT_YEO_JONSON']
        skew_yeo_johnson = m['SKEW_YEO_JONSON']
        mean_rel_rr_yeo_johnson = m['MEAN_REL_RR_YEO_JONSON']
        skew_rel_rr_yeo_johnson = m['SKEW_REL_RR_YEO_JONSON']
        lf_boxcox = m['LF_BOXCOX']
        hf_boxcox = m['HF_BOXCOX']
        sd1_boxcox = m['SD1_BOXCOX']

        # List of measures to check for NaN values
        measures_to_check = [
            mean_rr, median_rr, sdnn, rmssd, sdsd, sdnn_rmssd, hr, pnn25, pnn50, sd1, sd2,
            skew_rr, kurt_rr, mean_rel_rr, median_rel_rr, sdrr_rel_rr, rmssd_rel_rr, sdsd_rel_rr,
            sdrr_rmssd_rel_rr, skew_rel_rr, kurt_rel_rr, vlf, vlf_pct, lf, lf_pct, lf_nu,
            hf, hf_pct, hf_nu, tp, lf_hf, hf_lf, sampen, higuci, mean_rr_log, mean_rr_sqrt,
            tp_sqrt, tp_log, vlf_log, lf_log, hf_log, lf_hf_log, rmssd_log, sdrr_rmssd_log,
            pnn25_log, pnn50_log, sd1_log, median_rel_rr_log, rmssd_rel_rr_log, sdsd_rel_rr_log,
            kurt_square, kurt_yeo_johnson, skew_yeo_johnson, mean_rel_rr_yeo_johnson,
            skew_rel_rr_yeo_johnson, lf_boxcox, hf_boxcox, sd1_boxcox, hr_sqrt,
            mean_rr_mean_mean_rel_rr, sd2_lf, hr_lf, hr_hf, hf_vlf
        ]

        # Check if any of the measures are NaN
        if not any(math.isnan(value) for value in measures_to_check):
            # Append the data to the list 'data' if all measures are valid
            data.append([
                datetime.strptime(timer[0], "%d/%m/%Y %H:%M:%S.%f") + timedelta(minutes=1 * contador),
                mean_rr, median_rr, sdnn, rmssd, sdsd, sdnn_rmssd, hr, pnn25, pnn50, sd1, sd2,
                skew_rr, kurt_rr, mean_rel_rr, median_rel_rr, sdrr_rel_rr, rmssd_rel_rr,
                sdsd_rel_rr, sdrr_rmssd_rel_rr, skew_rel_rr, kurt_rel_rr, vlf, vlf_pct, lf,
                lf_pct, lf_nu, hf, hf_pct, hf_nu, tp, lf_hf, hf_lf, sampen, higuci, mean_rr_log,
                mean_rr_sqrt, tp_sqrt, tp_log, vlf_log, lf_log, hf_log, lf_hf_log, rmssd_log,
                sdrr_rmssd_log, pnn25_log, pnn50_log, sd1_log, median_rel_rr_log, rmssd_rel_rr_log,
                sdsd_rel_rr_log, kurt_square, kurt_yeo_johnson, skew_yeo_johnson,
                mean_rel_rr_yeo_johnson, skew_rel_rr_yeo_johnson, lf_boxcox, hf_boxcox, sd1_boxcox,
                hr_sqrt, mean_rr_mean_mean_rel_rr, sd2_lf, hr_lf, hr_hf, hf_vlf
            ])
        else:
            # Handle the case where measures are NaN
            x.append({
                "date": (datetime.strptime(timer[0], "%d/%m/%Y %H:%M:%S.%f") + timedelta(minutes=1 * contador)).strftime("%d/%m/%Y %H:%M"),
                "measure": -1
            })
        
        # Increment the counter
        contador += 1

    except Exception as e:
        # Handle any exceptions that occur during processing
        x.append({
            "date": (datetime.strptime(timer[0], "%d/%m/%Y %H:%M:%S.%f") + timedelta(minutes=1 * contador)).strftime("%d/%m/%Y %H:%M"),
            "measure": -1,
            "error": str(e)
        })
        contador += 1

# Define the header for the DataFrame
header = [
    'DATETIME', 'MEAN_RR', 'MEDIAN_RR', 'SDRR', 'RMSSD', 'SDSD', 'SDRR_RMSSD', 'HR', 'pNN25', 'pNN50',
    'SD1', 'SD2', 'SKEW', 'KURT', 'MEAN_REL_RR', 'MEDIAN_REL_RR', 'SDRR_REL_RR', 'RMSSD_REL_RR',
    'SDSD_REL_RR', 'SDRR_RMSSD_REL_RR', 'SKEW_REL_RR', 'KURT_REL_RR', 'VLF', 'VLF_PCT', 'LF',
    'LF_PCT', 'LF_NU', 'HF', 'HF_PCT', 'HF_NU', 'TP', 'LF_HF', 'HF_LF', 'sampen', 'higuci',
    'MEAN_RR_LOG', 'MEAN_RR_SQRT', 'TP_SQRT', 'TP_LOG', 'VLF_LOG', 'LF_LOG', 'HF_LOG', 'LF_HF_LOG',
    'RMSSD_LOG', 'SDRR_RMSSD_LOG', 'pNN25_LOG', 'pNN50_LOG', 'SD1_LOG', 'MEDIAN_REL_RR_LOG',
    'RMSSD_REL_RR_LOG', 'SDSD_REL_RR_LOG', 'KURT_SQUARE', 'KURT_YEO_JONSON', 'SKEW_YEO_JONSON',
    'MEAN_REL_RR_YEO_JONSON', 'SKEW_REL_RR_YEO_JONSON', 'LF_BOXCOX', 'HF_BOXCOX', 'SD1_BOXCOX',
    'HR_SQRT', 'MEAN_RR_MEAN_MEAN_REL_RR', 'SD2_LF', 'HR_LF', 'HR_HF', 'HF_VLF'
]

# Create the DataFrame
datos_a_procesar = pd.DataFrame(data, columns=header)

procesar(datos_a_procesar)
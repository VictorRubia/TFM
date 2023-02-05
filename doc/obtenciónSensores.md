# Obtención de los sensores del SmartWatch

Tras analizar los escasos artículos que analizan las formas de medir el estrés en personas mediante sensores de smartwatch, he podido estudiar que el sensor PPG (Photoplethysmogram - Fotopletismografía) toma una gran relevancia.

Para la obtención de las medidas de los sensores del Smartwatch, entre los que se encuentra el sensor de PPG, he investigado el software que posee el reloj. Tras identificar que se trata de WearOS by Google, he podido estudiar las guías de desarrollo para este sistema. Es un sistema que trabaja de igual forma que lo hace Android, por lo que usaré Kotlin como lenguaje para programarlo.

He creado una aplicación para el reloj, que solicita permisos para acceder a los sensores. Tras numerosos intentos de obtener datos del sensor de PPG, finalmente he podido estudiar que se obtiene en dos canales diferentes. Tras hacer varias mediciones y obtener datos numéricos en formato .CSV a través de la memoria del reloj, los he analizado en Excel. He podido representarlos en un gráfico, sin embargo, estos carecían de la forma de una señal PPG típica. Por esto, he procedido a normalizar los datos con valores entre 0 y 1 y tras representarlos en un gráfico, he podido ver que toman una forma más similar a una señal PPG.

![](/doc/img/PPG_Graph.png)

Sin embargo, tras la lectura de diversos artículos y tras sospechar que estas ondas que he representado son bastante irregulares, se constata que la lectura del sensor PPG debe hacerse en condiciones de mínimo movimiento. Esto hace que el estudio que queremos hacer sea inviable para este trabajo de fin de grado, pues, aun existiendo métodos de limpieza de ruido en señales de PPG, son bastante experimentales y complejos de realizar.

## Reunión 4/3/2022

Las tutoras del TFG y yo hemos tenido una reunión, en la que también ha asistido un compañero de departamento que investiga los sensores de los dispositivos para temas relacionados con la salud. Él me ayuda a indicarme qué camino debo seguir, siendo este el de buscar algoritmos de limpieza y procesado de la señal en Python, pues hay muchos hechos ya y no sería necesario, pues, hacer el propio algoritmo yo.

## Tras la reunión

Investigo acerca de librerías o módulos para procesar señales PPG. Me encuentro con estos dos artículos de investigación [1](https://www.researchgate.net/publication/328654252_Analysing_Noisy_Driver_Physiology_Real-Time_Using_Off-the-Shelf_Sensors_Heart_Rate_Analysis_Software_from_the_Taking_the_Fast_Lane_Project) [2](https://www.researchgate.net/publication/325967542_Heart_Rate_Analysis_for_Human_Factors_Development_and_Validation_of_an_Open_Source_Toolkit_for_Noisy_Naturalistic_Heart_Rate_Data).

Estos dos artículos me parecen bastante interesantes teniendo también en cuenta otros que he leído. Esto me lleva a descubrir un módulo de Python llamado [HeartPy](https://github.com/paulvangentcom/heartrate_analysis_python), el cual es capaz de procesar señales PPG obtenidas mediante smartwatch y obtener información bastante relevante, como

- Pulsaciones por minuto (bpm)
- Intervalo de tiempo entre pulsaciones (ibi)
- La desviación típica de los intervalos R-R normales (sdnn)
- la desviación típica de las diferencias sucesivas entre NNs adyacentes (sdsd)
- La raíz cuadrada de la media de los cuadrados de las diferencias sucesivas entre NNs adyacentes (rmssd)
- La proporción de NN20 dividida por el número total de NN (pnn20)
- La proporción de NN50 dividida por el número total de NN (pnn50)
- Desviación absoluta mediana de los intervalos RR (hr_mad)
- Ratio de respiraciones en Hz (breathingrate)

Estos datos se pueden obtener para un serie de mediciones acotadas o para el conjunto completo. Por esto, concluimos que este módulo será el elegido para continuar la investigación e interpretar la señal PPG.
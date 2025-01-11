# Repositorio para Trabajo de Fin de Máster

## Explicación del proyecto

La explicación del proyecto se puede encontrar en el siguiente [enlace 📔](doc/explicacionProyecto.md)

## Herramientas y lenguajes a utilizar

Las herramientas, lenguajes y tecnologías de desarrollo a utilizar se encuentran en el siguiente [enlace 🛠️](doc/herramientasLenguajes.md)

## Changelog

1. Actualizar Ruby y Ruby On Rails, junto a todas las Gemas que se habían quedado desactualizadas.
2. Comprobar que tras la actualización todo seguía funcionando.
3. Añadir la posibilidad de tener BBDD distribuidas de modo que los usuarios ahora se guardan en una diferente a la del resto de datos, de modo que aumentamos así la seguridad.
4. Ahora no se guarda la contraseña de los usuarios en texto plano, sino que se guarda encriptada.
5. Ahora se pueden crear etiquetas de actividades en el sistema, estas tienen una imagen que la representa y se ha utilizado Uppy para gestionar la subida de las imágenes a Active Record y AWS S3, personalizandose el formulario para hacer drag and drop y posibilitar el recortar y editar la foto que se suba.También tienen un texto que las describe y la opción de personalizar el texto que aparecerá en el reloj.
6. Ahora se pueden crear tipos de actividad en el sistema, estas tienen también una imagen que las representa y se ha utilizado Uppy para gestionar la subida de las imágenes a Active Record y AWS S3, personalizandose el formulario para hacer drag and drop y posibilitar el recortar y editar la foto que se suba. También tienen un texto que las describe y la opción de personalizar el texto que aparecerá en el reloj.
7. Se añade la posibilidad de asignar actividades a los usuarios registrados en el sistema, mediante un sistema de checks.
8. Se añade la posibilidad de asignar etiquetas a las actividades, mediante un sistema de drag and drop de las etiquetas, pudiendo ordenarlas a gusto del terapeuta.
9. En cuanto a la visualización del listado de etiquetas del sistema, se ha diseñado de tal forma que aparezcan tal y como se mostrarán en el reloj, con la imagen y el texto personalizado.
10. Se añade la posibilidad de buscar en las diferentes pantallas de configuración de usuarios, etiquetas y tipos de actividad.
11. Se modifica para obtener las actividades asignadas a un usuario de modo que cuando el reloj obtenga la API Key del usuario logueado en el teléfono obtenga sus actividades asignadas.
12. Se ha añadido la posibilidad de registrar nuevos señales de nuevos tipos de sensor del reloj Android Wear, como el acelerómetro, gps, ppg, significant_mov y pasos.
13. Se ha cambiado la visualización de las actividades de los usuarios para adaptarlo a la nueva forma de tener actividades dinámicas.
14. Se ha cambiado la visualización del gráfico de estrés, pudiéndose ahora visualizar las etiquetas registradas sobre los mismos puntos del gráfico (gráfico realizado gracias a graph.js).
15. Se ha añadido un nuevo apartado dentro de la actividad para visualizar en un mapa la ruta que ha seguido el usuario con el GPS.
16. Se ha dado soporte a nivel de APi para que el reloj pueda enviar y consultar toda la nueva lógica de datos.
17. Se ha implementado el logging a nivel de aplicación web para poder ver las interacciones que se realizan en la aplicación a través de un stack ELK.
18. Se ha añadido la posibilidad de que se pueda elaborar un perfil de probabilidad de padecer estrés para un usuario en base a sus actividades.
19. Se ha desacoplado la forma en la que se calculaba el nivel de estrés para una actividad, antes se lanzaba un proceso python desde la aplicación web, ahora se ha lanza como un microservicio (Function as a Service) mediante Kubernetes y OpenFaaS.
# Ejercicio técnico Brubank - Android

## Nicolás Lambertucci

## Resumen
Se me solicitó realizar el desarrollo de una aplicación que le permita al usuario final ver una lista de peliculas, con su correspondiente detalle,
realizar búsquedas de películas puntuales y suscribirse/agregar a favoritos las que fuera de su agrado.

## Solución 
Se realizó dicha aplicacion siguiendo el patrón de arquitectura MVVM (Model - View - ViewModel) el cual brinda una mejor separación de
responsabilidades, facilita los tests y permite una mejor reutilización de componentes en caso de ser necesario. 

### Funcionalidades implementadas
- Listado de peliclas más populares
- Detalle de película seleccionada
- Obtención del color dominante del poster para background color del detalle
- Posibilidad de agregar/eliminar pelicula de favoritas
- Búsqueda de pelíluca con listado de resultados
- Animación para que al realizar scroll de pantalla de detalle la imagen principal se achique
- Paginación, implementado en un branch aparte, [PAGINACION](https://github.com/Nicolamber/brubankExcercise/pull/1), ya que por motivos de tiempo no se pudo performar mas la app
- [Pruebas unitarias](https://github.com/Nicolamber/brubankExcercise/tree/main/app/src/test/java/com/nlambertucci/brubank) realizadas sobre los View models y los casos de uso

- ## Video de prueba de la aplicación
  |Pixel 4 API 34 | Pixel 4 API 34|
  |---------------------------------------------------|----------------------------------|
  | ![Buscar y eliminar de favs](https://github.com/Nicolamber/brubankExcercise/assets/33043378/04ab4898-a2ab-42a5-8cab-d4de4d63dac8) | ![ListaYdetalle (1)](https://github.com/Nicolamber/brubankExcercise/assets/33043378/b0b343fd-c046-4b2b-8090-cdc636ea4852)|


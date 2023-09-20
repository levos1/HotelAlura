# HotelAlura

Este código es un sistema de registro de reservas y huespedes de un hotel ficticio para el challenge de hotel_alura de Oracle Next Education



Para poder usar este proyecto en su equipo deben de crear una base de datos sql llamada "hotel_alura" con la ayuda del archivo schema.sql que crea las 2 tablas ( reservas y huespedes) además de cambiar los valores usados para la conexión en la línea 9 
del archivo DBConection en la carpeta "\src\factorys" para que coincidan con sus credenciales.

1: Si se ejecuta el programa desde la clase Menu principal habrá un login donde los valores por defecto son : usuario ->  admin , contraseña-> admin
2: Al logear pueden nagevar las vistas de reserva o busqueda
3: En reserva pueden crear una nueva reserva que se guarda en la base de datos junto al huesped de dicha reserva
4: En busqueda pueden buscar por id o por apellido las reservas y huespedes asociadas a estos valores
5: ademas, pueden clickear una fila de entre los resultaods de busqueda y actualizar o eliminar dicha fila. Si se selecciona una reseva.

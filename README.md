# Documentación

Los endpoint de los servicios son:

- Creación de usuario
> URI: /users

> Metodo: POST

> Request: 
> >{
		"name": string,
		"username": string,
		"email": string,
		"phone": string
	}
- Lista de todos los usuarios registrados
> URI: /users
> Metodo: GET
- Consulta de usuario por su email
> URI: /users/{email}
> Metodo: GET

- Servicio de llamada externa
> URI: /externals/?param={VALUE}
> Metodo: POST

El archivo **ionix-script.sql** contiene el control de cambio de base de datos

Quede pendiente con los casos de pruebas 

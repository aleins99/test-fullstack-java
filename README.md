<h1 align="center">Bienvenido, futuro compañero!</h1>

¡Saludos de [e-tica!](https://www.linkedin.com/company/eticapy/) Si estás leyendo esto, significa que queremos conocer tus habilidades.

### Acerca del Proyecto

Este proyecto es mantenido por e-tica, una empresa dedicada a crear soluciones tecnológicas innovadoras. Nuestro objetivo con este proyecto es desarrollar una API Rest simple utilizando Spring Boot, así como integrarla con desafíos frontend. A continuación, encontrarás algunos desafíos interesantes para trabajar en este proyecto.

### Instruccion a tener en cuenta

- Para el backend es requerimiento utilizar el lenguaje **Java**, junto con el framework **Springboot**.
- Utilizar los HTTP METHODS adecuados para cada servicio.
- Para el frontend el framework es a elección
- Para la persistencia se recomienda utilizar alguna solucion en memoria como h2 o en caso de utilizar postgres u otro motor favor facilitar el DML o un docker-compose.yml para montar.
- Se valorará la facilidad para levantar todo el proyecto y un readme con instrucciones.

### Desafíos Backend con Spring Boot

**1. API de Usuarios:** Diseña los servicios necesarios para un CRUD de Usuarios:

- [x] Crear, modificar, eliminar y listar usuarios

- [ ] Filtro por estado, nombre y lo que se crea pertinente

Cada Usuario debe contener minimamente los siguientes datos:

- [x] Identificador único

- [x] Nombre comleto
- [x] Contraseña (cifrado es opcional)
- [x] Correo electrónico
- [x] Estado (ACTIVO e INACTIVO)
- [x] Y otra información relevante.
- [x] Rol (Obs: Los roles pueden ser ADMIN, CONSULTOR)

_Obs: Puedes agregar cualquier servicio que se crea necesario al API._

- [x] _Obs 2: El rol **ADMIN** puede consumir todos los servicios, el rol **CONSULTOR** solo los listados (GET)_

- [x] _Obs 3: Los usuarios inactivos no pueden iniciar sesion_

**2. Implementar autenticación y autorización:**

- [x] Crea una capa de autenticación y autorización para proteger los endpoints de la API.
- [x] Asegúrate de que solo los usuarios autenticados y autorizados puedan acceder a ciertos recursos.
- [x] No olvides el control de los ROLES.

- [x] Obs: Se sugiere usar SpringSecurity para la seguridad. Pero el mismo tambien es opcional\*

### Test Unitarios

- [x] Escribir test unitarios o de integración que verifiquen los anteriores requerimientos funcionales. No es necesario tener full covertura, sino seleccionar los puntos más críticos de la lógica del sistema y enfocarse en eso. Por ejemplo, asegurarse de que se puedan crear correctamente los Usuarios y marcarlas como activos.

### Desafíos Frontend Integrados

- [ ] 1. **Diseñar una interfaz para inicio de sesion** Crea una pagina de inicio de sesion que reciba usuario y contraseña, en caso de exito redirigir a la pagina de listado de usuarios, caso contrario mostrar un mensaje de error.

- [ ] 1. **Diseñar una interfaz para mostrar la lista de usuarios:** Crea una interfaz web para consumir el endpoint que obtiene la lista de usuarios. Muestra la información de manera atractiva y fácil de entender.

- [ ] 2. **Implementar un formulario para agregar nuevos usuarios:** Diseña un formulario que permita agregar nuevos usuarios a través de la API backend. Asegúrate de validar los campos y proporcionar mensajes de error claros.

Estos desafíos te darán una excelente oportunidad para aprender y poner en práctica tus habilidades de desarrollo en el entorno de Spring Boot y Frontend. ¡Esperamos con ansias ver tus contribuciones a este proyecto!

### Empezando el Desafío

- [x] Para empezar crear un fork de este repositorio para implementar los ejercicios.

- [ ] Adjuntar cualquier documentación al proyecto en forma de archivos con extensión `.md`.

- [ ] Se recomienda ir haciendo commits a medida que se avanza con la solución. Agrupando estos commits si corresponde hacerlo.

## Envíar el código para evaluación

Luego al finalizar enviar un email con el link al fork a la persona que te envió este test.

_Ante cualquier duda podes contactarme directamente al correo [marcelo.lopez@somosetica.com](mailto:marcelo.lopez@somosetica.com)_

¡Buena suerte de parte de todo el equipo de e-tica!

# Instructivo de como levantar el proyecto


### Requisitos 
- Jdk 11+ (requisito para Maven)
- Maven
- Docker
- Docker compose


### Pasos
- Clonar el repositorio con **git clone https://github.com/aleins99/test-fullstack-java.git**
- **cd test-fullstack-java/**
- **mvn package -DskipTests** (-DskipTests es para saltar los tests porque necesitamos que esté corriendo la bd para ejecutar los tests)
- **docker compose up --build** o **docker-compose up --build** para correr los contenedores de docker definidos en docker-compose.yml
- Ir a la ruta **http://localhost:5173/**


### Usuarios
Tenemos 3 usuarios predefinidos:
1. admin@gmail.com (rol Admin)
2. inactivo@gmail.com (usuario Inactivo)
3. consultor@gmail.com (rol Consultor)

La contraseña para los tres usuarios es: **admin**



### Tests

**Nota**: Para ejecutar las pruebas, los contenedores de Docker deben estar funcionando, ya que la base de datos está vinculada a ellos. 
    
Puedes ejecutar las pruebas con el comando **mvn test** en el directorio del proyecto.




------
Eso sería todo :) Espero que no hayas tenido problemas al levantar 

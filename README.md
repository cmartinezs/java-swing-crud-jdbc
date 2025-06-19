# Aplicación CRUD Java Swing con JDBC

Una aplicación de escritorio que demuestra operaciones CRUD (Crear, Leer, Actualizar, Eliminar) utilizando Java Swing
para la interfaz gráfica y JDBC para la conectividad con la base de datos.

## Requisitos

- Java JDK 21 o superior
- MySQL 5.7 o superior
- Maven
- IntelliJ Ultimate

## Dependencias

- Controlador JDBC de MySQL
- Java Swing (incluido en JDK)

## Instalación

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/tunombre/java-swing-crud-jdbc.git
   ```
2. Configurar su base de datos

3. Configurar la conexión a la base de datos en `src/main/resources/db.properties`
   ```properties
   db.url=jdbc: 
   db.user=tu-usuario-de-bd
   db.password=tu-contraseña-de-bd
   ```
4. Construir el proyecto:
   ```bash
   mvn clean install
   ```

## Uso

1. Ejecutar la aplicación:
   ```bash
   java -jar target/java-swing-crud-jdbc.jar
   ```

2. Utilizar la interfaz gráfica para:
    - Agregar nuevos registros
    - Ver registros existentes
    - Actualizar registros
    - Eliminar registros

## Estructura del Proyecto

// en construcción

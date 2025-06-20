
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

## Configuración del Entorno

1. **Base de Datos**
   - Crear una base de datos MySQL llamada `swing_java`
   - Ejecutar el script de inicialización ubicado en `src/main/resources/db/init.sql`

2. **IDE (IntelliJ IDEA)**
   - Importar como proyecto Maven
   - Verificar que el JDK 21 esté configurado
   - Asegurarse que las dependencias Maven estén descargadas

## Instalación

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/tunombre/java-swing-crud-jdbc.git
   ```

2. Configurar la conexión a la base de datos en `src/main/resources/db.properties`
   ```properties
   db.url=jdbc:mysql://localhost:3306/swing_java
   db.user=tu-usuario-de-bd
   db.password=tu-contraseña-de-bd
   ```

3. Construir el proyecto:
   ```bash
   mvn clean install
   ```


## Estructura del Proyecto

El proyecto sigue el patrón de arquitectura MVC (Modelo-Vista-Controlador) y está organizado de la siguiente manera:
```
java-swing-crud-jdbc/ 
├── src/ 
│  └── main/ 
│     ├── java/
│     │   └── cl/duoc/swingapp/ 
│     │       ├── controller/ # Controladores para la lógica de negocio 
│     │       ├── model/ # Modelos de datos y DAOs 
│     │       ├── view/ # Componentes de la interfaz gráfica 
│     │       └── Main.java # Punto de entrada de la aplicación 
│     └── resources/ 
│         └── db.properties # Configuración de la base de datos 
├── pom.xml # Configuración de Maven y dependencias 
└── README.md # Documentación del proyecto
```
### Patrón MVC

- **Modelo**: Representa los datos y la lógica de negocio (package `model`)
- **Vista**: Interfaces gráficas construidas con Swing (package `view`)
- **Controlador**: Maneja eventos y coordina el modelo y la vista (package `controller`)

## Funcionalidades

1. **Gestión de Usuarios**
   - Lista de usuarios (con paginación por implementar)
   - Formulario para crear/editar usuarios
   - Eliminación de usuarios con confirmación
   - Búsqueda por nombre o email (por implementar)

2. **Validaciones**
   - Campos requeridos
   - Formato de email válido (por implementar)
   - Longitud máxima de campos (por implementar)

3. **Mensajes al Usuario**
   - Confirmaciones de operaciones exitosas
   - Alertas de errores
   - Mensajes de validación

## Guía Rápida de Desarrollo

### Agregar un Nuevo Campo a Usuario

1. Modificar la tabla en la base de datos
2. Actualizar la clase `User.java`
3. Modificar `UserDAOImpl.java` para incluir el nuevo campo
4. Actualizar `UserPanel.java` con el nuevo campo en el formulario

### Implementar Nueva Funcionalidad

1. Crear las clases necesarias siguiendo la estructura MVC
2. Agregar la opción en el menú principal si es necesario
3. Implementar la lógica en el controlador correspondiente

## Solución de Problemas Comunes

1. **Error de Conexión a BD**
   - Verificar credenciales en db.properties
   - Comprobar que el servidor MySQL esté activo
   - Validar el nombre de la base de datos

2. **Problemas de Compilación**
   - Ejecutar `mvn clean install`
   - Verificar la versión de JDK
   - Actualizar dependencias Maven

3. **Errores en Tiempo de Ejecución**
   - Revisar los logs en la consola
   - Verificar las excepciones SQL
   - Comprobar permisos de BD

## Comandos Útiles

1. **Compilación y Ejecución**
   ```bash
   # Compilar el proyecto
   mvn clean compile

   # Ejecutar pruebas
   mvn test

   # Generar JAR ejecutable
   mvn package

   # Ejecutar la aplicación
   java -jar target/java-swing-crud-jdbc.jar
   ```

2. **Base de Datos**
   ```sql
   -- Reiniciar la tabla de usuarios
   TRUNCATE TABLE users;

   -- Verificar registros
   SELECT * FROM users;
   ```

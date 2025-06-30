package cl.duoc.swingapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/* * ConnectionFactory.java
 *
 * Esta clase es responsable de crear y gestionar conexiones a la base de datos.
 * Utiliza un archivo de propiedades para cargar la configuración de conexión.
 *
 */
public class ConnectionFactory {
  public static final String DATABASE_PROPERTIES_FILENAME = "db.properties";
  public static final String DATABASE_URL_PROPERTY_NAME = "db.url";
  public static final String DATABASE_USER_PROPERTY_NAME = "db.user";
  public static final String DATABASE_PASSWORD_PROPERTY_NAME = "db.password";
  public static final String JDBC_DRIVER_CLASSNAME = "com.mysql.cj.jdbc.Driver";

  private static ConnectionFactory instance;

  private final String url;
  private final String user;
  private final String password;

  /**
   * Constructor privado para inicializar la conexión a la base de datos. Carga las propiedades
   * desde el archivo db.properties y registra el driver JDBC.
   */
  private ConnectionFactory() {
    try {
      Properties props = new Properties();
      props.load(getClass().getClassLoader().getResourceAsStream(DATABASE_PROPERTIES_FILENAME));
      url = props.getProperty(DATABASE_URL_PROPERTY_NAME);
      user = props.getProperty(DATABASE_USER_PROPERTY_NAME);
      password = props.getProperty(DATABASE_PASSWORD_PROPERTY_NAME);
      Class.forName(JDBC_DRIVER_CLASSNAME);
    } catch (Exception e) {
      throw new RuntimeException("No se pudo cargar configuración de BD", e);
    }
  }

  /**
   * Método estático para obtener la instancia única de ConnectionFactory.
   *
   * @return instancia de ConnectionFactory
   */
  public static ConnectionFactory getInstance() {
    if (instance == null) {
      instance = new ConnectionFactory();
    }
    return instance;
  }

  /**
   * Método para obtener una conexión a la base de datos.
   *
   * @return Connection objeto de conexión a la base de datos
   * @throws Exception si ocurre un error al obtener la conexión
   */
  public Connection getConnection() throws Exception {
    return DriverManager.getConnection(url, user, password);
  }
}

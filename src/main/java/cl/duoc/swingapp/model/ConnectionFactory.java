package cl.duoc.swingapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionFactory {
  private static ConnectionFactory instance;
  private final String url;
  private final String user;
  private final String password;

  private ConnectionFactory() {
    try {
      Properties props = new Properties();
      props.load(getClass().getClassLoader().getResourceAsStream("db.properties"));
      url = props.getProperty("db.url");
      user = props.getProperty("db.user");
      password = props.getProperty("db.password");
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (Exception e) {
      throw new RuntimeException("No se pudo cargar configuración de BD", e);
    }
  }

  public static ConnectionFactory getInstance() {
    if (instance == null) {
      instance = new ConnectionFactory();
    }
    return instance;
  }

  public Connection getConnection() throws Exception {
    return DriverManager.getConnection(url, user, password);
  }
}

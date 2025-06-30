package cl.duoc.swingapp.view.entity;

public class LoginView {
  private final String username;
  private final String password;

  public LoginView(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getValidationErrors() {
    StringBuilder errors = new StringBuilder();

    if (username == null || username.trim().isEmpty()) {
      errors.append("El nombre de usuario es requerido\n");
    }

    if (password == null || password.trim().isEmpty()) {
      errors.append("La contraseña es requerida\n");
    }

    return errors.toString();
  }

  public boolean isValid() {
    return getValidationErrors().isEmpty();
  }
}

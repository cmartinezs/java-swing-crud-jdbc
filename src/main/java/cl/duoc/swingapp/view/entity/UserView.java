package cl.duoc.swingapp.view.entity;

public class UserView {
  private Long id;
  private String username;
  private String password;
  private String email;

  public UserView(Long id, String username, String password, String email) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String toString() {
      return "UserView{" +
              "id=" + id +
              ", username='" + username + '\'' +
              ", password='" + password + '\'' +
              ", email='" + email + '\'' +
              '}';
  }

  public String getValidationErrors() {
    StringBuilder errors = new StringBuilder();

    if (username == null || username.trim().isEmpty()) {
      errors.append("El nombre de usuario es requerido\n");
    }

    if (password == null || password.trim().isEmpty()) {
      errors.append("La contraseña es requerida\n");
    }

    if (email == null || email.trim().isEmpty()) {
      errors.append("El correo electrónico es requerido\n");
    }

    return errors.toString();
  }

  public boolean isValid() {
    return getValidationErrors().isEmpty();
  }

}

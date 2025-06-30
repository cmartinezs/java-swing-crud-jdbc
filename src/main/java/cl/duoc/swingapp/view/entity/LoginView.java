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

  public boolean isValid() {
    return username == null || username.isEmpty() || password == null || password.isEmpty();
  }
}

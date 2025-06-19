package cl.duoc.swingapp.model.entity;

public class UserModel {
  private final Long id;
  private final String username;
  private final String password;
  private final String email;

  public UserModel(Long id, String username, String password, String email) {
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
}

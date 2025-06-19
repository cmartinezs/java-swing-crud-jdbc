package cl.duoc.swingapp.view.window;

import cl.duoc.swingapp.view.entity.UserView;
import cl.duoc.swingapp.view.exception.ViewException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class UserForm extends JDialog {
  private final JTextField usernameField;
  private final JPasswordField passwordField;
  private final JTextField emailField;
  private final JButton btnSave;
  private final Long userId;
  private Consumer<Long> loadUserCallback;

  public UserForm(UserMainFrame owner, Long userId) {
    super(owner, true);
    this.userId = userId;
    setTitle(userId == null ? "Nuevo Usuario" : "Editar Usuario");
    usernameField = new JTextField(20);
    passwordField = new JPasswordField(20);
    emailField = new JTextField(20);
    btnSave = new JButton("Guardar");

    setLayout(new GridLayout(4, 2, 5, 5));
    add(new JLabel("Nombre:"));
    add(usernameField);
    add(new JLabel("Password:"));
    add(passwordField);
    add(new JLabel("Email:"));
    add(emailField);
    add(new JLabel());
    add(btnSave);

    pack();
    setLocationRelativeTo(owner);
  }

  public void setLoadUserCallback(Consumer<Long> loadUserCallback) {
    this.loadUserCallback = loadUserCallback;
  }

  public void loadUser() throws ViewException {
    if (userId == null) {
      return; // No user to load
    }
    if (loadUserCallback == null) {
      throw new ViewException("No existe función para cargar el usuario");
    }
    loadUserCallback.accept(userId);
  }

  public void addSaveListener(ActionListener l) {
    btnSave.addActionListener(l);
  }

  public UserView getUserView() {
    String username = usernameField.getText().trim();
    String password = new String(passwordField.getPassword()).trim();
    String email = emailField.getText().trim();
    return new UserView(userId, username, password, email);
  }

  public boolean validateUserView() {
    String username = usernameField.getText().trim();
    String password = new String(passwordField.getPassword()).trim();
    String email = emailField.getText().trim();
    if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
      JOptionPane.showMessageDialog(
          this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
      return false;
    }
    return true;
  }

  public void setUserView(UserView userView) {
  }
}

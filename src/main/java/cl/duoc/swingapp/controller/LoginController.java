package cl.duoc.swingapp.controller;

import cl.duoc.swingapp.model.dao.UserDAO;
import cl.duoc.swingapp.model.exception.DAOException;
import cl.duoc.swingapp.view.entity.LoginView;
import cl.duoc.swingapp.view.window.LoginForm;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class LoginController {
  private static final String ERROR_INVALID_CREDENTIALS = "Usuario o contraseña incorrectos";
  private static final String ERROR_AUTHENTICATION_FAILED =
      "Ha ocurrido un error al autenticar al usuario";
  private static final String SUCCESS_LOGIN = "Sesión iniciada correctamente";

  private final LoginForm loginForm;
  private final UserDAO userDao;

  private Runnable onLoginSuccess;
  private Runnable onLoginCancel;

  public LoginController(LoginForm loginForm, UserDAO userDao) {
    this.loginForm = loginForm;
    this.userDao = userDao;
    initListeners();
  }

  private void initListeners() {
    this.loginForm.setLoginListener(this::login);
    this.loginForm.setCancelListener(this::cancel);
  }

  private void login(ActionEvent actionEvent) {

    int confirm = this.loginForm.showConfirmationMessage("¿Está seguro que desea ingresar?");

    if (confirm == JOptionPane.NO_OPTION) {
      this.loginForm.showSuccessMessage("Operación cancelada");
      return;
    }

    LoginView loginView = this.loginForm.getView();

    if (!loginView.isValid()) {
      this.loginForm.showErrorMessage(loginView.getValidationErrors());
      return;
    }

    try {
      if (!validateCredentials(loginView)) {
        this.loginForm.showErrorMessage(ERROR_INVALID_CREDENTIALS);
        return;
      }

      this.loginForm.showSuccessMessage(SUCCESS_LOGIN);
      this.loginForm.dispose();

      if (this.onLoginSuccess != null) {
        this.onLoginSuccess.run();
      }
    } catch (Exception e) {
      this.loginForm.showErrorMessage(ERROR_AUTHENTICATION_FAILED);
    }
  }

  private boolean validateCredentials(LoginView loginView) throws DAOException {
    return this.userDao
        .getUserByUsername(loginView.getUsername())
        .filter(user -> user.getPassword().equals(loginView.getPassword()))
        .isPresent();
  }

  private void cancel(ActionEvent actionEvent) {
    int confirm = this.loginForm.showConfirmationMessage("¿Está seguro que desea cancelar?");
    if (confirm == JOptionPane.NO_OPTION) {
      this.loginForm.showSuccessMessage("Operación cancelada");
      return;
    }
    this.loginForm.dispose();
    if (this.onLoginCancel != null) {
      this.onLoginCancel.run();
    }
  }

  public void showLogin() {
    this.loginForm.setVisible(true);
  }

  public void runOnLoginSuccess(Runnable onLoginSuccess) {
    this.onLoginSuccess = onLoginSuccess;
  }

  public void runOnLoginCancel(Runnable onLoginCancel) {
    this.onLoginCancel = onLoginCancel;
  }
}

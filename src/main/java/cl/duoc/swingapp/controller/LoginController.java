package cl.duoc.swingapp.controller;

import cl.duoc.swingapp.model.dao.UserDAO;
import cl.duoc.swingapp.model.exception.DAOException;
import cl.duoc.swingapp.view.entity.LoginView;
import cl.duoc.swingapp.view.window.LoginDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class LoginController {
  private static final String ERROR_NO_CREDENTIALS = "Por  favor, ingrese sus credenciales";
  private static final String ERROR_INVALID_CREDENTIALS = "Usuario o contraseña incorrectos";
  private static final String ERROR_AUTHENTICATION_FAILED =
      "Ha ocurrido un error al autenticar al usuario";
  private static final String SUCCESS_LOGIN = "Sesión iniciada correctamente";

  private final LoginDialog loginDialog;
  private final UserDAO userDao;

  private boolean loginCancelled = false;

  public LoginController(LoginDialog loginDialog, UserDAO userDao) {
    this.loginDialog = loginDialog;
    this.userDao = userDao;
    initListeners();
  }

  private void initListeners() {
    this.loginDialog.setLoginListener(this::login);
    this.loginDialog.setCancelListener(this::cancel);
  }

  private void login(ActionEvent actionEvent) {

    int confirm = this.loginDialog.showConfirmationMessage("¿Está seguro que desea ingresar?");

    if (confirm == JOptionPane.NO_OPTION) {
      this.loginDialog.showSuccessMessage("Operación cancelada");
      return;
    }

    LoginView loginView = this.loginDialog.getView();

    if (!loginView.isValid()) {
      this.loginDialog.showErrorMessage(loginView.getValidationErrors());
      return;
    }

    try {
      if (validateCredentials(loginView)) {
        this.loginDialog.showSuccessMessage(SUCCESS_LOGIN);
        this.loginDialog.dispose();
      } else {
        this.loginDialog.showErrorMessage(ERROR_INVALID_CREDENTIALS);
      }
    } catch (Exception e) {
      this.loginDialog.showErrorMessage(ERROR_AUTHENTICATION_FAILED);
    }
  }

  private boolean validateCredentials(LoginView loginView) throws DAOException {
    return this.userDao
        .getUserByUsername(loginView.getUsername())
        .filter(user -> user.getPassword().equals(loginView.getPassword()))
        .isPresent();
  }

  private void cancel(ActionEvent actionEvent) {
    int confirm = this.loginDialog.showConfirmationMessage("¿Está seguro que desea cancelar?");
    if (confirm == JOptionPane.NO_OPTION) {
      this.loginDialog.showSuccessMessage("Operación cancelada");
      return;
    }
    this.loginCancelled = true;
    this.loginDialog.dispose();
  }

  public void showLogin() {
    this.loginCancelled = false;
    this.loginDialog.setVisible(true);
  }

  public boolean isLoginCancelled() {
    return loginCancelled;
  }
}

package cl.duoc.swingapp;

import cl.duoc.swingapp.controller.LoginController;
import cl.duoc.swingapp.controller.MainController;
import cl.duoc.swingapp.controller.UserController;
import cl.duoc.swingapp.model.dao.impl.UserDAOImpl;
import cl.duoc.swingapp.view.window.LoginDialog;
import cl.duoc.swingapp.view.window.MainFrame;
import cl.duoc.swingapp.view.window.UserPanel;

import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    System.out.println("¡Proyecto CRUD Usuario iniciado!");
    SwingUtilities.invokeLater(Main::start);
  }

  private static void start() {

    UserDAOImpl userDao = new UserDAOImpl();
    LoginController loginController = new LoginController(new LoginDialog(), userDao);
    MainController mainController =
        new MainController(new MainFrame(), new UserController(new UserPanel(), userDao));

    System.out.println("Mostrando el diálogo de inicio de sesión...");
    loginController.showLogin();

    if (loginController.isLoginCancelled()) {
      System.out.println("Inicio de sesión cancelado por el usuario.");
      System.exit(0);
      return;
    }
    System.out.println("Inicio de sesión exitoso. Cargando la aplicación...");
    mainController.show();
  }
}

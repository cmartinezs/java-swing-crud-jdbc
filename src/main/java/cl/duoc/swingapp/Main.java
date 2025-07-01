package cl.duoc.swingapp;

import cl.duoc.swingapp.controller.LoginController;
import cl.duoc.swingapp.controller.MainController;
import cl.duoc.swingapp.controller.UserController;
import cl.duoc.swingapp.model.dao.impl.UserDAOImpl;
import cl.duoc.swingapp.view.window.LoginForm;
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
    LoginController loginController = new LoginController(new LoginForm(), userDao);
    MainController mainController =
        new MainController(new MainFrame(), new UserController(new UserPanel(), userDao));

    loginController.runOnLoginSuccess(() -> {
      System.out.println("Login exitoso, cargando la aplicación de principal...");
      mainController.show();
    });
    loginController.runOnLoginCancel(() -> {
      System.out.println("Login cancelado, cerrando la aplicación...");
      System.exit(0);
    });
    System.out.println("Mostrando el jframe de inicio de sesión...");
    loginController.showLogin();
  }
}

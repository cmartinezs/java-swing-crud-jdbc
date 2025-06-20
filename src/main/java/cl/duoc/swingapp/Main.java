package cl.duoc.swingapp;

import cl.duoc.swingapp.controller.MainController;
import cl.duoc.swingapp.controller.UserController;
import cl.duoc.swingapp.model.dao.impl.UserDAOImpl;
import cl.duoc.swingapp.view.window.MainFrame;
import cl.duoc.swingapp.view.window.UserPanel;

import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    System.out.println("¡Proyecto CRUD Usuario iniciado!");
    SwingUtilities.invokeLater(
        () -> {
          MainController mainController =
              new MainController(
                  new MainFrame(), new UserController(new UserPanel(), new UserDAOImpl()));
          mainController.show();
        });
  }
}

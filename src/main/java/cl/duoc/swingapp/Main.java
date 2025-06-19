package cl.duoc.swingapp;

import cl.duoc.swingapp.controller.UserController;
import cl.duoc.swingapp.model.dao.impl.UserDAOImpl;
import cl.duoc.swingapp.view.window.UserMainFrame;

import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    System.out.println("¡Proyecto CRUD Usuario iniciado!");
    SwingUtilities.invokeLater(() -> new UserController(new UserMainFrame(), new UserDAOImpl()));
  }
}

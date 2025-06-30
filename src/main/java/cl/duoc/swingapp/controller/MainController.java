package cl.duoc.swingapp.controller;

import cl.duoc.swingapp.view.window.MainFrame;

import java.awt.event.ActionEvent;

public class MainController {
  private final MainFrame mainFrame;
  private final UserController userController;

  public MainController(MainFrame mainFrame, UserController userController) {
    this.mainFrame = mainFrame;
    this.userController = userController;
    initListeners();
  }

  private void initListeners() {
    this.mainFrame.addMenuUsersListListener(this::showUserPanel);
  }

  private void showUserPanel(ActionEvent actionEvent) {
    this.mainFrame.setMainContent(this.userController.getUserPanel());
  }

  public void show() {
    this.mainFrame.setVisible(true);
  }
}

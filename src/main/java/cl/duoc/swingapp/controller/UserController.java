package cl.duoc.swingapp.controller;

import cl.duoc.swingapp.model.dao.UserDAO;
import cl.duoc.swingapp.model.entity.UserModel;
import cl.duoc.swingapp.model.exception.DAOException;
import cl.duoc.swingapp.view.entity.UserView;
import cl.duoc.swingapp.view.exception.ViewException;
import cl.duoc.swingapp.view.window.UserForm;
import cl.duoc.swingapp.view.window.UserPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class UserController {
  private final UserPanel userPanel;
  private final UserDAO userDao;
  private UserForm form;

  public UserController(UserPanel userPanel, UserDAO userDao) {
    this.userPanel = userPanel;
    this.userDao = userDao;
    initListeners();
  }

  private void initListeners() {
    this.userPanel.addNewUserListener(e -> openNewUserForm());
    this.userPanel.addEditUserListener(e -> openEditUserForm());
    this.userPanel.addDeleteUserListener(e -> deleteSelectedUser());
    this.userPanel.addRefreshUserListener(e -> refreshView());
  }

  private void openNewUserForm() {
    openUserForm(null);
  }

  private void openEditUserForm() {
    Optional<Long> optId = this.userPanel.getSelectedUserId();
    if (optId.isEmpty()) {
      this.userPanel.showError("No hay usuario seleccionado para editar");
      return;
    }
    openUserForm(optId.get());
  }

  private void openUserForm(Long userId) {
    this.form = new UserForm(getFrame(this.userPanel), userId, true);
    this.form.setLoadUserCallback(this::loadUser);
    this.form.addSaveListener(e -> saveUser());
    try {
      this.form.loadUser();
      this.form.setVisible(true);
    } catch (ViewException e) {
      this.form.dispose();
      this.userPanel.showError(e.getMessage());
    }
  }

  private Frame getFrame(JPanel panel) {
    Window window = SwingUtilities.getWindowAncestor(panel);
    if (window instanceof Frame aFrame) {
      return aFrame;
    } else {
      throw new IllegalStateException("El panel no está asociado a un Frame");
    }
  }

  private void saveUser() {
    int confirm =
            JOptionPane.showConfirmDialog(
                    this.form, "¿Está seguro que desea guardar?", "Confirmar", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.NO_OPTION) {
      return;
    }
    try {
      if (!this.form.validateUserView()) {
        return;
      }
      UserModel um = this.form.getUserView().toModel();
      if (um.getId() == null) {
        this.userDao.createUser(um);
      } else {
        this.userDao.updateUser(um);
      }
      refreshView();
    } catch (DAOException ex) {
      this.userPanel.showError(ex.getMessage());
      return;
    }
    this.form.dispose();
  }

  private void loadUser(Long userId) {
    if (userId == null) {
      return;
    }
    try {
      Optional<UserModel> optUm = this.userDao.getUserById(userId);
      if (optUm.isPresent()) {
        this.form.setUserView(UserView.fromModel(optUm.get()));
      } else {
        this.userPanel.showError("Usuario no encontrado");
        this.form.dispose();
      }
    } catch (DAOException e) {
      this.userPanel.showError(e.getMessage());
      this.form.dispose();
    }
  }

  private void deleteSelectedUser() {
    int confirm =
            JOptionPane.showConfirmDialog(
                    this.userPanel, "¿Está seguro que desea eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.NO_OPTION) {
      return;
    }
    Optional<Long> optId = this.userPanel.getSelectedUserId();
    if (optId.isEmpty()) {
      this.userPanel.showError("No hay usuario seleccionado para eliminar");
      return;
    }
    try {
      this.userDao.deleteUser(optId.get());
      refreshView();
    } catch (Exception e) {
      this.userPanel.showError(e.getMessage());
    }
  }

  private void refreshView() {
    try {
      this.userPanel.setTableData(collectViewFromModel(this.userDao.getAllUsers()));
    } catch (Exception e) {
      this.userPanel.showError(e.getMessage());
    }
  }

  private static List<UserView> collectViewFromModel(List<UserModel> users) {
    return users.stream().map(UserView::fromModel).collect(Collectors.toList());
  }

  public JPanel getUserPanel() {
    return this.userPanel;
  }
}

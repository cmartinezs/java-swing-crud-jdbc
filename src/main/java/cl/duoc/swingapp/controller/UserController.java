package cl.duoc.swingapp.controller;

import cl.duoc.swingapp.model.dao.UserDAO;
import cl.duoc.swingapp.model.entity.UserModel;
import cl.duoc.swingapp.model.exception.DAOException;
import cl.duoc.swingapp.view.entity.UserView;
import cl.duoc.swingapp.view.window.UserPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserController {
  private final UserPanel userPanel;
  private final UserDAO userDao;

  public UserController(UserPanel userPanel, UserDAO userDao) {
    this.userPanel = userPanel;
    this.userDao = userDao;
    initListeners();
  }

  private void initListeners() {
    this.userPanel.addNewUserListener(
        e -> {
          cleanUserForm();
          enabledUserForm();
        });
    this.userPanel.addEditUserListener(e -> loadSelectedUser());
    this.userPanel.addDeleteUserListener(e -> deleteSelectedUser());
    this.userPanel.addRefreshUserListener(e -> loadUserTable());
    this.userPanel.addSaveUserListener(e -> saveUser());
    this.userPanel.addCancelUserListener(e -> cancelUser());
  }

  private void cleanUserForm() {
    this.userPanel.cleanUserForm();
  }

  private void loadSelectedUser() {
    int confirm = this.userPanel.showConfirmDialog("¿Está seguro que desea editar?");
    if (confirm == JOptionPane.NO_OPTION) {
      this.userPanel.showInfoMessage("Operación cancelada cancelada");
      return;
    }
    Optional<Long> optId = this.userPanel.getSelectedUserId();
    if (optId.isEmpty()) {
      this.userPanel.showError("No hay usuario seleccionado");
      return;
    }
    Long userId = optId.get();
    try {
      Optional<UserModel> optUm = this.userDao.getUserById(userId);
      if (optUm.isPresent()) {
        this.userPanel.setUserView(toView(optUm.get()));
        this.enabledUserForm();
      } else {
        this.userPanel.showError("Usuario no encontrado");
      }
    } catch (DAOException e) {
      this.userPanel.showError(e.getMessage());
    }
  }

  private void deleteSelectedUser() {
    int confirm = this.userPanel.showConfirmDialog("¿Está seguro que desea eliminar?");
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
      loadUserTable();
    } catch (Exception e) {
      this.userPanel.showError(e.getMessage());
    }
  }

  private void saveUser() {
    int confirm = this.userPanel.showConfirmDialog("¿Está seguro que desea guardar?");
    if (confirm == JOptionPane.NO_OPTION) {
      this.userPanel.showInfoMessage("Operación cancelada");
      return;
    }

    UserView uv = this.userPanel.getUserView();

    if (!uv.isValid()) {
      this.userPanel.showError(uv.getValidationErrors());
      return;
    }

    try {
      UserModel um = toModel(uv);
      if (um.getId() == null) {
        this.userDao.createUser(um);
      } else {
        this.userDao.updateUser(um);
      }
      loadUserTable();
    } catch (DAOException ex) {
      this.userPanel.showError(ex.getMessage());
    }
  }

  private void cancelUser() {
    int confirm = this.userPanel.showConfirmDialog("¿Está seguro que desea cancelar?");
    if (confirm == JOptionPane.NO_OPTION) {
      this.userPanel.showInfoMessage("Operación cancelada");
      return;
    }
    cleanUserForm();
    disabledUserForm();
  }

  public void loadUserTable() {
    try {
      this.userPanel.setTableData(collectViewFromModel(this.userDao.getAllUsers()));
    } catch (Exception e) {
      this.userPanel.showError(e.getMessage());
    }
  }

  private Frame getFrame() {
    Window window = SwingUtilities.getWindowAncestor(this.userPanel);
    if (window instanceof Frame aFrame) {
      return aFrame;
    } else {
      throw new IllegalStateException("El panel no está asociado a un Frame");
    }
  }

  private static List<UserView> collectViewFromModel(List<UserModel> users) {
    return users.stream().map(UserController::toView).collect(Collectors.toList());
  }

  private static UserView toView(UserModel um) {
    return new UserView(um.getId(), um.getUsername(), um.getPassword(), um.getEmail());
  }

  private static UserModel toModel(UserView uv) {
    return new UserModel(uv.getId(), uv.getUsername(), uv.getPassword(), uv.getEmail());
  }

  public JPanel getUserPanel() {
    return this.userPanel;
  }

  public void disabledUserForm() {
    this.userPanel.disabledUserForm();
  }

  public void enabledUserForm() {
    this.userPanel.enabledUserForm();
  }
}

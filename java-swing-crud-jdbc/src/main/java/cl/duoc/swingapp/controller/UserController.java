package cl.duoc.swingapp.controller;

import cl.duoc.swingapp.model.dao.UserDAO;
import cl.duoc.swingapp.model.entity.UserModel;
import cl.duoc.swingapp.model.exception.DAOException;
import cl.duoc.swingapp.view.entity.UserView;
import cl.duoc.swingapp.view.exception.ViewException;
import cl.duoc.swingapp.view.window.UserForm;
import cl.duoc.swingapp.view.window.UserMainFrame;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The UserController class is responsible for handling the interaction between the user interface
 * and the data access layer. It serves as a mediator to manage user-related actions and updates to
 * the graphical user interface.
 *
 * <p>Responsibilities: - Handles user input events from the {@link UserMainFrame}. - Coordinates
 * data operations with the {@link UserDAO}. - Updates the view based on changes to the data model.
 *
 * <p>Constructor: - Initializes the controller with a specific {@link UserMainFrame} and {@link
 * UserDAO}. - Registers event listeners for creating, editing, and deleting users. - Refreshes the
 * user table in the UI upon initialization.
 *
 * <p>Methods: - {@link #openUserForm(Long)}: Opens the "UserForm" for creating or updating user
 * information. - {@link #deleteSelectedUser()}: Deletes the currently selected user from the
 * database and refreshes the displayed data. - {@link #refreshView()}: Updates the user table in
 * the UI with the latest data from the database. - {@link #collectViewFromModel(List)}: Converts a
 * list of user models into a list of user views for use in the UI.
 */
public class UserController {
  private final UserMainFrame userMainFrame;
  private final UserDAO userDAO;

  /**
   * Constructs a UserController to manage the interaction between the user interface and the data
   * access layer. The controller initializes event listeners for user-related actions, handles view
   * updates, and ensures the graphical user interface remains in sync with the underlying data.
   *
   * @param userMainFrame the main application frame responsible for displaying the user interface
   *     and handling user input events
   * @param userDAO the data access object responsible for performing CRUD operations on the user
   *     data
   * @see UserMainFrame
   * @see UserDAO
   * @see #refreshView()
   * @see #openUserForm(Long)
   * @see #deleteSelectedUser()
   * @see #collectViewFromModel(List)
   */
  public UserController(UserMainFrame userMainFrame, UserDAO userDAO) {
    this.userMainFrame = userMainFrame;
    this.userDAO = userDAO;
    // Registrar listeners
    userMainFrame.addNewUserListener(e -> openNewUserForm());
    userMainFrame.addEditUserListener(e -> openEditUserForm());
    userMainFrame.addDeleteUserListener(e -> deleteSelectedUser());
    userMainFrame.setVisible(true);
    refreshView();
  }

  private void openNewUserForm() {
    openUserForm(null);
  }

  private void openEditUserForm() {
    Optional<Long> optId = userMainFrame.getSelectedUserId();
    if (optId.isEmpty()) {
      userMainFrame.showError("No hay usuario seleccionado para editar");
      return;
    }
    openUserForm(optId.get());
  }

  private void openUserForm(Long userId) {
    UserForm form = new UserForm(userMainFrame, userId);
    form.setLoadUserCallback(id -> loadUser(form, id));
    form.addSaveListener(e -> saveUser(form, userId));
    form.setVisible(true);
    try {
      form.loadUser();
    } catch (ViewException e) {
      form.dispose();
      userMainFrame.showError(e.getMessage());
    }
  }

  private void saveUser(UserForm uf, Long userId) {
    try {
      if (!uf.validateUserView()) {
        return;
      }
      UserModel um = uf.getUserView().toModel();
      if (userId == null) {
        userDAO.createUser(um);
      } else {
        userDAO.updateUser(um);
      }
      refreshView();
    } catch (DAOException ex) {
      userMainFrame.showError(ex.getMessage());
      return;
    }
    uf.dispose();
  }

  private void loadUser(UserForm uf, Long userId) {
    if (userId == null) {
      return;
    }
    try {
      Optional<UserModel> optUm = userDAO.getUserById(userId);
      if (optUm.isPresent()) {
        uf.setUserView(UserView.fromModel(optUm.get()));
      } else {
        userMainFrame.showError("Usuario no encontrado");
        uf.dispose();
      }
    } catch (DAOException e) {
      userMainFrame.showError(e.getMessage());
      uf.dispose();
    }
  }

  /**
   * Deletes the user currently selected in the user interface, if any.
   *
   * <p>This method retrieves the ID of the selected user from the user interface. If no user is
   * selected, the method exits without performing any action. Otherwise, it attempts to delete the
   * user corresponding to the retrieved ID using the data access object. After successfully
   * deleting the user, the user interface is refreshed to reflect the changes.
   *
   * <p>If an exception occurs during the deletion process, an error message is displayed to the
   * user through the user interface.
   *
   * @see UserMainFrame#showError(String)
   * @see UserDAO#deleteUser(Long)
   * @see #refreshView()
   */
  private void deleteSelectedUser() {
    Optional<Long> optId = userMainFrame.getSelectedUserId();
    if (optId.isEmpty()) {
      userMainFrame.showError("No hay usuario seleccionado para eliminar");
      return;
    }
    try {
      userDAO.deleteUser(optId.get());
      refreshView();
    } catch (Exception e) {
      userMainFrame.showError(e.getMessage());
    }
  }

  /**
   * Refreshes the user interface by updating the data displayed in the table based on the most
   * recent user data retrieved from the data source.
   *
   * <p>This method fetches all user data from the data access object ({@link UserDAO}), converts it
   * to a view-friendly format, and updates the table in the user interface with the newly obtained
   * data through the {@link UserMainFrame} object.
   *
   * <p>If an error occurs during the data retrieval process, such as a failure to fetch data from
   * the data source, the error message is displayed to the user using the {@link
   * UserMainFrame#showError(String)} method.
   *
   * @see UserDAO#getAllUsers()
   * @see UserMainFrame#setTableData(List)
   * @see UserMainFrame#showError(String)
   * @see #collectViewFromModel(List)
   */
  private void refreshView() {
    try {
      userMainFrame.setTableData(collectViewFromModel(userDAO.getAllUsers()));
    } catch (Exception e) {
      userMainFrame.showError(e.getMessage());
    }
  }

  /**
   * Converts a list of {@link UserModel} objects into a list of {@link UserView} objects. This
   * method maps each UserModel instance into a corresponding {@code UserView} instance using the
   * static {@link UserView#fromModel(UserModel)} method.
   *
   * @param users the list of UserModel objects to be converted into UserView objects
   * @return a list of UserView objects representing the converted model data
   * @see UserView#fromModel(UserModel)
   */
  private static List<UserView> collectViewFromModel(List<UserModel> users) {
    return users.stream().map(UserView::fromModel).collect(Collectors.toList());
  }
}

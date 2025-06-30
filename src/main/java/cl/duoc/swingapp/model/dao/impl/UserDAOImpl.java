package cl.duoc.swingapp.model.dao.impl;

import cl.duoc.swingapp.model.ConnectionFactory;
import cl.duoc.swingapp.model.dao.UserDAO;
import cl.duoc.swingapp.model.entity.UserModel;
import cl.duoc.swingapp.model.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

  private static final String INSERT =
      "INSERT INTO users(username, password, email) VALUES (?, ?, ?)";
  private static final String UPDATE =
      "UPDATE users SET username = ?, password = ?, email = ? WHERE id = ?";
  private static final String DELETE = "DELETE FROM users WHERE id = ?";

  private static final String SELECT_BY_ID = "SELECT * FROM users WHERE id = ?";

  private static final String SELECT_BY_USERNAME = "SELECT * FROM users WHERE username = ?";

  private static final String SELECT_BY_EMAIL = "SELECT * FROM users WHERE email = ?";

  private static final String SELECT_ALL = "SELECT id, username, email FROM users";

  @Override
  public Optional<Long> createUser(UserModel u) throws DAOException {
    try (Connection conn = ConnectionFactory.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, u.getUsername());
      ps.setString(2, u.getPassword());
      ps.setString(3, u.getEmail());
      ps.executeUpdate();
      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (rs.next()) {
          return Optional.of(rs.getLong(1));
        }
      }
    } catch (SQLException e) {
      System.err.println("Error al crear usuario: " + e.getMessage());
      throw new DAOException("Error al crear usuario", e);
    } catch (Exception e) {
      System.err.println("Error inesperado al crear usuario: " + e.getMessage());
      throw new DAOException("Error inesperado al crear usuario", e);
    }
    return Optional.empty();
  }

  @Override
  public boolean updateUser(UserModel user) throws DAOException {
    try (Connection conn = ConnectionFactory.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(UPDATE)) {
      ps.setString(1, user.getUsername());
      ps.setString(2, user.getPassword());
      ps.setString(3, user.getEmail());
      ps.setLong(4, user.getId());
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      System.err.println("Error al actualizar usuario: " + e.getMessage());
      throw new DAOException("Error al actualizar usuario", e);
    } catch (Exception e) {
      System.err.println("Error inesperado al actualizar usuario: " + e.getMessage());
      throw new DAOException("Error inesperado al actualizar usuario", e);
    }
  }

  @Override
  public boolean deleteUser(Long id) throws DAOException {
    try (Connection conn = ConnectionFactory.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(DELETE)) {
      ps.setLong(1, id);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      System.err.println("Error al eliminar usuario: " + e.getMessage());
      throw new DAOException("Error al eliminar usuario", e);
    } catch (Exception e) {
      System.err.println("Error inesperado al eliminar usuario: " + e.getMessage());
      throw new DAOException("Error inesperado al eliminar usuario", e);
    }
  }

  @Override
  public Optional<UserModel> getUserById(Long id) throws DAOException {
    try (Connection conn = ConnectionFactory.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {
      ps.setLong(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return Optional.of(
              new UserModel(
                  rs.getLong("id"),
                  rs.getString("username"),
                  rs.getString("password"),
                  rs.getString("email")));
        }

        System.out.println("Usuario no encontrado con ID: " + id);
      }
    } catch (SQLException e) {
      System.err.println("Error al obtener usuario por ID: " + e.getMessage());
      throw new DAOException("Error al obtener usuario por ID", e);
    } catch (Exception e) {
      System.err.println("Error inesperado al obtener usuario por ID: " + e.getMessage());
      throw new DAOException("Error inesperado al obtener usuario por ID", e);
    }
    return Optional.empty();
  }

  @Override
  public Optional<UserModel> getUserByUsername(String username) throws DAOException {
    try (Connection conn = ConnectionFactory.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(SELECT_BY_USERNAME)) {
      ps.setString(1, username);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return Optional.of(
              new UserModel(
                  rs.getLong("id"),
                  rs.getString("username"),
                  rs.getString("password"),
                  rs.getString("email")));
        }
        System.out.println("Usuario no encontrado con username: " + username);
      }
    } catch (SQLException e) {
      System.err.println("Error al obtener usuario por username: " + e.getMessage());
      throw new DAOException("Error al obtener usuario por username", e);
    } catch (Exception e) {
      System.err.println("Error inesperado al obtener usuario por username: " + e.getMessage());
      throw new DAOException("Error inesperado al obtener usuario por username", e);
    }
    return Optional.empty();
  }

  @Override
  public List<UserModel> getAllUsers() throws DAOException {
    List<UserModel> list = new ArrayList<>();
    try (Connection conn = ConnectionFactory.getInstance().getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(SELECT_ALL)) {
      while (rs.next()) {
        list.add(
            new UserModel(rs.getLong("id"), rs.getString("username"), null, rs.getString("email")));
      }
    } catch (SQLException e) {
      System.err.println("Error al obtener todos los usuarios: " + e.getMessage());
      throw new DAOException("Error al obtener todos los usuarios", e);
    } catch (Exception e) {
      System.err.println("Error inesperado al obtener todos los usuarios: " + e.getMessage());
      throw new DAOException("Error inesperado al obtener todos los usuarios", e);
    }
    return list;
  }
}

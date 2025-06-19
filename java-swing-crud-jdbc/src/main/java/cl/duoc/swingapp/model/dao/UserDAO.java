package cl.duoc.swingapp.model.dao;

import cl.duoc.swingapp.model.entity.UserModel;
import cl.duoc.swingapp.model.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
  Optional<Long> createUser(UserModel user) throws DAOException;

  boolean updateUser(UserModel user) throws DAOException;

  boolean deleteUser(Long id);

  Optional<UserModel> getUserById(Long id) throws DAOException;

  Optional<UserModel> getUserByUsername(String username) throws DAOException;

  Optional<UserModel> getUserByEmail(String email) throws DAOException;

  List<UserModel> getAllUsers() throws DAOException;
}

package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dao.UserDAO;
import org.astashonok.onlinestorebackend.dto.Role;
import org.astashonok.onlinestorebackend.dto.User;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendLogicalException;
import org.astashonok.onlinestorebackend.exceptions.technicalexception.DatabaseException;
import org.astashonok.onlinestorebackend.util.ClassName;
import org.astashonok.onlinestorebackend.util.pool.Pool;
import org.astashonok.onlinestorebackend.util.pool.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.pool.Pools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.astashonok.onlinestorebackend.daoImpl.RoleDAOImpl.getRoleById;

public class UserDAOImpl implements UserDAO {

    private Pool pool;
    private static final Logger logger = LoggerFactory.getLogger(ClassName.getCurrentClassName());

    public UserDAOImpl(Pool pool) {
        this.pool = pool;
    }

    public UserDAOImpl() {
        pool = Pools.newPool(PoolWithDataSource.class);
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    private Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    @Override
    public User getByEmail(String email) throws BackendException {
        logger.info("Invoking of getByEmail(String email) method: email = {}", email);
        String sql = "SELECT id, first_name, last_name, email, password, contact_number, enabled, role_id FROM users"
                + " WHERE email = '" + email + "'";
        User user = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setContactNumber(resultSet.getString("contact_number"));
                    user.setEnabled(resultSet.getBoolean("enabled"));
                    long roleId = resultSet.getLong("role_id");
                    user.setRole(getRoleById(roleId, connection));
                    connection.commit();
                }
                logger.info("The user was fetched from database: {}", user);
            } catch (BackendLogicalException e) {
                connection.rollback();
                logger.error("The user wasn't fetched from database", e);
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                logger.error("SQLException", e);
                throw new DatabaseException("SQLException", e);
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return user;
    }

    @Override
    public List<User> getAll() throws BackendException {
        logger.info("Invoking of getAll() method");
        return get("SELECT id, first_name, last_name, email, password, contact_number, enabled, role_id FROM users");
    }

    @Override
    public List<User> getAllEnable() throws BackendException {
        logger.info("Invoking of getAllEnable() method");
        return get("SELECT id, first_name, last_name, email, password, contact_number, enabled, role_id FROM "
                + "users WHERE enabled = 1");
    }

    private List<User> get(String sql) throws BackendException {
        logger.info("Invoking of get(String sql) method: sql = {}", sql);
        List<User> users = new ArrayList<>();
        User user;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setContactNumber(resultSet.getString("contact_number"));
                    long roleId = resultSet.getLong("role_id");
                    user.setEnabled(resultSet.getBoolean("enabled"));
                    user.setRole(getRoleById(roleId, connection));
                    users.add(user);
                }
                connection.commit();
                logger.info("The users was fetched from database: {}", users);
            } catch (BackendLogicalException e) {
                connection.rollback();
                logger.error("The users wasn't fetched from database", e);
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                logger.error("SQLException", e);
                throw new DatabaseException("SQLException", e);
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return users;
    }

    @Override
    public List<User> getByRole(Role role) throws BackendException {
        logger.info("Invoking of getByRole(Role role): {}", role);
        String sql = "SELECT id, first_name, last_name, email, password, contact_number, enabled, role_id FROM users"
                + " WHERE role_id = " + role.getId();
        List<User> users = new ArrayList<>();
        User user;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setContactNumber(resultSet.getString("contact_number"));
                user.setEnabled(resultSet.getBoolean("enabled"));
                user.setRole(role);
                users.add(user);
            }
            logger.info("The users was fetched from database: {}", users);
        } catch (BackendLogicalException e) {
            logger.error("The users wasn't fetched from database", e);
            throw e;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return users;
    }

    @Override
    public long add(User entity) throws BackendException {
        logger.info("Invoking of add(User entity) method: {}", entity);
        String sql = "INSERT INTO users (first_name, last_name, email, password, contact_number, enabled, role_id)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?)";
        long id = -1;
        long userId;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, entity.getFirstName());
                preparedStatement.setString(2, entity.getLastName());
                preparedStatement.setString(3, entity.getEmail());
                preparedStatement.setString(4, entity.getPassword());
                preparedStatement.setString(5, entity.getContactNumber());
                preparedStatement.setBoolean(6, entity.isEnabled());
                preparedStatement.setLong(7, entity.getRole().getId());
                if (preparedStatement.executeUpdate() == 0) {
                    logger.error("Creating the user is failed, no rows is affected");
                    throw new DatabaseException("Creating the user is failed, no rows is affected");
                }
                generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    userId = generatedKeys.getLong(1);
                } else {
                    logger.error("The user key isn't fetched from database");
                    throw new DatabaseException("The user key isn't fetched from database");
                }
                sql = "INSERT INTO carts (id, total, cart_items) VALUES (" + userId + ", 0, 0)";
                preparedStatement = connection.prepareStatement(sql);
                if (preparedStatement.executeUpdate() == 0) {
                    logger.error("Creating the cart is failed, no rows is affected");
                    throw new DatabaseException("Creating the cart is failed, no rows is affected");
                }
                id = userId;
                entity.setId(id);
                connection.commit();
                logger.info("The user was added, its id = {}", id);
            } catch (BackendLogicalException e) {
                connection.rollback();
                logger.error("The user wasn't fetched from database", e);
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                logger.error("SQLException", e);
                throw new DatabaseException("SQLException", e);
            } finally {
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return id;
    }

    @Override
    public User getById(long id) throws BackendException {
        logger.info("Invoking of getById(long id), id = {}", id);
        User user;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                user = getUserById(id, connection);
                connection.commit();
                logger.info("The user was fetched from database: {}", user);
            } catch (BackendLogicalException e) {
                connection.rollback();
                logger.error("The user wasn't fetched from database", e);
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                logger.error("SQLException", e);
                throw new DatabaseException("SQLException", e);
            }
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return user;
    }

    @Override
    public boolean edit(User entity) throws BackendException {
        logger.info("Invoking of edit(User entity): {}", entity);
        String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ?, password = ?, contact_number = ?, "
                + "enabled = ?, role_id = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(8, entity.getId());
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getEmail());
            preparedStatement.setString(4, entity.getPassword());
            preparedStatement.setString(5, entity.getContactNumber());
            preparedStatement.setBoolean(6, entity.isEnabled());
            preparedStatement.setLong(7, entity.getRole().getId());
            if (preparedStatement.executeUpdate() == 0) {
                logger.error("Updating the user is failed, no rows is affected");
                throw new DatabaseException("Updating the user is failed, no rows is affected");
            }
            logger.info("The user was updated");
            return true;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
    }

    // ENGINE = InnoDB and ON DELETE CASCADE are used in this project
    @Override
    public boolean remove(User entity) throws BackendException {
        logger.info("Invoking of remove(User entity): {}", entity);
        String sql = "DELETE FROM users WHERE id = " + entity.getId();
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (preparedStatement.executeUpdate() == 0) {
                logger.error("Deleting the user is failed, no rows is affected");
                throw new DatabaseException("Deleting the user is failed, no rows is affected");
            }
            logger.info("The user was deleted successfully");
            return true;
        } catch (SQLException e) {
            logger.error("Deleting user is failed", e);
            throw new DatabaseException("Deleting user is failed");
        }
    }

    static User getUserById(long id, Connection connection) throws SQLException, BackendLogicalException {
        String sql = "SELECT id, first_name, last_name, email, password, contact_number, enabled, role_id FROM users"
                + " WHERE id = " + id;
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setContactNumber(resultSet.getString("contact_number"));
                user.setEnabled(resultSet.getBoolean("enabled"));
                long roleId = resultSet.getLong("role_id");
                user.setRole(getRoleById(roleId, connection));
            }
        }
        return user;
    }
}
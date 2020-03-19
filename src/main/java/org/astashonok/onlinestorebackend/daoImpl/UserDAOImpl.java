package org.astashonok.onlinestorebackend.dao.daoImpl;

import org.astashonok.onlinestorebackend.dao.UserDAO;
import org.astashonok.onlinestorebackend.dto.Role;
import org.astashonok.onlinestorebackend.dto.User;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.util.Pool;
import org.astashonok.onlinestorebackend.util.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.Pools;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAOImpl implements UserDAO {

    private Pool pool;

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
    public User getByEmail(String email) {
        String sqlForUser = "SELECT id, first_name, last_name, email, password, contact_number, enabled, role_id FROM users"
                + " WHERE email = ?";
        String sqlForRole = "SELECT id, name, active FROM roles WHERE id = ?";
        User user;
        Role role;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sqlForUser);
                preparedStatement.setString(1, email);
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
                    preparedStatement = connection.prepareStatement(sqlForRole);
                    preparedStatement.setLong(1, roleId);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        role = new Role();
                        role.setId(resultSet.getLong("id"));
                        role.setName(resultSet.getString("name"));
                        role.setActive(resultSet.getBoolean("active"));
                        user.setRole(role);
                        connection.commit();
                        return user;
                    }
                }
            } catch (SQLException | OnlineStoreLogicalException e) {
                connection.rollback();
                e.printStackTrace();
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        String sqlForUser = "SELECT id, first_name, last_name, email, password, contact_number, enabled, role_id FROM users";
        String sqlForRole = "SELECT id, name, active FROM roles WHERE id = ?";
        List<User> users = new ArrayList<>();
        Map<User, Long> userHashMap = new HashMap<>();
        User user;
        Role role;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sqlForUser);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setContactNumber(resultSet.getString("contact_number"));
                    Long roleId = resultSet.getLong("role_id");
                    user.setEnabled(resultSet.getBoolean("enabled"));
                    userHashMap.put(user, roleId);
                }
                preparedStatement = connection.prepareStatement(sqlForRole);
                for (Map.Entry<User, Long> entry : userHashMap.entrySet()) {
                    preparedStatement.setLong(1, entry.getValue());
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        role = new Role();
                        role.setId(resultSet.getLong("id"));
                        role.setName(resultSet.getString("name"));
                        role.setActive(resultSet.getBoolean("active"));
                        user = entry.getKey();
                        user.setRole(role);
                        users.add(user);
                    }
                }
                connection.commit();
                return users;
            } catch (SQLException | OnlineStoreLogicalException e) {
                connection.rollback();
                e.printStackTrace();
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAllEnable() {
        String sqlForUser = "SELECT id, first_name, last_name, email, password, contact_number, enabled, role_id FROM "
                + "users WHERE enabled = 1";
        String sqlForRole = "SELECT id, name, active FROM roles WHERE id = ?";
        List<User> users = new ArrayList<>();
        Map<User, Long> userHashMap = new HashMap<>();
        User user;
        Role role;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sqlForUser);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setContactNumber(resultSet.getString("contact_number"));
                    Long roleId = resultSet.getLong("role_id");
                    user.setEnabled(resultSet.getBoolean("enabled"));
                    userHashMap.put(user, roleId);
                }
                preparedStatement = connection.prepareStatement(sqlForRole);
                for (Map.Entry<User, Long> entry : userHashMap.entrySet()) {
                    preparedStatement.setLong(1, entry.getValue());
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        role = new Role();
                        role.setId(resultSet.getLong("id"));
                        role.setName(resultSet.getString("name"));
                        role.setActive(resultSet.getBoolean("active"));
                        user = entry.getKey();
                        user.setRole(role);
                        users.add(user);
                    }
                }
                connection.commit();
                return users;
            } catch (SQLException | OnlineStoreLogicalException e) {
                connection.rollback();
                e.printStackTrace();
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getByRole(Role role) {
        String sql = "SELECT id, first_name, last_name, email, password, contact_number, enabled, role_id FROM users"
                + " WHERE role_id = ?";
        List<User> users = new ArrayList<>();
        User user;
        ResultSet resultSet = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, role.getId());
            resultSet = preparedStatement.executeQuery();
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
            return users;
        } catch (SQLException | OnlineStoreLogicalException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public boolean add(User entity) {
        String sqlForUserCreation = "INSERT INTO users (first_name, last_name, email, password, contact_number, enabled, role_id)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?)";
        String sqlForUserFetching = "SELECT id, first_name, last_name, email, password, contact_number, enabled, role_id FROM users"
                + " WHERE email = '" + entity.getEmail() + "';";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sqlForUserCreation);
                preparedStatement.setString(1, entity.getFirstName());
                preparedStatement.setString(2, entity.getLastName());
                preparedStatement.setString(3, entity.getEmail());
                preparedStatement.setString(4, entity.getPassword());
                preparedStatement.setString(5, entity.getContactNumber());
                preparedStatement.setBoolean(6, entity.isEnabled());
                preparedStatement.setLong(7, entity.getRole().getId());
                preparedStatement.executeUpdate();
                preparedStatement = connection.prepareStatement(sqlForUserFetching);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String sqlForCart = "INSERT INTO carts (id, total, cart_items) VALUES (" + resultSet
                            .getLong("id") + ", 0, 0)";
                    preparedStatement = connection.prepareStatement(sqlForCart);
                    preparedStatement.executeUpdate();
                    connection.commit();
                    return true;
                }
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getById(long id) {
        String sqlForUser = "SELECT id, first_name, last_name, email, password, contact_number, enabled, role_id FROM users"
                + " WHERE id = ?";
        String sqlForRole = "SELECT id, name, active FROM roles WHERE id = ?";
        User user;
        Role role;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sqlForUser);
                preparedStatement.setLong(1, id);
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
                    preparedStatement = connection.prepareStatement(sqlForRole);
                    preparedStatement.setLong(1, roleId);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        role = new Role();
                        role.setId(resultSet.getLong("id"));
                        role.setName(resultSet.getString("name"));
                        role.setActive(resultSet.getBoolean("active"));
                        user.setRole(role);
                        connection.commit();
                        return user;
                    }
                }
            } catch (SQLException | OnlineStoreLogicalException e) {
                connection.rollback();
                e.printStackTrace();
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean edit(User entity) {
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
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ?
    @Override
    public boolean remove(User entity) {
        String sqlForCartDeleting = "DELETE FROM carts WHERE id = " + entity.getId() + ";";
        String sqlForUserDeleting = "DELETE FROM users WHERE email = '" + entity.getEmail() + "';";
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            try {
                connection.setAutoCommit(false);
                statement.addBatch(sqlForCartDeleting);
                statement.addBatch(sqlForUserDeleting);
                statement.executeBatch();
                connection.commit();
                return true;
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
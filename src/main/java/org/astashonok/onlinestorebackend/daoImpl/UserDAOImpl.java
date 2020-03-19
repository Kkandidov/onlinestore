package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dao.UserDAO;
import org.astashonok.onlinestorebackend.dto.Role;
import org.astashonok.onlinestorebackend.dto.User;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.util.pool.Pool;
import org.astashonok.onlinestorebackend.util.pool.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.pool.Pools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.astashonok.onlinestorebackend.daoImpl.RoleDAOImpl.getRoleById;

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
        String sql = "SELECT id, first_name, last_name, email, password, contact_number, enabled, role_id FROM users"
                + " WHERE email = '" + email + "'";
        User user = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
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
                    user.setRole(getRoleById(roleId, connection, preparedStatement, resultSet));
                    connection.commit();
                }
            } catch (SQLException | OnlineStoreLogicalException e) {
                user = null;
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
        return user;
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT id, first_name, last_name, email, password, contact_number, enabled, role_id FROM users";
        List<User> users = new ArrayList<>();
        User user;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
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
                    user.setRole(getRoleById(roleId, connection, preparedStatement, resultSet));
                    users.add(user);
                }
                connection.commit();
            } catch (SQLException | OnlineStoreLogicalException e) {
                users = null;
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
        return users;
    }

    @Override
    public List<User> getAllEnable() {
        String sql = "SELECT id, first_name, last_name, email, password, contact_number, enabled, role_id FROM "
                + "users WHERE enabled = 1";
        List<User> users = new ArrayList<>();
        User user;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
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
                    user.setRole(getRoleById(roleId, connection, preparedStatement, resultSet));
                    users.add(user);
                }
                connection.commit();
            } catch (SQLException | OnlineStoreLogicalException e) {
                users = null;
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
        return users;
    }

    @Override
    public List<User> getByRole(Role role) {
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
        } catch (SQLException | OnlineStoreLogicalException e) {
            users = null;
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public long add(User entity) {
        String sql = "INSERT INTO users (first_name, last_name, email, password, contact_number, enabled, role_id)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?)";
        long id = 0;
        long userId;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        try (Connection connection = getConnection()) {
            //transaction execution
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
                    throw new SQLException("Creating user is failed, no rows is affected! ");
                }
                generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    userId = generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating user is failed, no id is obtained! ");
                }
                sql = "INSERT INTO carts (id, total, cart_items) VALUES (" + userId + ", 0, 0)";
                preparedStatement = connection.prepareStatement(sql);
                if (preparedStatement.executeUpdate() == 0) {
                    throw new SQLException("Creating user's cart is failed, no rows is affected! ");
                }
                id = userId;
                entity.setId(id);
                connection.commit();
            } catch (SQLException | OnlineStoreLogicalException e) {
                connection.rollback();
                e.printStackTrace();
            } finally {
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public User getById(long id) {
        String sqlForUser = "SELECT id, first_name, last_name, email, password, contact_number, enabled, role_id FROM users"
                + " WHERE id = " + id;
        User user = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sqlForUser);
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
                    user.setRole(getRoleById(roleId, connection, preparedStatement, resultSet));
                    connection.commit();
                }
            } catch (SQLException | OnlineStoreLogicalException e) {
                user = null;
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
        return user;
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
            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException("Creating user is failed, no rows is affected! ");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(User entity) {
        String sqlForOrdersFetching = "SELECT id FROM orders WHERE user_id = " + entity.getId();
        String sqlForOrderItem = "DELETE FROM order_items WHERE order_id = ";
        String sqlForOrder = "DELETE FROM orders WHERE user_id = " + entity.getId();
        String sqlForAddress = "DELETE FROM addresses WHERE user_id = " + entity.getId();
        String sqlForCartItem = "DELETE FROM cart_items WHERE cart_id = " + entity.getId();
        String sqlForCart = "DELETE FROM carts WHERE id = " + entity.getId();
        String sqlForUser = "DELETE FROM users WHERE id = " + entity.getId();
        Statement statement = null;
        ResultSet resultSet;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sqlForOrdersFetching);
                while (resultSet.next()) {
                    statement.addBatch(sqlForOrderItem + resultSet.getLong("id"));
                }
                statement.addBatch(sqlForOrder);
                statement.addBatch(sqlForAddress);
                statement.addBatch(sqlForCartItem);
                statement.addBatch(sqlForCart);
                statement.addBatch(sqlForUser);
                if (statement.executeBatch().length == 0) {
                    throw new SQLException("Deleting user is failed! ");
                }
                connection.commit();
                return true;
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    public static User getUserById(long id, Connection connection, PreparedStatement preparedStatement
            , ResultSet resultSet) throws SQLException, OnlineStoreLogicalException {
        String sql = "SELECT id, first_name, last_name, email, password, contact_number, enabled, role_id FROM users"
                + " WHERE id = " + id;
        User user = null;
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
            user.setRole(getRoleById(roleId, connection, preparedStatement, resultSet));
        }
        return user;
    }
}
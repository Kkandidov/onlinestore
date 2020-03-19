package org.astashonok.onlinestorebackend.dao.daoImpl;

import org.astashonok.onlinestorebackend.dao.CartDAO;
import org.astashonok.onlinestorebackend.dto.Cart;
import org.astashonok.onlinestorebackend.dto.Role;
import org.astashonok.onlinestorebackend.dto.User;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.util.Pool;
import org.astashonok.onlinestorebackend.util.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.Pools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartDAOImpl implements CartDAO {

    private Pool pool;

    public CartDAOImpl(Pool pool) {
        this.pool = pool;
    }

    public CartDAOImpl() {
        pool = Pools.newPool(PoolWithDataSource.class);
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    private Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    @Override
    public Cart getByUser(User user) {
        String sql = "SELECT id, total, cart_items FROM carts WHERE id = " + user.getId();
        Cart cart = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                cart = new Cart();
                cart.setUser(user);
                cart.setTotal(resultSet.getDouble("total"));
                cart.setCartItems(resultSet.getInt("cart_items"));
            }
        } catch (OnlineStoreLogicalException | SQLException e) {
            e.printStackTrace();
        }
        return cart;
    }

    @Override
    public boolean add(Cart entity) {
        //ignore
        // Cart is added automatically when the user is created
        return false;
    }

    @Override
    public Cart getById(long id) {
        String sqlForCart = "SELECT id, total, cart_items FROM carts WHERE id = " + id;
        String sqlForUser = "SELECT id, first_name, last_name, email, password, contact_number, enabled, role_id FROM users"
                + " WHERE id = " + id;
        String sqlForRole = "SELECT id, name, active FROM roles WHERE id = ?";
        Cart cart = null;
        User user;
        Role role;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sqlForCart);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    cart = new Cart();
                    cart.setTotal(resultSet.getDouble("total"));
                    cart.setCartItems(resultSet.getInt("cart_items"));
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
                        preparedStatement = connection.prepareStatement(sqlForRole);
                        preparedStatement.setLong(1, roleId);
                        resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            role = new Role();
                            role.setId(resultSet.getLong("id"));
                            role.setName(resultSet.getString("name"));
                            role.setActive(resultSet.getBoolean("active"));
                            user.setRole(role);
                            cart.setUser(user);
                            connection.commit();
                        }
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
        return cart;
    }

    @Override
    public boolean edit(Cart entity) {
        String sql = "UPDATE carts SET total = ?, cart_items = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(3, entity.getId());
            preparedStatement.setDouble(1, entity.getTotal());
            preparedStatement.setInt(2, entity.getCartItems());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(Cart entity) {
        //ignore
        // Cart is deleted automatically when the user is deleted
        return false;
    }
}

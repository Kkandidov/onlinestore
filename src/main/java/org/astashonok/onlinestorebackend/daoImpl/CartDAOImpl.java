package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dao.CartDAO;
import org.astashonok.onlinestorebackend.dto.Cart;
import org.astashonok.onlinestorebackend.dto.User;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.util.pool.Pool;
import org.astashonok.onlinestorebackend.util.pool.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.pool.Pools;

import java.sql.*;

import static org.astashonok.onlinestorebackend.daoImpl.UserDAOImpl.getUserById;

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
        try (Connection connection = getConnection()
             ; PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                cart = new Cart();
                cart.setUser(user);
                cart.setTotal(resultSet.getDouble("total"));
                cart.setCartItems(resultSet.getInt("cart_items"));
            }
        } catch (OnlineStoreLogicalException | SQLException e) {
            cart = null;
            e.printStackTrace();
        }
        return cart;
    }

    @Override
    public long add(Cart entity) {
        //ignore
        throw new IllegalStateException("Cart is added automatically when the user is created");
    }

    @Override
    public Cart getById(long id) {
        String sql = "SELECT id, total, cart_items FROM carts WHERE id = " + id;
        Cart cart = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    cart = new Cart();
                    cart.setTotal(resultSet.getDouble("total"));
                    cart.setCartItems(resultSet.getInt("cart_items"));
                    cart.setUser(getUserById(id, connection, preparedStatement, resultSet));
                    connection.commit();
                }
            } catch (SQLException | OnlineStoreLogicalException e) {
                cart = null;
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
            if (preparedStatement.executeUpdate() == 0){
                throw new SQLException("Creating cart is failed, no rows is affected! ");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(Cart entity) {
        //ignore
        throw new IllegalStateException("Cart is deleted automatically when the user is deleted");
    }


    public static Cart getCartById(long id, Connection connection, PreparedStatement preparedStatement
            , ResultSet resultSet) throws SQLException, OnlineStoreLogicalException {
        String sql = "SELECT id, total, cart_items FROM carts WHERE id = " + id;
        Cart cart = null;
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            cart = new Cart();
            cart.setTotal(resultSet.getDouble("total"));
            cart.setCartItems(resultSet.getInt("cart_items"));
            cart.setUser(getUserById(id, connection, preparedStatement, resultSet));
        }
        return cart;
    }
}

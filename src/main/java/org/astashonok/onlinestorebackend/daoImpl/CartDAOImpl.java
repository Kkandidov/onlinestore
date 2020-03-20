package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dao.CartDAO;
import org.astashonok.onlinestorebackend.dto.Cart;
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

import static org.astashonok.onlinestorebackend.daoImpl.UserDAOImpl.getUserById;

public class CartDAOImpl implements CartDAO {

    private Pool pool;
    private static final Logger logger = LoggerFactory.getLogger(ClassName.getCurrentClassName());

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
    public Cart getByUser(User user) throws BackendException {
        logger.info("Invoking of getByUser(User user) method: {}", user);
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
            logger.info("The cart was fetched from database: {}", cart);
        } catch (BackendLogicalException e) {
            logger.error("No cart was fetched from database", e);
            throw e;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return cart;
    }

    @Override
    public long add(Cart entity) {
        //ignore
        throw new IllegalStateException("The cart is added automatically when the user is created");
    }

    @Override
    public Cart getById(long id) throws BackendException {
        logger.info("Invoking of getById(long id), id = {}", id);
        Cart cart;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                cart = getCartById(id, connection);
                connection.commit();
                logger.info("The cart was fetched from database: {}", cart);
            } catch (BackendLogicalException e) {
                connection.rollback();
                logger.error("The address wasn't fetched from database", e);
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
        return cart;
    }

    @Override
    public boolean edit(Cart entity) throws BackendException {
        logger.info("Invoking of edit(Cart entity): {}", entity);
        String sql = "UPDATE carts SET total = ?, cart_items = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(3, entity.getId());
            preparedStatement.setDouble(1, entity.getTotal());
            preparedStatement.setInt(2, entity.getCartItems());
            if (preparedStatement.executeUpdate() == 0) {
                logger.error("Updating the cart is failed, no rows is affected");
                throw new DatabaseException("Updating the cart is failed, no rows is affected");
            }
            logger.info("The cart was updated");
            return true;
        } catch (SQLException e) {
            throw new DatabaseException("SQLException", e);
        }
    }

    @Override
    public boolean remove(Cart entity) {
        //ignore
        throw new IllegalStateException("The cart is deleted automatically when the user is deleted");
    }


    static Cart getCartById(long id, Connection connection) throws SQLException, BackendLogicalException {
        String sql = "SELECT id, total, cart_items FROM carts WHERE id = " + id;
        Cart cart = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                cart = new Cart();
                cart.setTotal(resultSet.getDouble("total"));
                cart.setCartItems(resultSet.getInt("cart_items"));
                cart.setUser(getUserById(id, connection));
            }
        }
        return cart;
    }
}

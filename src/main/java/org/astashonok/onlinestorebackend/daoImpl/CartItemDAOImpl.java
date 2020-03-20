package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dao.CartItemDAO;
import org.astashonok.onlinestorebackend.dto.*;
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

import static org.astashonok.onlinestorebackend.daoImpl.CartDAOImpl.getCartById;
import static org.astashonok.onlinestorebackend.daoImpl.ProductDAOImpl.getProductById;

public class CartItemDAOImpl implements CartItemDAO {

    private Pool pool;
    private static final Logger logger = LoggerFactory.getLogger(ClassName.getCurrentClassName());

    public CartItemDAOImpl(Pool pool) {
        this.pool = pool;
    }

    public CartItemDAOImpl() {
        pool = Pools.newPool(PoolWithDataSource.class);
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    private Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    @Override
    public List<CartItem> getByCart(Cart cart) throws BackendException {
        logger.info("Invoking of getByCart(Cart cart) method: {}", cart);
        return get(cart, "SELECT id, cart_id, total, product_id, product_count, product_price, available FROM "
                + "cart_items WHERE cart_id = " + cart.getId());
    }

    @Override
    public List<CartItem> getAvailableByCart(Cart cart) throws BackendException {
        logger.info("Invoking of getAvailableByCart(Cart cart) method: {}", cart);
        return get(cart, "SELECT id, cart_id, total, product_id, product_count, product_price, available FROM "
                + "cart_items WHERE available = 1 AND cart_id = " + cart.getId());
    }

    private List<CartItem> get(Cart cart, String sql) throws BackendException {
        logger.info("Invoking of get(Cart cart, String sql) method: {}, sql = {}", cart, sql);
        List<CartItem> list = new ArrayList<>();
        CartItem cartItem;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    cartItem = new CartItem();
                    cartItem.setId(resultSet.getLong("id"));
                    cartItem.setCart(cart);
                    cartItem.setTotal(resultSet.getDouble("total"));
                    cartItem.setProductCount(resultSet.getInt("product_count"));
                    cartItem.setProductPrice(resultSet.getDouble("product_price"));
                    cartItem.setAvailable(resultSet.getBoolean("available"));
                    long productId = resultSet.getLong("product_id");
                    cartItem.setProduct(getProductById(productId, connection));
                    list.add(cartItem);
                }
                connection.commit();
                logger.info("The cartItems were fetched from database: {}", list);
            } catch (BackendLogicalException e) {
                connection.rollback();
                logger.error("The cartItems weren't fetched from database", e);
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
        return list;
    }

    @Override
    public List<CartItem> getByProduct(Product product) throws BackendException {
        logger.info("Invoking of getByProduct(Product product) method: {}", product);
        String sql = "SELECT id, cart_id, total, product_id, product_count, product_price, available FROM "
                + "cart_items WHERE product_id = " + product.getId();
        List<CartItem> list = new ArrayList<>();
        CartItem cartItem;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    cartItem = new CartItem();
                    cartItem.setId(resultSet.getLong("id"));
                    cartItem.setTotal(resultSet.getDouble("total"));
                    cartItem.setProduct(product);
                    cartItem.setProductCount(resultSet.getInt("product_count"));
                    cartItem.setProductPrice(resultSet.getDouble("product_price"));
                    cartItem.setAvailable(resultSet.getBoolean("available"));
                    long cartId = resultSet.getLong("cart_id");
                    cartItem.setCart(getCartById(cartId, connection));
                    list.add(cartItem);
                }
                connection.commit();
                logger.info("The cartItems were fetched from database: {}", list);
            } catch (BackendLogicalException e) {
                connection.rollback();
                logger.error("The cartItems weren't fetched from database", e);
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
        return list;
    }

    @Override
    public CartItem getByCartAndProduct(Cart cart, Product product) throws BackendException {
        logger.info("Invoking of getByCartAndProduct(Cart cart, Product product) method: {}, {}", cart, product);
        String sql = "SELECT id, cart_id, total, product_id, product_count, product_price, available FROM cart_items "
                + "WHERE cart_id = " + cart.getId() + " AND product_id = " + product.getId();
        CartItem cartItem = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                cartItem = new CartItem();
                cartItem.setId(resultSet.getLong("id"));
                cartItem.setCart(cart);
                cartItem.setTotal(resultSet.getDouble("total"));
                cartItem.setProduct(product);
                cartItem.setProductCount(resultSet.getInt("product_count"));
                cartItem.setProductPrice(resultSet.getDouble("product_price"));
                cartItem.setAvailable(resultSet.getBoolean("available"));
            }
            logger.info("The cartItem was fetched from database: {}", cartItem);
        } catch (BackendLogicalException e) {
            logger.error("The cartItem wasn't fetched from database", e);
            throw e;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return cartItem;
    }

    @Override
    public long add(CartItem entity) throws BackendException {
        logger.info("Invoking of add(CartItem entity) method: {}", entity);
        String sql = "INSERT INTO cart_items (cart_id, total, product_id, product_count, product_price, available)" +
                " VALUES(?, ?, ?, ?, ?, ?)";
        long id;
        ResultSet generatedKeys = null;
        try (Connection connection = getConnection()
             ; PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, entity.getCart().getId());
            preparedStatement.setDouble(2, entity.getTotal());
            preparedStatement.setLong(3, entity.getProduct().getId());
            preparedStatement.setInt(4, entity.getProductCount());
            preparedStatement.setDouble(5, entity.getProductPrice());
            preparedStatement.setBoolean(6, entity.isAvailable());
            if (preparedStatement.executeUpdate() == 0) {
                logger.error("Creating the cartItem is failed, no rows is affected");
                throw new DatabaseException("Creating the cartItem is failed, no rows is affected");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                entity.setId(id);
            } else {
                logger.error("The cartItem key isn't fetched from database");
                throw new DatabaseException("The cartItem key isn't fetched from database");
            }
            logger.info("The cartItem was added, its id = {}", id);
        } catch (BackendLogicalException e) {
            logger.error("The cartItem wasn't added", e);
            throw e;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        } finally {
            if (generatedKeys != null) {
                try {
                    generatedKeys.close();
                } catch (SQLException e) {
                    logger.error("All the resources weren't closed", e);
                }
            }
        }
        return id;
    }

    @Override
    public CartItem getById(long id) throws BackendException {
        logger.info("Invoking of getById(long id), id = {}", id);
        CartItem cartItem;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                cartItem = getCartItemById(id, connection);
                connection.commit();
                logger.info("The cartItem was fetched from database: {}", cartItem);
            } catch (BackendLogicalException e) {
                connection.rollback();
                logger.error("The cartItem wasn't fetched from database", e);
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
        return cartItem;
    }

    @Override
    public boolean edit(CartItem entity) throws BackendException {
        logger.info("Invoking of edit(CartItem entity): {}", entity);
        String sql = "UPDATE cart_items SET cart_id = ?, total = ?, product_id = ?, product_count = ?, product_price = ?"
                + ", available = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(7, entity.getId());
            preparedStatement.setLong(1, entity.getCart().getId());
            preparedStatement.setDouble(2, entity.getTotal());
            preparedStatement.setLong(3, entity.getProduct().getId());
            preparedStatement.setInt(4, entity.getProductCount());
            preparedStatement.setDouble(5, entity.getProductPrice());
            preparedStatement.setBoolean(6, entity.isAvailable());
            if (preparedStatement.executeUpdate() == 0) {
                logger.error("Updating the cartItem is failed, no rows is affected");
                throw new DatabaseException("Updating the cartItem is failed, no rows is affected");
            }
            logger.info("The cartItem was updated");
            return true;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
    }

    @Override
    public boolean remove(CartItem entity) throws BackendException {
        logger.info("Invoking of remove(CartItem entity): {}", entity);
        String sql = "DELETE FROM cart_items WHERE id = " + entity.getId();
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            if (statement.executeUpdate(sql) == 0) {
                logger.error("Deleting the cartItem is failed");
                throw new DatabaseException("Deleting the cartItem is failed");
            }
            logger.info("The cartItem was deleted successfully");
            return true;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
    }

    private static CartItem getCartItemById(long id, Connection connection) throws SQLException, BackendLogicalException {
        String sql = "SELECT id, cart_id, total, product_id, product_count, product_price, available FROM "
                + "cart_items WHERE id = " + id;
        CartItem cartItem = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                cartItem = new CartItem();
                cartItem.setId(resultSet.getLong("id"));
                cartItem.setTotal(resultSet.getDouble("total"));
                cartItem.setProductCount(resultSet.getInt("product_count"));
                cartItem.setProductPrice(resultSet.getDouble("product_price"));
                cartItem.setAvailable(resultSet.getBoolean("available"));
                long cartId = resultSet.getLong("cart_id");
                long productId = resultSet.getLong("product_id");
                cartItem.setCart(getCartById(cartId, connection));
                cartItem.setProduct(getProductById(productId, connection));
            }
        }
        return cartItem;
    }
}
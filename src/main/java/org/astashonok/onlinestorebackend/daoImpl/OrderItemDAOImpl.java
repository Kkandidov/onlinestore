package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dao.OrderItemDAO;
import org.astashonok.onlinestorebackend.dto.Order;
import org.astashonok.onlinestorebackend.dto.OrderItem;
import org.astashonok.onlinestorebackend.dto.Product;
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

import static org.astashonok.onlinestorebackend.daoImpl.OrderDAOImpl.getOrderById;
import static org.astashonok.onlinestorebackend.daoImpl.ProductDAOImpl.getProductById;

public class OrderItemDAOImpl implements OrderItemDAO {

    private Pool pool;
    private static final Logger logger = LoggerFactory.getLogger(ClassName.getCurrentClassName());

    public OrderItemDAOImpl(Pool pool) {
        this.pool = pool;
    }

    public OrderItemDAOImpl() {
        pool = Pools.newPool(PoolWithDataSource.class);
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    private Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    @Override
    public List<OrderItem> getByOrder(Order order) throws BackendException {
        logger.info("Invoking of getByOrder(Order order) method: {}", order);
        String sql = "SELECT id, order_id, total, product_id, product_count, product_price FROM order_items WHERE order_id = " + order.getId();
        OrderItem orderItem;
        List<OrderItem> list = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    orderItem = new OrderItem();
                    orderItem.setId(resultSet.getLong("id"));
                    orderItem.setOrder(order);
                    orderItem.setTotal(resultSet.getDouble("total"));
                    long productId = resultSet.getLong("product_id");
                    orderItem.setProductCount(resultSet.getInt("product_count"));
                    orderItem.setProductPrice(resultSet.getDouble("product_price"));
                    orderItem.setProduct(getProductById(productId, connection));
                    list.add(orderItem);
                }
                connection.commit();
                logger.info("The orderItems were fetched from database: {}", list);
            } catch (BackendLogicalException e) {
                connection.rollback();
                logger.error("The orderItems weren't fetched from database", e);
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
    public List<OrderItem> getByProduct(Product product) throws BackendException {
        logger.info("Invoking of getByProduct(Product product) method: {}", product);
        String sql = "SELECT id, order_id, total, product_id, product_count, product_price FROM order_items WHERE product_id = " + product.getId();
        OrderItem orderItem;
        List<OrderItem> list = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            // transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    orderItem = new OrderItem();
                    orderItem.setId(resultSet.getLong("id"));
                    long orderId = resultSet.getLong("order_id");
                    orderItem.setTotal(resultSet.getDouble("total"));
                    orderItem.setProduct(product);
                    orderItem.setProductCount(resultSet.getInt("product_count"));
                    orderItem.setProductPrice(resultSet.getDouble("product_price"));
                    orderItem.setOrder(getOrderById(orderId, connection));
                    list.add(orderItem);
                }
                connection.commit();
                logger.info("The orderItems were fetched from database: {}", list);
            } catch (BackendLogicalException e) {
                connection.rollback();
                logger.error("The orderItems weren't fetched from database", e);
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
    public OrderItem getByOrderAndProduct(Order order, Product product) throws BackendException {
        logger.info("Invoking of getByOrderAndProduct(Order order, Product product) method: {}, {}", order, product);
        String sql = "SELECT id, order_id, total, product_id, product_count, product_price FROM order_items WHERE "
                + "product_id = " + product.getId() + " AND order_id = " + order.getId();
        OrderItem orderItem = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                orderItem = new OrderItem();
                orderItem.setId(resultSet.getLong("id"));
                orderItem.setOrder(order);
                orderItem.setTotal(resultSet.getDouble("total"));
                orderItem.setProduct(product);
                orderItem.setProductCount(resultSet.getInt("product_count"));
                orderItem.setProductPrice(resultSet.getDouble("product_price"));
            }
            logger.info("The orderItem was fetched from database: {}", orderItem);
        } catch (BackendLogicalException e) {
            logger.error("The orderItem wasn't fetched from database", e);
            throw e;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return orderItem;
    }

    @Override
    public long add(OrderItem entity) throws BackendException {
        logger.info("Invoking of add(OrderItem entity) method: {}", entity);
        String sql = "INSERT INTO order_items (order_id, total, product_id, product_count, product_price)" +
                " VALUES(?, ?, ?, ?, ?)";
        long id;
        ResultSet generatedKeys = null;
        try (Connection connection = getConnection()
             ; PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, entity.getOrder().getId());
            preparedStatement.setDouble(2, entity.getTotal());
            preparedStatement.setLong(3, entity.getProduct().getId());
            preparedStatement.setInt(4, entity.getProductCount());
            preparedStatement.setDouble(5, entity.getProductPrice());
            if (preparedStatement.executeUpdate() == 0) {
                logger.error("Creating the orderItem is failed, no rows is affected");
                throw new DatabaseException("Creating the orderItem is failed, no rows is affected");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                entity.setId(id);
            } else {
                logger.error("The orderItem key isn't fetched from database");
                throw new DatabaseException("The orderItem key isn't fetched from database");
            }
            logger.info("The orderItem was added, its id = {}", id);
        } catch (BackendLogicalException e) {
            logger.error("The address wasn't added", e);
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
    public OrderItem getById(long id) throws BackendException {
        logger.info("Invoking of getById(long id), id = {}", id);
        OrderItem orderItem = null;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                orderItem = getOrderItemById(id, connection);
                connection.commit();
                logger.info("The orderItem was fetched from database: {}", orderItem);
            } catch (BackendLogicalException e) {
                connection.rollback();
                logger.error("The orderItem wasn't fetched from database", e);
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
        return orderItem;
    }

    @Override
    public boolean edit(OrderItem entity) throws BackendException {
        logger.info("Invoking of edit(OrderItem entity): {}", entity);
        String sql = "UPDATE order_items SET order_id = ?, total = ?, product_id = ?, product_count = ?, product_price = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(6, entity.getId());
            preparedStatement.setLong(1, entity.getOrder().getId());
            preparedStatement.setDouble(2, entity.getTotal());
            preparedStatement.setLong(3, entity.getProduct().getId());
            preparedStatement.setInt(4, entity.getProductCount());
            preparedStatement.setDouble(5, entity.getProductPrice());
            if (preparedStatement.executeUpdate() == 0) {
                logger.error("Updating the orderItem is failed, no rows is affected");
                throw new DatabaseException("Updating the orderItem is failed, no rows is affected");
            }
            logger.info("The orderItem was updated");
            return true;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
    }

    @Override
    public boolean remove(OrderItem entity) throws BackendException {
        logger.info("Invoking of remove(OrderItem entity): {}", entity);
        String sql = "DELETE FROM order_items WHERE id = " + entity.getId();
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            if (statement.executeUpdate(sql) == 0) {
                logger.error("Deleting the orderItem is failed");
                throw new DatabaseException("Deleting the orderItem is failed");
            }
            logger.info("The orderItem was deleted successfully");
            return true;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
    }


    private static OrderItem getOrderItemById(long id, Connection connection) throws SQLException, BackendLogicalException {
        String sql = "SELECT id, order_id, total, product_id, product_count, product_price FROM order_items WHERE id = " + id;
        OrderItem orderItem = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                orderItem = new OrderItem();
                orderItem.setId(resultSet.getLong("id"));
                long orderId = resultSet.getLong("order_id");
                orderItem.setTotal(resultSet.getDouble("total"));
                long productId = resultSet.getLong("product_id");
                orderItem.setProductCount(resultSet.getInt("product_count"));
                orderItem.setProductPrice(resultSet.getDouble("product_price"));
                orderItem.setOrder(getOrderById(orderId, connection));
                orderItem.setProduct(getProductById(productId, connection));
            }
        }
        return orderItem;
    }
}

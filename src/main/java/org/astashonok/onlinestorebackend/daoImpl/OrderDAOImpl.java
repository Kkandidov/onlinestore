package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dao.OrderDAO;
import org.astashonok.onlinestorebackend.dto.Address;
import org.astashonok.onlinestorebackend.dto.Order;
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

import static org.astashonok.onlinestorebackend.daoImpl.AddressDAOImpl.getAddressById;
import static org.astashonok.onlinestorebackend.daoImpl.UserDAOImpl.getUserById;

public class OrderDAOImpl implements OrderDAO {

    private Pool pool;
    private static final Logger logger = LoggerFactory.getLogger(ClassName.getCurrentClassName());

    public OrderDAOImpl(Pool pool) {
        this.pool = pool;
    }

    public OrderDAOImpl() {
        pool = Pools.newPool(PoolWithDataSource.class);
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    private Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    @Override
    public List<Order> getByUser(User user) throws BackendException {
        logger.info("Invoking of getByUser(User user) method: {}", user);
        String sql = "SELECT id, user_id, total, count, shipping_id, billing_id, date FROM orders WHERE user_id = " + user.getId();
        Order order;
        List<Order> list = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    order = new Order();
                    order.setId(resultSet.getLong("id"));
                    order.setUser(user);
                    order.setTotal(resultSet.getDouble("total"));
                    order.setCount(resultSet.getInt("count"));
                    order.setDate(new java.util.Date(resultSet.getTimestamp("date").getTime()));
                    long shipping = resultSet.getLong("shipping_id");
                    long billing = resultSet.getLong("billing_id");
                    order.setShipping(getAddressById(shipping, connection));
                    order.setBilling(getAddressById(billing, connection));
                    list.add(order);
                }
                connection.commit();
                logger.info("The orders were fetched from database: {}", list);
            } catch (BackendLogicalException e) {
                connection.rollback();
                logger.error("The orders wasn't fetched from database", e);
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
    public List<Order> getByAddress(Address address) throws BackendException {
        logger.info("Invoking of getByAddress(Address address) method: {}", address);
        String sql = address.isShipping()
                ? "SELECT id, user_id, total, count, shipping_id, billing_id, date FROM orders WHERE shipping_id = " + address.getId()
                : "SELECT id, user_id, total, count, shipping_id, billing_id, date FROM orders WHERE billing_id = " + address.getId();
        Order order;
        List<Order> list = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    order = new Order();
                    long addressId;
                    order.setId(resultSet.getLong("id"));
                    long userId = resultSet.getLong("user_id");
                    order.setTotal(resultSet.getDouble("total"));
                    order.setCount(resultSet.getInt("count"));
                    order.setDate(new java.util.Date(resultSet.getTimestamp("date").getTime()));
                    if (address.isShipping()) {
                        order.setShipping(address);
                        addressId = resultSet.getLong("billing_id");
                        order.setBilling(getAddressById(addressId, connection));
                    } else {
                        order.setBilling(address);
                        addressId = resultSet.getLong("shipping_id");
                        order.setShipping(getAddressById(addressId, connection));
                    }
                    order.setUser(getUserById(userId, connection));
                    list.add(order);
                }
                connection.commit();
                logger.info("The orders were fetched from database: {}", list);
            } catch (BackendLogicalException e) {
                connection.rollback();
                logger.error("The orders weren't fetched from database", e);
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
    public long add(Order entity) throws BackendException {
        logger.info("Invoking of add(Order entity) method: {}", entity);
        String sql = "INSERT INTO orders (user_id, total, count, shipping_id, billing_id, date) VALUES (?, ?, ?, ?, ?, ?)";
        long id;
        ResultSet generatedKeys = null;
        try (Connection connection = getConnection()
             ; PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, entity.getUser().getId());
            preparedStatement.setDouble(2, entity.getTotal());
            preparedStatement.setInt(3, entity.getCount());
            preparedStatement.setLong(4, entity.getShipping().getId());
            preparedStatement.setLong(5, entity.getBilling().getId());
            preparedStatement.setTimestamp(6, new java.sql.Timestamp(entity.getDate().getTime()));
            if (preparedStatement.executeUpdate() == 0) {
                logger.error("Creating the order is failed, no rows is affected");
                throw new DatabaseException("Creating the order is failed, no rows is affected");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                entity.setId(id);
            } else {
                logger.error("The order key isn't fetched from database");
                throw new DatabaseException("The order key isn't fetched from database");
            }
        } catch (BackendLogicalException e) {
            logger.error("The order wasn't added", e);
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
    public Order getById(long id) throws BackendException {
        logger.info("Invoking of getById(long id), id = {}", id);
        Order order;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                order = getOrderById(id, connection);
                connection.commit();
                logger.info("The order was fetched from database: {}", order);
            } catch (BackendLogicalException e) {
                connection.rollback();
                logger.error("The order wasn't fetched from database", e);
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
        return order;
    }

    @Override
    public boolean edit(Order entity) throws BackendException {
        logger.info("Invoking of edit(Order entity): {}", entity);
        String sql = "UPDATE orders SET user_id = ?, total = ?, count = ?, shipping_id = ?, billing_id = ?, date = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(7, entity.getId());
            preparedStatement.setLong(1, entity.getUser().getId());
            preparedStatement.setDouble(2, entity.getTotal());
            preparedStatement.setInt(3, entity.getCount());
            preparedStatement.setLong(4, entity.getShipping().getId());
            preparedStatement.setLong(5, entity.getBilling().getId());
            preparedStatement.setTimestamp(6, new java.sql.Timestamp(entity.getDate().getTime()));
            if (preparedStatement.executeUpdate() == 0) {
                logger.error("Updating the order is failed, no rows is affected");
                throw new DatabaseException("Updating the order is failed, no rows is affected");
            }
            logger.info("The order was updated");
            return true;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
    }

    // ENGINE = InnoDB and ON DELETE CASCADE are used in this project
    @Override
    public boolean remove(Order entity) throws BackendException {
        logger.info("Invoking of remove(Order entity): {}", entity);
        String sql = "DELETE FROM orders WHERE id = " + entity.getId();
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (preparedStatement.executeUpdate() == 0) {
                logger.error("Deleting the order is failed, no rows is affected");
                throw new DatabaseException("Deleting the order is failed, no rows is affected");
            }
            logger.info("The order was deleted successfully");
            return true;
        } catch (SQLException e) {
            logger.error("Deleting the order is failed", e);
            throw new DatabaseException("Deleting order is failed", e);
        }
    }


    static Order getOrderById(long id, Connection connection) throws SQLException, BackendLogicalException {
        String sql = "SELECT id, user_id, total, count, shipping_id, billing_id, date FROM orders WHERE id = " + id;
        Order order = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                order = new Order();
                order.setId(resultSet.getLong("id"));
                long userId = resultSet.getLong("user_id");
                order.setTotal(resultSet.getDouble("total"));
                order.setCount(resultSet.getInt("count"));
                order.setDate(new java.util.Date(resultSet.getTimestamp("date").getTime()));
                long shipping = resultSet.getLong("shipping_id");
                long billing = resultSet.getLong("billing_id");
                order.setShipping(getAddressById(shipping, connection));
                order.setBilling(getAddressById(billing, connection));
                order.setUser(getUserById(userId, connection));
            }
        }
        return order;
    }
}

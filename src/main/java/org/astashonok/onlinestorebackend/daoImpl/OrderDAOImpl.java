package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dao.OrderDAO;
import org.astashonok.onlinestorebackend.dto.Address;
import org.astashonok.onlinestorebackend.dto.Order;
import org.astashonok.onlinestorebackend.dto.User;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.util.pool.Pool;
import org.astashonok.onlinestorebackend.util.pool.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.pool.Pools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.astashonok.onlinestorebackend.daoImpl.AddressDAOImpl.getAddressById;
import static org.astashonok.onlinestorebackend.daoImpl.UserDAOImpl.getUserById;

public class OrderDAOImpl implements OrderDAO {

    private Pool pool;

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
    public List<Order> getByUser(User user) {
        String sql = "SELECT id, user_id, total, count, shipping_id, billing_id, date FROM orders WHERE user_id = " + user.getId();
        Order order;
        List<Order> list = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            // transaction execution
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
                    order.setShipping(getAddressById(shipping, connection, preparedStatement, resultSet));
                    order.setBilling(getAddressById(billing, connection, preparedStatement, resultSet));
                    list.add(order);
                }
                connection.commit();
            } catch (SQLException | OnlineStoreLogicalException e) {
                list = null;
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
        return list;
    }

    @Override
    public List<Order> getByAddress(Address address) {
        String sql = address.isShipping()
                ? "SELECT id, user_id, total, count, shipping_id, billing_id, date FROM orders WHERE shipping_id = " + address.getId()
                : "SELECT id, user_id, total, count, shipping_id, billing_id, date FROM orders WHERE billing_id = " + address.getId();
        Order order;
        List<Order> list = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            // transaction execution
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
                        order.setBilling(getAddressById(addressId, connection, preparedStatement, resultSet));
                    } else {
                        order.setBilling(address);
                        addressId = resultSet.getLong("shipping_id");
                        order.setShipping(getAddressById(addressId, connection, preparedStatement, resultSet));
                    }
                    order.setUser(getUserById(userId, connection, preparedStatement, resultSet));
                    list.add(order);
                }
                connection.commit();
            } catch (SQLException | OnlineStoreLogicalException e) {
                list = null;
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
        return list;
    }

    @Override
    public long add(Order entity) {
        String sql = "INSERT INTO orders (user_id, total, count, shipping_id, billing_id, date) VALUES (?, ?, ?, ?, ?, ?)";
        long id = 0;
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
                throw new SQLException("Creating order is failed, no rows is affected! ");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                entity.setId(id);
            } else {
                throw new SQLException("Creating order is failed, no id is obtained! ");
            }
        } catch (SQLException | OnlineStoreLogicalException e) {
            e.printStackTrace();
        } finally {
            if (generatedKeys != null) {
                try {
                    generatedKeys.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return id;
    }

    @Override
    public Order getById(long id) {
        String sql = "SELECT id, user_id, total, count, shipping_id, billing_id, date FROM orders WHERE id = " + id;
        Order order = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            // transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    order = new Order();
                    order.setId(resultSet.getLong("id"));
                    long userId = resultSet.getLong("user_id");
                    order.setTotal(resultSet.getDouble("total"));
                    order.setCount(resultSet.getInt("count"));
                    order.setDate(new java.util.Date(resultSet.getTimestamp("date").getTime()));
                    long shipping = resultSet.getLong("shipping_id");
                    long billing = resultSet.getLong("billing_id");
                    order.setShipping(getAddressById(shipping, connection, preparedStatement, resultSet));
                    order.setBilling(getAddressById(billing, connection, preparedStatement, resultSet));
                    order.setUser(getUserById(userId, connection, preparedStatement, resultSet));
                }
                connection.commit();
            } catch (SQLException | OnlineStoreLogicalException e) {
                order = null;
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
        return order;
    }

    @Override
    public boolean edit(Order entity) {
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
                throw new SQLException("Creating order is failed, no rows is affected! ");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(Order entity) {
        String sqlForOrderItem = "DELETE FROM order_items WHERE order_id = " + entity.getId();
        String sqlForOrder = "DELETE FROM orders WHERE id = " + entity.getId();
        Statement statement = null;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                statement = connection.createStatement();
                statement.addBatch(sqlForOrderItem);
                statement.addBatch(sqlForOrder);
                if (statement.executeBatch().length == 0) {
                    throw new SQLException("Deleting order is failed! ");
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
            if (statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    public static Order getOrderById(long id, Connection connection, PreparedStatement preparedStatement
            , ResultSet resultSet) throws SQLException, OnlineStoreLogicalException {
        String sql = "SELECT id, user_id, total, count, shipping_id, billing_id, date FROM orders WHERE id = " + id;
        Order order = null;
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            order = new Order();
            order.setId(resultSet.getLong("id"));
            long userId = resultSet.getLong("user_id");
            order.setTotal(resultSet.getDouble("total"));
            order.setCount(resultSet.getInt("count"));
            order.setDate(new java.util.Date(resultSet.getTimestamp("date").getTime()));
            long shipping = resultSet.getLong("shipping_id");
            long billing = resultSet.getLong("billing_id");
            order.setShipping(getAddressById(shipping, connection, preparedStatement, resultSet));
            order.setBilling(getAddressById(billing, connection, preparedStatement, resultSet));
            order.setUser(getUserById(userId, connection, preparedStatement, resultSet));
        }
        return order;
    }
}

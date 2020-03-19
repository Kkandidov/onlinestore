package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dao.OrderItemDAO;
import org.astashonok.onlinestorebackend.dto.Order;
import org.astashonok.onlinestorebackend.dto.OrderItem;
import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.util.pool.Pool;
import org.astashonok.onlinestorebackend.util.pool.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.pool.Pools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.astashonok.onlinestorebackend.daoImpl.OrderDAOImpl.getOrderById;
import static org.astashonok.onlinestorebackend.daoImpl.ProductDAOImpl.getProductById;

public class OrderItemDAOImpl implements OrderItemDAO {

    private Pool pool;

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
    public List<OrderItem> getByOrder(Order order) {
        String sql = "SELECT id, order_id, total, product_id, product_count, product_price FROM order_items WHERE order_id = " + order.getId();
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
                    orderItem.setOrder(order);
                    orderItem.setTotal(resultSet.getDouble("total"));
                    long productId = resultSet.getLong("product_id");
                    orderItem.setProductCount(resultSet.getInt("product_count"));
                    orderItem.setProductPrice(resultSet.getDouble("product_price"));
                    orderItem.setProduct(getProductById(productId, connection, preparedStatement, resultSet));
                    list.add(orderItem);
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
    public List<OrderItem> getByProduct(Product product) {
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
                    orderItem.setOrder(getOrderById(orderId, connection, preparedStatement, resultSet));
                    list.add(orderItem);
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
    public OrderItem getByOrderAndProduct(Order order, Product product) {
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
        } catch (SQLException | OnlineStoreLogicalException e) {
            orderItem = null;
            e.printStackTrace();
        }
        return orderItem;
    }

    @Override
    public long add(OrderItem entity) {
        String sql = "INSERT INTO order_items (order_id, total, product_id, product_count, product_price)" +
                " VALUES(?, ?, ?, ?, ?)";
        long id = 0;
        ResultSet generatedKeys = null;
        try (Connection connection = getConnection()
             ; PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, entity.getOrder().getId());
            preparedStatement.setDouble(2, entity.getTotal());
            preparedStatement.setLong(3, entity.getProduct().getId());
            preparedStatement.setInt(4, entity.getProductCount());
            preparedStatement.setDouble(5, entity.getProductPrice());
            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException("Creating orderItem is failed, no rows is affected! ");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                entity.setId(id);
            } else {
                throw new SQLException("Creating orderItem is failed, no id is obtained! ");
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
    public OrderItem getById(long id) {
        String sql = "SELECT id, order_id, total, product_id, product_count, product_price FROM order_items WHERE id = " + id;
        OrderItem orderItem = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            // transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    orderItem = new OrderItem();
                    orderItem.setId(resultSet.getLong("id"));
                    long orderId = resultSet.getLong("order_id");
                    orderItem.setTotal(resultSet.getDouble("total"));
                    long productId = resultSet.getLong("product_id");
                    orderItem.setProductCount(resultSet.getInt("product_count"));
                    orderItem.setProductPrice(resultSet.getDouble("product_price"));
                    orderItem.setOrder(getOrderById(orderId, connection, preparedStatement, resultSet));
                    orderItem.setProduct(getProductById(productId, connection, preparedStatement, resultSet));
                    connection.commit();
                }
            } catch (SQLException | OnlineStoreLogicalException e) {
                orderItem = null;
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
        return orderItem;
    }

    @Override
    public boolean edit(OrderItem entity) {
        String sql = "UPDATE order_items SET order_id = ?, total = ?, product_id = ?, product_count = ?, product_price = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(6, entity.getId());
            preparedStatement.setLong(1, entity.getOrder().getId());
            preparedStatement.setDouble(2, entity.getTotal());
            preparedStatement.setLong(3, entity.getProduct().getId());
            preparedStatement.setInt(4, entity.getProductCount());
            preparedStatement.setDouble(5, entity.getProductPrice());
            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException("Creating orderItem is failed, no rows is affected! ");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(OrderItem entity) {
        String sql = "DELETE FROM order_items WHERE id = " + entity.getId();
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            if (statement.executeUpdate(sql) == 0) {
                throw new SQLException("Deleting orderItem is failed, no rows is affected! ");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static OrderItem getOrderItemById(long id, Connection connection, PreparedStatement preparedStatement
            , ResultSet resultSet) throws SQLException, OnlineStoreLogicalException {
        String sql = "SELECT id, order_id, total, product_id, product_count, product_price FROM order_items WHERE id = " + id;
        OrderItem orderItem = null;
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            orderItem = new OrderItem();
            orderItem.setId(resultSet.getLong("id"));
            long orderId = resultSet.getLong("order_id");
            orderItem.setTotal(resultSet.getDouble("total"));
            long productId = resultSet.getLong("product_id");
            orderItem.setProductCount(resultSet.getInt("product_count"));
            orderItem.setProductPrice(resultSet.getDouble("product_price"));
            orderItem.setOrder(getOrderById(orderId, connection, preparedStatement, resultSet));
            orderItem.setProduct(getProductById(productId, connection, preparedStatement, resultSet));
        }
        return orderItem;
    }
}

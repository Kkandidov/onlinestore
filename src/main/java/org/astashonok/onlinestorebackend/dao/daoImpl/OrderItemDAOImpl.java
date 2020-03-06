package org.astashonok.onlinestorebackend.dao.daoImpl;

import org.astashonok.onlinestorebackend.dao.OrderItemDAO;
import org.astashonok.onlinestorebackend.dto.Order;
import org.astashonok.onlinestorebackend.dto.OrderItem;
import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.util.Pool;
import org.astashonok.onlinestorebackend.util.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.Pools;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
        return null;
    }

    @Override
    public List<OrderItem> getByProduct(Product product) {
        return null;
    }

    @Override
    public OrderItem getByOrderAndProduct(OrderItem orderItem, Product product) {
        return null;
    }

    @Override
    public boolean add(OrderItem entity) {
        return false;
    }

    @Override
    public Entity getById(long id) {
        return null;
    }

    @Override
    public boolean edit(OrderItem entity) {
        return false;
    }

    @Override
    public boolean remove(OrderItem entity) {
        return false;
    }
}

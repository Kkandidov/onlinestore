package org.astashonok.onlinestorebackend.dao.daoImpl;

import org.astashonok.onlinestorebackend.dao.OrderDAO;
import org.astashonok.onlinestorebackend.dto.Address;
import org.astashonok.onlinestorebackend.dto.Order;
import org.astashonok.onlinestorebackend.dto.User;
import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.util.Pool;
import org.astashonok.onlinestorebackend.util.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.Pools;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
        return null;
    }

    @Override
    public List<Order> getByAddress(Address address) {
        return null;
    }

    @Override
    public boolean add(Order entity) {
        return false;
    }

    @Override
    public Entity getById(long id) {
        return null;
    }

    @Override
    public boolean edit(Order entity) {
        return false;
    }

    @Override
    public boolean remove(Order entity) {
        return false;
    }
}

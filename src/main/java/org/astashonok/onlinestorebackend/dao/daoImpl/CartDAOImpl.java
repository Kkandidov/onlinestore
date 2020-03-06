package org.astashonok.onlinestorebackend.dao.daoImpl;

import org.astashonok.onlinestorebackend.dao.CartDAO;
import org.astashonok.onlinestorebackend.dto.Cart;
import org.astashonok.onlinestorebackend.dto.User;
import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.util.Pool;
import org.astashonok.onlinestorebackend.util.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.Pools;

import java.sql.Connection;
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
        return null;
    }

    @Override
    public boolean add(Cart entity) {
        return false;
    }

    @Override
    public Entity getById(long id) {
        return null;
    }

    @Override
    public boolean edit(Cart entity) {
        return false;
    }

    @Override
    public boolean remove(Cart entity) {
        return false;
    }
}

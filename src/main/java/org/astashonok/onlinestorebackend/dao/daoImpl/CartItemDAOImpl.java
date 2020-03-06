package org.astashonok.onlinestorebackend.dao.daoImpl;

import org.astashonok.onlinestorebackend.dao.CartItemDAO;
import org.astashonok.onlinestorebackend.dto.Cart;
import org.astashonok.onlinestorebackend.dto.CartItem;
import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.util.Pool;
import org.astashonok.onlinestorebackend.util.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.Pools;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CartItemDAOImpl implements CartItemDAO {

    private Pool pool;

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
    public List<CartItem> getByCart(Cart cart) {
        return null;
    }

    @Override
    public List<CartItem> getAvailableByCart() {
        return null;
    }

    @Override
    public List<CartItem> getByProduct(Product product) {
        return null;
    }

    @Override
    public CartItem getByCartAndProduct(Cart cart, Product product) {
        return null;
    }

    @Override
    public boolean add(CartItem entity) {
        return false;
    }

    @Override
    public Entity getById(long id) {
        return null;
    }

    @Override
    public boolean edit(CartItem entity) {
        return false;
    }

    @Override
    public boolean remove(CartItem entity) {
        return false;
    }
}

package org.astashonok.onlinestorebackend.dao.daoImpl;

import org.astashonok.onlinestorebackend.dao.ProductDAO;
import org.astashonok.onlinestorebackend.dto.Brand;
import org.astashonok.onlinestorebackend.dto.Category;
import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.util.Pool;
import org.astashonok.onlinestorebackend.util.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.Pools;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    private Pool pool;

    public ProductDAOImpl(Pool pool) {
        this.pool = pool;
    }

    public ProductDAOImpl() {
        pool = Pools.newPool(PoolWithDataSource.class);
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    private Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public List<Product> getAllActive() {
        return null;
    }

    @Override
    public List<Product> getAllActiveByBrand(Brand brand) {
        return null;
    }

    @Override
    public List<Product> getAllActiveByCategory(Category category) {
        return null;
    }

    @Override
    public boolean add(Product entity) {
        return false;
    }

    @Override
    public Entity getById(long id) {
        return null;
    }

    @Override
    public boolean edit(Product entity) {
        return false;
    }

    @Override
    public boolean remove(Product entity) {
        return false;
    }
}

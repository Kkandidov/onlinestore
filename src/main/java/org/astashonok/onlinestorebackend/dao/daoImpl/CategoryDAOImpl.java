package org.astashonok.onlinestorebackend.dao.daoImpl;

import org.astashonok.onlinestorebackend.dao.CategoryDAO;
import org.astashonok.onlinestorebackend.dto.Category;
import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.util.Pool;
import org.astashonok.onlinestorebackend.util.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.Pools;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {

    private Pool pool;

    public CategoryDAOImpl(Pool pool) {
        this.pool = pool;
    }

    public CategoryDAOImpl() {
        pool = Pools.newPool(PoolWithDataSource.class);
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    private Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    @Override
    public List<Category> getAll() {
        return null;
    }

    @Override
    public List<Category> getAllActive() {
        return null;
    }

    @Override
    public Category getByName(String name) {
        return null;
    }

    @Override
    public boolean add(Category entity) {
        return false;
    }

    @Override
    public Entity getById(long id) {
        return null;
    }

    @Override
    public boolean edit(Category entity) {
        return false;
    }

    @Override
    public boolean remove(Category entity) {
        return false;
    }
}

package org.astashonok.onlinestorebackend.dao.daoImpl;

import org.astashonok.onlinestorebackend.dao.BrandDAO;
import org.astashonok.onlinestorebackend.dto.Brand;
import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.util.Pool;
import org.astashonok.onlinestorebackend.util.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.Pools;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BrandDAOImpl implements BrandDAO {

    private Pool pool;

    public BrandDAOImpl(Pool pool) {
        this.pool = pool;
    }

    public BrandDAOImpl() {
        pool = Pools.newPool(PoolWithDataSource.class);
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    private Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    @Override
    public List<Brand> getAll() {
        return null;
    }

    @Override
    public List<Brand> getAllActive() {
        return null;
    }

    @Override
    public Brand getByName(String name) {
        return null;
    }

    @Override
    public boolean add(Brand entity) {
        return false;
    }

    @Override
    public Entity getById(long id) {
        return null;
    }

    @Override
    public boolean edit(Brand entity) {
        return false;
    }

    @Override
    public boolean remove(Brand entity) {
        return false;
    }
}

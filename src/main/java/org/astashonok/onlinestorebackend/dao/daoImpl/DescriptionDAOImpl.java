package org.astashonok.onlinestorebackend.dao.daoImpl;

import org.astashonok.onlinestorebackend.dao.DescriptionDAO;
import org.astashonok.onlinestorebackend.dto.Description;
import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.util.Pool;
import org.astashonok.onlinestorebackend.util.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.Pools;

import java.sql.Connection;
import java.sql.SQLException;

public class DescriptionDAOImpl implements DescriptionDAO {

    private Pool pool;

    public DescriptionDAOImpl(Pool pool) {
        this.pool = pool;
    }

    public DescriptionDAOImpl() {
        pool = Pools.newPool(PoolWithDataSource.class);
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    private Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    @Override
    public Description getByProduct(Product product) {
        return null;
    }

    @Override
    public boolean add(Description entity) {
        return false;
    }

    @Override
    public Entity getById(long id) {
        return null;
    }

    @Override
    public boolean edit(Description entity) {
        return false;
    }

    @Override
    public boolean remove(Description entity) {
        return false;
    }
}

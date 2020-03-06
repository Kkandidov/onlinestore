package org.astashonok.onlinestorebackend.dao.daoImpl;

import org.astashonok.onlinestorebackend.dao.ViewDAO;
import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.dto.View;
import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.util.Pool;
import org.astashonok.onlinestorebackend.util.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.Pools;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ViewDAOImpl implements ViewDAO {

    private Pool pool;

    public ViewDAOImpl(Pool pool) {
        this.pool = pool;
    }

    public ViewDAOImpl() {
        pool = Pools.newPool(PoolWithDataSource.class);
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    private Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    @Override
    public List<View> getByProduct(Product product) {
        return null;
    }

    @Override
    public boolean add(View entity) {
        return false;
    }

    @Override
    public Entity getById(long id) {
        return null;
    }

    @Override
    public boolean edit(View entity) {
        return false;
    }

    @Override
    public boolean remove(View entity) {
        return false;
    }
}

package org.astashonok.onlinestorebackend.dao.daoImpl;

import org.astashonok.onlinestorebackend.dao.RoleDAO;
import org.astashonok.onlinestorebackend.dto.Role;
import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.util.Pool;
import org.astashonok.onlinestorebackend.util.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.Pools;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RoleDAOImpl implements RoleDAO {

    private Pool pool;

    public RoleDAOImpl(Pool pool) {
        this.pool = pool;
    }

    public RoleDAOImpl() {
        pool = Pools.newPool(PoolWithDataSource.class);
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    private Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    @Override
    public List<Role> getAll() {
        return null;
    }

    @Override
    public List<Role> getAllActive() {
        return null;
    }

    @Override
    public Role getByName(String name) {
        return null;
    }

    @Override
    public boolean add(Role entity) {
        return false;
    }

    @Override
    public Entity getById(long id) {
        return null;
    }

    @Override
    public boolean edit(Role entity) {
        return false;
    }

    @Override
    public boolean remove(Role entity) {
        return false;
    }
}

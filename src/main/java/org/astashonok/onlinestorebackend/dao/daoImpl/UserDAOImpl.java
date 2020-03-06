package org.astashonok.onlinestorebackend.dao.daoImpl;

import org.astashonok.onlinestorebackend.dao.UserDAO;
import org.astashonok.onlinestorebackend.dto.Role;
import org.astashonok.onlinestorebackend.dto.User;
import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.util.Pool;
import org.astashonok.onlinestorebackend.util.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.Pools;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private Pool pool;

    public UserDAOImpl(Pool pool) {
        this.pool = pool;
    }

    public UserDAOImpl() {
        pool = Pools.newPool(PoolWithDataSource.class);
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    private Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    @Override
    public User getByEmail(String email) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public List<User> getAllEnable() {
        return null;
    }

    @Override
    public List<User> getByRole(Role role) {
        return null;
    }

    @Override
    public boolean add(User entity) {
        return false;
    }

    @Override
    public Entity getById(long id) {
        return null;
    }

    @Override
    public boolean edit(User entity) {
        return false;
    }

    @Override
    public boolean remove(User entity) {
        return false;
    }
}

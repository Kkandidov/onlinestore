package org.astashonok.onlinestorebackend.dao.daoImpl;

import org.astashonok.onlinestorebackend.dao.AddressDAO;
import org.astashonok.onlinestorebackend.dto.Address;
import org.astashonok.onlinestorebackend.dto.User;
import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.util.Pool;
import org.astashonok.onlinestorebackend.util.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.Pools;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AddressDAOImpl implements AddressDAO {

    private Pool pool;

    public AddressDAOImpl(Pool pool) {
        this.pool = pool;
    }

    public AddressDAOImpl() {
        pool = Pools.newPool(PoolWithDataSource.class);
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    private Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    @Override
    public Address getBillingAddressByUser(User user) {
        return null;
    }

    @Override
    public List<Address> getShippingAddressesByUser(User user) {
        return null;
    }

    @Override
    public boolean add(Address entity) {
        return false;
    }

    @Override
    public Entity getById(long id) {
        return null;
    }

    @Override
    public boolean edit(Address entity) {
        return false;
    }

    @Override
    public boolean remove(Address entity) {
        return false;
    }
}

package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dao.BrandDAO;
import org.astashonok.onlinestorebackend.dto.Brand;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.util.pool.Pool;
import org.astashonok.onlinestorebackend.util.pool.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.pool.Pools;

import java.sql.*;
import java.util.ArrayList;
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
        String sql = "SELECT id, name, description, active FROM brands";
        List<Brand> brands = new ArrayList<>();
        Brand brand;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                brand = new Brand();
                brand.setId(resultSet.getLong("id"));
                brand.setName(resultSet.getString("name"));
                brand.setDescription(resultSet.getString("description"));
                brand.setActive(resultSet.getBoolean("active"));
                brands.add(brand);
            }
        } catch (SQLException | OnlineStoreLogicalException e) {
            brands = null;
            e.printStackTrace();
        }
        return brands;
    }

    @Override
    public List<Brand> getAllActive() {
        String sql = "SELECT id, name, description, active FROM brands WHERE active = 1";
        List<Brand> brands = new ArrayList<>();
        Brand brand;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                brand = new Brand();
                brand.setId(resultSet.getLong("id"));
                brand.setName(resultSet.getString("name"));
                brand.setDescription(resultSet.getString("description"));
                brand.setActive(resultSet.getBoolean("active"));
                brands.add(brand);
            }
        } catch (SQLException | OnlineStoreLogicalException e) {
            brands = null;
            e.printStackTrace();
        }
        return brands;
    }

    @Override
    public Brand getByName(String name) {
        String sql = "SELECT id, name, description, active FROM brands WHERE name = '" + name + "'";
        Brand brand = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                brand = new Brand();
                brand.setId(resultSet.getLong("id"));
                brand.setName(resultSet.getString("name"));
                brand.setDescription(resultSet.getString("description"));
                brand.setActive(resultSet.getBoolean("active"));
            }
        } catch (SQLException | OnlineStoreLogicalException e) {
            brand = null;
            e.printStackTrace();
        }
        return brand;
    }

    @Override
    public long add(Brand entity) {
        String sql = "INSERT INTO brands (name, description, active) VALUES (?, ?, ?)";
        long id = 0;
        ResultSet generatedKeys = null;
        try (Connection connection = getConnection()
             ; PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setBoolean(3, entity.isActive());
            if (preparedStatement.executeUpdate() == 0){
                throw new SQLException("Creating brand is failed, no rows is affected! ");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                id = generatedKeys.getLong(1);
                entity.setId(id);
            }
            else {
                throw new SQLException("Creating brand is failed, no id is obtained! ");
            }
        } catch (SQLException | OnlineStoreLogicalException e) {
            e.printStackTrace();
        } finally {
            if (generatedKeys != null){
                try {
                    generatedKeys.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return id;
    }

    @Override
    public Brand getById(long id) {
        String sql = "SELECT id, name, description, active FROM brands WHERE id = " + id;
        Brand brand = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                brand = new Brand();
                brand.setId(resultSet.getLong("id"));
                brand.setName(resultSet.getString("name"));
                brand.setDescription(resultSet.getString("description"));
                brand.setActive(resultSet.getBoolean("active"));
            }
        } catch (SQLException | OnlineStoreLogicalException e) {
            brand = null;
            e.printStackTrace();
        }
        return brand;
    }

    @Override
    public boolean edit(Brand entity) {
        String sql = "UPDATE brands SET name = ?,  description = ?, active = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(4, entity.getId());
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setBoolean(3, entity.isActive());
            if (preparedStatement.executeUpdate() == 0){
                throw new SQLException("Creating brand is failed, no rows is affected! ");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(Brand entity) {
        entity.setActive(false);
        return edit(entity);
    }


    public static Brand getBrandById(long id, Connection connection, PreparedStatement preparedStatement
            , ResultSet resultSet) throws SQLException, OnlineStoreLogicalException {
        String sql = "SELECT id, name, description, active FROM brands WHERE id = " + id;
        Brand brand = null;
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            brand = new Brand();
            brand.setId(resultSet.getLong("id"));
            brand.setName(resultSet.getString("name"));
            brand.setDescription(resultSet.getString("description"));
            brand.setActive(resultSet.getBoolean("active"));
        }
        return brand;
    }
}

package org.astashonok.onlinestorebackend.dao.daoImpl;

import org.astashonok.onlinestorebackend.dao.BrandDAO;
import org.astashonok.onlinestorebackend.dto.Brand;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.util.Pool;
import org.astashonok.onlinestorebackend.util.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.Pools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        ResultSet resultSet = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Brand brand = new Brand();
                brand.setId(resultSet.getLong("id"));
                brand.setName(resultSet.getString("name"));
                brand.setDescription(resultSet.getString("description"));
                brand.setActive(resultSet.getBoolean("active"));
                brands.add(brand);
            }
        } catch (SQLException | OnlineStoreLogicalException e) {
            e.printStackTrace();
        }finally {
            if(resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return brands;
    }

    @Override
    public List<Brand> getAllActive() {
        String sql = "SELECT id, name, description, active FROM brands WHERE active = 1";
        List<Brand> brands = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Brand brand = new Brand();
                brand.setId(resultSet.getLong("id"));
                brand.setName(resultSet.getString("name"));
                brand.setDescription(resultSet.getString("description"));
                brand.setActive(resultSet.getBoolean("active"));
                brands.add(brand);
            }
        } catch (SQLException | OnlineStoreLogicalException e) {
            e.printStackTrace();
        }finally {
            if(resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return brands;
    }

    @Override
    public Brand getByName(String name) {
        String sql = "SELECT id, name, description, active FROM brands WHERE name = ?";
        Brand brand = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                brand = new Brand();
                brand.setId(resultSet.getLong("id"));
                brand.setName(resultSet.getString("name"));
                brand.setDescription(resultSet.getString("description"));
                brand.setActive(resultSet.getBoolean("active"));
            }
        } catch (SQLException | OnlineStoreLogicalException e) {
            e.printStackTrace();
        }finally {
            if(resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return brand;
    }

    @Override
    public boolean add(Brand entity) {
        String sql = "INSERT INTO brands (name, description, active) VALUES (?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setBoolean(3, entity.isActive());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Brand getById(long id) {
        String sql = "SELECT id, name, description, active FROM brands WHERE id = ?";
        Brand brand = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                brand = new Brand();
                brand.setId(resultSet.getLong("id"));
                brand.setName(resultSet.getString("name"));
                brand.setDescription(resultSet.getString("description"));
                brand.setActive(resultSet.getBoolean("active"));
            }
        } catch (SQLException | OnlineStoreLogicalException e) {
            e.printStackTrace();
        }finally {
            if(resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
            preparedStatement.executeUpdate();
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
}

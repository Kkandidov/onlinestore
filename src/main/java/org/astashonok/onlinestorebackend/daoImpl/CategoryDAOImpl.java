package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dao.CategoryDAO;
import org.astashonok.onlinestorebackend.dto.Category;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.util.pool.Pool;
import org.astashonok.onlinestorebackend.util.pool.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.pool.Pools;

import java.sql.*;
import java.util.ArrayList;
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
        String sql = "SELECT id, name, active FROM categories";
        List<Category> categories = new ArrayList<>();
        Category category;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                category = new Category();
                category.setId(resultSet.getLong("id"));
                category.setName(resultSet.getString("name"));
                category.setActive(resultSet.getBoolean("active"));
                categories.add(category);
            }
        } catch (SQLException | OnlineStoreLogicalException e) {
            categories = null;
            e.printStackTrace();
        }
        return categories;
    }


    @Override
    public List<Category> getAllActive() {
        String sql = "SELECT id, name, active FROM categories WHERE active = 1";
        List<Category> categories = new ArrayList<>();
        Category category;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                category = new Category();
                category.setId(resultSet.getLong("id"));
                category.setName(resultSet.getString("name"));
                category.setActive(resultSet.getBoolean("active"));
                categories.add(category);
            }
        } catch (SQLException | OnlineStoreLogicalException e) {
            categories = null;
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category getByName(String name) {
        String sql = "SELECT id, name, active FROM categories WHERE name = '" + name + "'";
        Category category = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                category = new Category();
                category.setId(resultSet.getLong("id"));
                category.setName(resultSet.getString("name"));
                category.setActive(resultSet.getBoolean("active"));
            }
        } catch (SQLException | OnlineStoreLogicalException e) {
            category = null;
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public long add(Category entity) {
        String sql = "INSERT INTO categories (name, active) VALUES (?, ?)";
        long id = 0;
        ResultSet generatedKeys = null;
        try (Connection connection = getConnection()
             ; PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setBoolean(2, entity.isActive());
            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException("Creating category is failed, no rows is affected! ");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                entity.setId(id);
            } else {
                throw new SQLException("Creating category is failed, no id is obtained! ");
            }
        } catch (SQLException | OnlineStoreLogicalException e) {
            e.printStackTrace();
        } finally {
            if (generatedKeys != null) {
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
    public Category getById(long id) {
        String sql = "SELECT id, name, active FROM categories WHERE id = " + id;
        Category category = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                category = new Category();
                category.setId(resultSet.getLong("id"));
                category.setName(resultSet.getString("name"));
                category.setActive(resultSet.getBoolean("active"));
            }
        } catch (SQLException | OnlineStoreLogicalException e) {
            category = null;
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public boolean edit(Category entity) {
        String sql = "UPDATE categories SET name = ?,  active = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(3, entity.getId());
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setBoolean(2, entity.isActive());
            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException("Creating category is failed, no rows is affected! ");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(Category entity) {
        entity.setActive(false);
        return edit(entity);
    }

    public static Category getCategoryById(long id, Connection connection, PreparedStatement preparedStatement
            , ResultSet resultSet) throws SQLException, OnlineStoreLogicalException {
        String sql = "SELECT id, name, active FROM categories WHERE id = " + id;
        Category category = null;
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            category = new Category();
            category.setId(resultSet.getLong("id"));
            category.setName(resultSet.getString("name"));
            category.setActive(resultSet.getBoolean("active"));
        }
        return category;
    }
}

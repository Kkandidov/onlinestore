package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dao.CategoryDAO;
import org.astashonok.onlinestorebackend.dto.Category;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendLogicalException;
import org.astashonok.onlinestorebackend.exceptions.technicalexception.DatabaseException;
import org.astashonok.onlinestorebackend.util.ClassName;
import org.astashonok.onlinestorebackend.util.pool.Pool;
import org.astashonok.onlinestorebackend.util.pool.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.pool.Pools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {

    private Pool pool;
    private static final Logger logger = LoggerFactory.getLogger(ClassName.getCurrentClassName());

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
    public List<Category> getAll() throws BackendException {
        logger.info("Invoking of getAll() method");
        return get("SELECT id, name, active FROM categories");
    }

    @Override
    public List<Category> getAllActive() throws BackendException {
        logger.info("Invoking of getAllActive() method");
        return get("SELECT id, name, active FROM categories WHERE active = 1");
    }

    private List<Category> get(String sql) throws BackendException {
        logger.info("Invoking of get(String sql) method: sql = {}", sql);
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
            logger.info("The categories were fetched from database: {}", categories);
        } catch (BackendLogicalException e) {
            logger.error("No categories were fetched from database", e);
            throw e;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return categories;
    }

    @Override
    public Category getByName(String name) throws BackendException {
        logger.info("Invoking of getByName(String name) method: name = {}", name);
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
            logger.info("The category was fetched from database: {}", category);
        } catch (BackendLogicalException e) {
            logger.error("No category was fetched from database", e);
            throw e;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return category;
    }

    @Override
    public long add(Category entity) throws BackendException {
        logger.info("Invoking of add(Category entity) method: {}", entity);
        String sql = "INSERT INTO categories (name, active) VALUES (?, ?)";
        long id;
        ResultSet generatedKeys = null;
        try (Connection connection = getConnection()
             ; PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setBoolean(2, entity.isActive());
            if (preparedStatement.executeUpdate() == 0) {
                logger.error("Creating the category is failed, no rows is affected");
                throw new DatabaseException("Creating the category is failed, no rows is affected");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                entity.setId(id);
            } else {
                logger.error("The category key isn't fetched from database");
                throw new DatabaseException("The category key isn't fetched from database");
            }
            logger.info("The category was added, its id = {}", id);
        } catch (BackendLogicalException e) {
            logger.error("The category wasn't added", e);
            throw e;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        } finally {
            if (generatedKeys != null) {
                try {
                    generatedKeys.close();
                } catch (SQLException e) {
                    logger.error("All the resources weren't closed", e);
                }
            }
        }
        return id;
    }

    @Override
    public Category getById(long id) throws BackendException {
        logger.info("Invoking of getById(long id), id = {}", id);
        Category category;
        try (Connection connection = getConnection()) {
            category = getCategoryById(id, connection);
            logger.info("The category was fetched from database: {}", category);
        } catch (BackendLogicalException e) {
            logger.error("The category wasn't fetched from database", e);
            throw e;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return category;
    }

    @Override
    public boolean edit(Category entity) throws BackendException {
        logger.info("Invoking of edit(Category entity): {}", entity);
        String sql = "UPDATE categories SET name = ?,  active = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(3, entity.getId());
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setBoolean(2, entity.isActive());
            if (preparedStatement.executeUpdate() == 0) {
                logger.error("Updating the category is failed, no rows is affected");
                throw new DatabaseException("Updating the category is failed, no rows is affected");
            }
            logger.info("The category was updated");
            return true;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
    }

    @Override
    public boolean remove(Category entity) throws BackendException {
        logger.info("Invoking of remove(Category entity): {}", entity);
        entity.setActive(false);
        logger.info("Setting the active field to false");
        return edit(entity);
    }

    static Category getCategoryById(long id, Connection connection) throws SQLException, BackendLogicalException {
        String sql = "SELECT id, name, active FROM categories WHERE id = " + id;
        Category category = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                category = new Category();
                category.setId(resultSet.getLong("id"));
                category.setName(resultSet.getString("name"));
                category.setActive(resultSet.getBoolean("active"));
            }
        }
        return category;
    }
}
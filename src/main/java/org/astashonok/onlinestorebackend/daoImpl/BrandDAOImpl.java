package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dao.BrandDAO;
import org.astashonok.onlinestorebackend.dto.Brand;
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

public class BrandDAOImpl implements BrandDAO {

    private Pool pool;
    private static final Logger logger = LoggerFactory.getLogger(ClassName.getCurrentClassName());

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
    public List<Brand> getAll() throws BackendException {
        logger.info("Invoking of getAll() method");
        return get("SELECT id, name, description, active FROM brands");
    }

    @Override
    public List<Brand> getAllActive() throws BackendException {
        logger.info("Invoking of getAllActive() method");
        return get("SELECT id, name, description, active FROM brands WHERE active = 1");
    }

    private List<Brand> get(String sql) throws BackendException {
        logger.info("Invoking of get(String sql) method: sql = {}", sql);
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
            logger.info("The brands were fetched from database: {}", brands);
        } catch (BackendLogicalException e) {
            logger.error("No brands were fetched from database", e);
            throw e;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return brands;
    }


    @Override
    public Brand getByName(String name) throws BackendException {
        logger.info("Invoking of getByName(String name) method: name = {}", name);
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
            logger.info("The brand was fetched from database: {}", brand);
        } catch (BackendLogicalException e) {
            logger.error("No brand was fetched from database", e);
            throw e;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return brand;
    }

    @Override
    public long add(Brand entity) throws BackendException {
        logger.info("Invoking of add(Brand entity) method: {}", entity);
        String sql = "INSERT INTO brands (name, description, active) VALUES (?, ?, ?)";
        long id;
        ResultSet generatedKeys = null;
        try (Connection connection = getConnection()
             ; PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setBoolean(3, entity.isActive());
            if (preparedStatement.executeUpdate() == 0) {
                logger.error("Creating the brand is failed, no rows is affected!");
                throw new DatabaseException("Creating the brand is failed, no rows is affected! ");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                entity.setId(id);
            } else {
                logger.error("The brand key isn't fetched from database! ");
                throw new DatabaseException("The brand key isn't fetched from database! ");
            }
            logger.info("The brand was added, its id = {}", id);
        } catch (BackendLogicalException e) {
            logger.error("The brand wasn't added", e);
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
    public Brand getById(long id) throws BackendException {
        logger.info("Invoking of getById(long id), id = {}", id);
        Brand brand;
        try (Connection connection = getConnection()) {
            brand = getBrandById(id, connection);
            logger.info("The brand was fetched from database: {}", brand);
        } catch (BackendLogicalException e) {
            logger.error("The brand wasn't fetched from database", e);
            throw e;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return brand;
    }

    @Override
    public boolean edit(Brand entity) throws BackendException {
        logger.info("Invoking of edit(Brand entity): {}", entity);
        String sql = "UPDATE brands SET name = ?,  description = ?, active = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(4, entity.getId());
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setBoolean(3, entity.isActive());
            if (preparedStatement.executeUpdate() == 0) {
                logger.error("Updating the brand is failed, no rows is affected");
                throw new DatabaseException("Updating the brand is failed, no rows is affected");
            }
            logger.info("The brand was updated");
            return true;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
    }

    @Override
    public boolean remove(Brand entity) throws BackendException {
        logger.info("Invoking of remove(Brand entity): {}", entity);
        entity.setActive(false);
        logger.info("Setting the active field to false");
        return edit(entity);
    }


    static Brand getBrandById(long id, Connection connection) throws SQLException, BackendLogicalException {
        String sql = "SELECT id, name, description, active FROM brands WHERE id = " + id;
        Brand brand = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                brand = new Brand();
                brand.setId(resultSet.getLong("id"));
                brand.setName(resultSet.getString("name"));
                brand.setDescription(resultSet.getString("description"));
                brand.setActive(resultSet.getBoolean("active"));
            }
        }
        return brand;
    }
}

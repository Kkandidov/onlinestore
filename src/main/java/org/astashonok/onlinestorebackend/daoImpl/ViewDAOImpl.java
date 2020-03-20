package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dao.ViewDAO;
import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.dto.View;
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

import static org.astashonok.onlinestorebackend.daoImpl.ProductDAOImpl.getProductById;

public class ViewDAOImpl implements ViewDAO {

    private Pool pool;
    private static final Logger logger = LoggerFactory.getLogger(ClassName.getCurrentClassName());

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
    public List<View> getByProduct(Product product) throws BackendException {
        logger.info("Invoking of getByProduct(Product product) method: {}", product);
        String sql = "SELECT id, code, product_id FROM views WHERE product_id = " + product.getId();
        View view;
        List<View> views = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                view = new View();
                view.setId(resultSet.getLong("id"));
                view.setCode(resultSet.getString("code"));
                view.setProduct(product);
                views.add(view);
            }
            logger.info("The views were fetched from database: {}", views);
        } catch (BackendLogicalException e) {
            logger.error("No views were fetched from database", e);
            throw e;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return views;
    }

    @Override
    public long add(View entity) throws BackendException {
        logger.info("Invoking of add(View entity) method: {}", entity);
        String sql = "INSERT INTO views (code, product_id) VALUES(?, ?)";
        long id = -1;
        ResultSet generatedKeys = null;
        try (Connection connection = getConnection()
             ; PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getCode());
            preparedStatement.setLong(2, entity.getProduct().getId());
            if (preparedStatement.executeUpdate() == 0) {
                logger.error("Creating the view is failed, no rows is affected");
                throw new DatabaseException("Creating the view is failed, no rows is affected");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                entity.setId(id);
            } else {
                logger.error("The view key isn't fetched from database");
                throw new DatabaseException("The view key isn't fetched from database");
            }
        } catch (BackendLogicalException e) {
            logger.error("The view wasn't added", e);
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
    public View getById(long id) throws BackendException {
        logger.info("Invoking of getById(long id), id = {}", id);
        View view;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                view = getViewById(id, connection);
                logger.info("The view was fetched from database: {}", view);
            } catch (BackendLogicalException e) {
                connection.rollback();
                logger.error("The view wasn't fetched from database", e);
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                logger.error("SQLException", e);
                throw new DatabaseException("SQLException", e);
            }
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return view;
    }

    @Override
    public boolean edit(View entity) throws BackendException {
        logger.info("Invoking of edit(View entity): {}", entity);
        String sql = "UPDATE views SET code = ?, product_id = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(3, entity.getId());
            preparedStatement.setString(1, entity.getCode());
            preparedStatement.setLong(2, entity.getProduct().getId());
            if (preparedStatement.executeUpdate() == 0) {
                logger.error("Updating the view is failed, no rows is affected");
                throw new DatabaseException("Updating the view is failed, no rows is affected");
            }
            logger.info("The view was updated");
            return true;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
    }

    @Override
    public boolean remove(View entity) throws BackendException {
        logger.info("Invoking of remove(View entity): {}", entity);
        String sql = "DELETE FROM views WHERE id = " + entity.getId();
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            if (statement.executeUpdate(sql) == 0) {
                logger.error("Deleting the view is failed, no rows is affected");
                throw new DatabaseException("Deleting the view is failed, no rows is affected");
            }
            logger.info("The view was deleted successfully");
            return true;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
    }


    private static View getViewById(long id, Connection connection) throws SQLException, BackendLogicalException {
        String sql = "SELECT id, code, product_id FROM views WHERE id = " + id;
        View view = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                view = new View();
                view.setId(resultSet.getLong("id"));
                view.setCode(resultSet.getString("code"));
                long productId = resultSet.getLong("product_id");
                view.setProduct(getProductById(productId, connection));
            }
        }
        return view;
    }
}

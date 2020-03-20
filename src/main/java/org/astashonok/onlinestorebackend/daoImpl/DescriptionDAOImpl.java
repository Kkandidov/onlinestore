package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dao.DescriptionDAO;
import org.astashonok.onlinestorebackend.dto.Description;
import org.astashonok.onlinestorebackend.dto.Product;
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

import static org.astashonok.onlinestorebackend.daoImpl.ProductDAOImpl.getProductById;

public class DescriptionDAOImpl implements DescriptionDAO {

    private Pool pool;
    private static final Logger logger = LoggerFactory.getLogger(ClassName.getCurrentClassName());

    public DescriptionDAOImpl(Pool pool) {
        this.pool = pool;
    }

    public DescriptionDAOImpl() {
        pool = Pools.newPool(PoolWithDataSource.class);
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    private Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    @Override
    public Description getByProduct(Product product) throws BackendException {
        logger.info("Invoking of getByProduct(Product product) method: {}", product);
        String sql = "SELECT id, screen, color, processor, front_camera, rear_camera, capacity, battery"
                + ", display_technology, graphics, wireless_communication FROM descriptions WHERE id = " + product.getId();
        Description description = null;
        try (Connection connection = getConnection()
             ; PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                description = new Description();
                description.setProduct(product);
                description.setScreen(resultSet.getString("screen"));
                description.setColor(resultSet.getString("color"));
                description.setProcessor(resultSet.getString("processor"));
                description.setFrontCamera(resultSet.getString("front_camera"));
                description.setRearCamera(resultSet.getString("rear_camera"));
                description.setCapacity(resultSet.getString("capacity"));
                description.setBattery(resultSet.getString("battery"));
                description.setDisplayTechnology(resultSet.getString("display_technology"));
                description.setGraphics(resultSet.getString("graphics"));
                description.setWirelessCommunication(resultSet.getString("wireless_communication"));
            }
            logger.info("The description was fetched from database: {}", description);
        } catch (BackendLogicalException e) {
            logger.error("No description was fetched from database", e);
            throw e;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return description;
    }

    @Override
    public long add(Description entity) {
        // ignore
        throw new IllegalStateException("The product description is added automatically when product is created");
    }

    @Override
    public Description getById(long id) throws BackendException {
        logger.info("Invoking of getById(long id), id = {}", id);
        ResultSet resultSet;
        Description description;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                description = getDescriptionById(id, connection);
                connection.commit();
                logger.info("The description was fetched from database: {}", description);
            } catch (BackendLogicalException e) {
                connection.rollback();
                logger.error("The description wasn't fetched from database", e);
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
        return description;
    }

    @Override
    public boolean edit(Description entity) throws BackendException {
        logger.info("Invoking of edit(Description entity): {}", entity);
        String sql = "UPDATE descriptions SET  screen = ?, color = ?, processor = ?, front_camera = ?"
                + ", rear_camera = ?, capacity = ?, battery = ?, display_technology = ?, graphics = ?"
                + ", wireless_communication = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(11, entity.getId());
            preparedStatement.setString(1, entity.getScreen());
            preparedStatement.setString(2, entity.getColor());
            preparedStatement.setString(3, entity.getProcessor());
            preparedStatement.setString(4, entity.getFrontCamera());
            preparedStatement.setString(5, entity.getRearCamera());
            preparedStatement.setString(6, entity.getCapacity());
            preparedStatement.setString(7, entity.getBattery());
            preparedStatement.setString(8, entity.getDisplayTechnology());
            preparedStatement.setString(9, entity.getGraphics());
            preparedStatement.setString(10, entity.getWirelessCommunication());
            if (preparedStatement.executeUpdate() == 0) {
                logger.error("Updating the description is failed, no rows is affected");
                throw new DatabaseException("Updating the description is failed, no rows is affected");
            }
            logger.info("The description was updated");
            return true;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
    }

    @Override
    public boolean remove(Description entity) {
        //ignore
        throw new IllegalStateException("The product description is deleted automatically when product is deleted");
    }


    private static Description getDescriptionById(long id, Connection connection) throws SQLException, BackendLogicalException {
        String sql = "SELECT id, screen, color, processor, front_camera, rear_camera, capacity, battery"
                + ", display_technology, graphics, wireless_communication FROM descriptions WHERE id = " + id;
        Description description = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                description = new Description();
                description.setScreen(resultSet.getString("screen"));
                description.setColor(resultSet.getString("color"));
                description.setProcessor(resultSet.getString("processor"));
                description.setFrontCamera(resultSet.getString("front_camera"));
                description.setRearCamera(resultSet.getString("rear_camera"));
                description.setCapacity(resultSet.getString("capacity"));
                description.setBattery(resultSet.getString("battery"));
                description.setDisplayTechnology(resultSet.getString("display_technology"));
                description.setGraphics(resultSet.getString("graphics"));
                description.setWirelessCommunication(resultSet.getString("wireless_communication"));
                description.setProduct(getProductById(id, connection));
            }
        }
        return description;
    }
}

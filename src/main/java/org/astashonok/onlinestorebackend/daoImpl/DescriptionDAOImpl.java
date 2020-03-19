package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dao.DescriptionDAO;
import org.astashonok.onlinestorebackend.dto.Description;
import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.util.pool.Pool;
import org.astashonok.onlinestorebackend.util.pool.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.pool.Pools;

import java.sql.*;

import static org.astashonok.onlinestorebackend.daoImpl.ProductDAOImpl.getProductById;

public class DescriptionDAOImpl implements DescriptionDAO {

    private Pool pool;

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
    public Description getByProduct(Product product) {
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
        } catch (SQLException | OnlineStoreLogicalException e) {
            description = null;
            e.printStackTrace();
        }
        return description;
    }

    @Override
    public long add(Description entity) {
        // ignore
        throw new IllegalStateException("A product description is added automatically when product is created");
    }

    @Override
    public Description getById(long id) {
        String sqlForDescription = "SELECT id, screen, color, processor, front_camera, rear_camera, capacity, battery"
                + ", display_technology, graphics, wireless_communication FROM descriptions WHERE id = " + id;
        Description description = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sqlForDescription);
                resultSet = preparedStatement.executeQuery();
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
                    description.setProduct(getProductById(id, connection, preparedStatement, resultSet));
                    connection.commit();
                }
            } catch (SQLException | OnlineStoreLogicalException e) {
                description = null;
                connection.rollback();
                e.printStackTrace();
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return description;
    }

    @Override
    public boolean edit(Description entity) {
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
                throw new SQLException("Creating description is failed, no rows is affected! ");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(Description entity) {
        //ignore
        throw new IllegalStateException("A product description is deleted automatically when product is deleted");
    }


    public static Description getDescriptionById(long id, Connection connection, PreparedStatement preparedStatement
            , ResultSet resultSet) throws SQLException, OnlineStoreLogicalException {
        String sql = "SELECT id, screen, color, processor, front_camera, rear_camera, capacity, battery"
                + ", display_technology, graphics, wireless_communication FROM descriptions WHERE id = " + id;
        Description description = null;
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
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
            description.setProduct(getProductById(id, connection, preparedStatement, resultSet));
        }
        return description;
    }
}

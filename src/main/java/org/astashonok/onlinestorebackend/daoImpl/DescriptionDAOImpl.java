package org.astashonok.onlinestorebackend.dao.daoImpl;

import org.astashonok.onlinestorebackend.dao.DescriptionDAO;
import org.astashonok.onlinestorebackend.dto.Brand;
import org.astashonok.onlinestorebackend.dto.Category;
import org.astashonok.onlinestorebackend.dto.Description;
import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.util.Pool;
import org.astashonok.onlinestorebackend.util.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.Pools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
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
            e.printStackTrace();
        }
        return description;
    }

    @Override
    public boolean add(Description entity) {
        // ignore
        // A product description is added automatically when product is created
        return false;
    }

    @Override
    public Description getById(long id) {
        String sqlForDescription = "SELECT id, screen, color, processor, front_camera, rear_camera, capacity, battery"
                + ", display_technology, graphics, wireless_communication FROM descriptions WHERE id = " + id;
        String sqlForProduct = "SELECT id, name, code, brand_id, unit_price, quantity, active, category_id "
                + "FROM products WHERE id = " + id;
        String sqlForBrand = "SELECT id, name, description, active FROM brands WHERE id = ?";
        String sqlForCategory = "SELECT id, name, active FROM categories WHERE id = ?";
        Description description = null;
        Product product;
        Brand brand;
        Category category;
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
                    preparedStatement = connection.prepareStatement(sqlForProduct);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        product = new Product();
                        product.setId(resultSet.getLong("id"));
                        product.setName(resultSet.getString("name"));
                        product.setCode(resultSet.getString("code"));
                        product.setUnitPrice(resultSet.getDouble("unit_price"));
                        product.setQuantity(resultSet.getInt("quantity"));
                        product.setActive(resultSet.getBoolean("active"));
                        long brandId = resultSet.getLong("brand_id");
                        long categoryId = resultSet.getLong("category_id");
                        preparedStatement = connection.prepareStatement(sqlForBrand);
                        preparedStatement.setLong(1, brandId);
                        resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            brand = new Brand();
                            brand.setId(resultSet.getLong("id"));
                            brand.setName(resultSet.getString("name"));
                            brand.setDescription(resultSet.getString("description"));
                            brand.setActive(resultSet.getBoolean("active"));

                            preparedStatement = connection.prepareStatement(sqlForCategory);
                            preparedStatement.setLong(1, categoryId);
                            resultSet = preparedStatement.executeQuery();
                            if (resultSet.next()) {
                                category = new Category();
                                category.setId(resultSet.getLong("id"));
                                category.setName(resultSet.getString("name"));
                                category.setActive(resultSet.getBoolean("active"));
                                product.setCategory(category);
                                product.setBrand(brand);
                                description.setProduct(product);
                                connection.commit();
                                return description;
                            }
                        }
                    }
                }
            } catch (SQLException | OnlineStoreLogicalException e) {
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
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(Description entity) {
        //ignore
        // A product description is deleted automatically when product is deleted
        return false;
    }
}

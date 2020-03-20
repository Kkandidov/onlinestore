package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dao.ProductDAO;
import org.astashonok.onlinestorebackend.dto.Brand;
import org.astashonok.onlinestorebackend.dto.Category;
import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
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

import static org.astashonok.onlinestorebackend.daoImpl.BrandDAOImpl.getBrandById;
import static org.astashonok.onlinestorebackend.daoImpl.CategoryDAOImpl.getCategoryById;

public class ProductDAOImpl implements ProductDAO {

    private Pool pool;
    private static final Logger logger = LoggerFactory.getLogger(ClassName.getCurrentClassName());

    public ProductDAOImpl(Pool pool) {
        this.pool = pool;
    }

    public ProductDAOImpl() {
        pool = Pools.newPool(PoolWithDataSource.class);
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    private Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    @Override
    public List<Product> getAll() throws BackendException {
        logger.info("Invoking of getAll() method");
        return get(null, "SELECT id, name, code, brand_id, unit_price, quantity, active, category_id FROM products");
    }

    @Override
    public List<Product> getAllActive() throws BackendException {
        logger.info("Invoking of getAllActive() method");
        return get(null, "SELECT id, name, code, brand_id, unit_price, quantity, active, category_id FROM "
                + "products WHERE active = 1");
    }

    @Override
    public List<Product> getAllActiveByBrand(Brand brand) throws BackendException {
        logger.info("Invoking of getAllActiveByBrand(Brand brand) method: {}", brand);
        return get(brand, "SELECT id, name, code, brand_id, unit_price, quantity, active, category_id FROM "
                + "products WHERE active = 1 AND brand_id = " + brand.getId());
    }

    @Override
    public List<Product> getAllActiveByCategory(Category category) throws BackendException {
        logger.info("Invoking of getAllActiveByCategory(Category category) method: {}", category);
        return get(category, "SELECT id, name, code, brand_id, unit_price, quantity, active, category_id FROM "
                + "products WHERE active = 1 AND category_id = " + category.getId());
    }

    private List<Product> get(Entity entity, String sql) throws BackendException {
        logger.info("Invoking of get(String sql) method: sql = {}", sql);
        Product product;
        List<Product> list = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                if (entity == null) {
                    while (resultSet.next()) {
                        product = new Product();
                        product.setId(resultSet.getLong("id"));
                        product.setName(resultSet.getString("name"));
                        product.setCode(resultSet.getString("code"));
                        product.setUnitPrice(resultSet.getDouble("unit_price"));
                        product.setQuantity(resultSet.getInt("quantity"));
                        product.setActive(resultSet.getBoolean("active"));
                        long brandId = resultSet.getLong("brand_id");
                        long categoryId = resultSet.getLong("category_id");
                        product.setBrand(getBrandById(brandId, connection));
                        product.setCategory(getCategoryById(categoryId, connection));
                        list.add(product);
                    }
                } else if (entity.getClass() == Brand.class) {
                    while (resultSet.next()) {
                        product = new Product();
                        product.setId(resultSet.getLong("id"));
                        product.setName(resultSet.getString("name"));
                        product.setCode(resultSet.getString("code"));
                        product.setBrand((Brand) entity);
                        product.setUnitPrice(resultSet.getDouble("unit_price"));
                        product.setQuantity(resultSet.getInt("quantity"));
                        product.setActive(resultSet.getBoolean("active"));
                        long categoryId = resultSet.getLong("category_id");
                        product.setCategory(getCategoryById(categoryId, connection));
                        list.add(product);
                    }
                } else if (entity.getClass() == Category.class) {
                    while (resultSet.next()) {
                        product = new Product();
                        product.setId(resultSet.getLong("id"));
                        product.setName(resultSet.getString("name"));
                        product.setCode(resultSet.getString("code"));
                        product.setUnitPrice(resultSet.getDouble("unit_price"));
                        product.setQuantity(resultSet.getInt("quantity"));
                        product.setActive(resultSet.getBoolean("active"));
                        product.setCategory((Category) entity);
                        long brandId = resultSet.getLong("brand_id");
                        product.setBrand(getBrandById(brandId, connection));
                        list.add(product);
                    }
                }
                connection.commit();
                logger.info("The products were fetched from database: {}", list);
            } catch (BackendLogicalException e) {
                connection.rollback();
                logger.error("The products weren't fetched from database", e);
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                logger.error("SQLException", e);
                throw new DatabaseException("SQLException", e);
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return list;
    }

    @Override
    public long add(Product entity) throws BackendException {
        logger.info("Invoking of add(Product entity) method: {}", entity);
        String sql = "INSERT INTO products (name, code, brand_id, unit_price, quantity, active"
                + ", category_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        long id;
        long productId;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, entity.getName());
                preparedStatement.setString(2, entity.getCode());
                preparedStatement.setLong(3, entity.getBrand().getId());
                preparedStatement.setDouble(4, entity.getUnitPrice());
                preparedStatement.setInt(5, entity.getQuantity());
                preparedStatement.setBoolean(6, entity.isActive());
                preparedStatement.setLong(7, entity.getCategory().getId());
                if (preparedStatement.executeUpdate() == 0) {
                    logger.error("Creating the product is failed, no rows is affected");
                    throw new DatabaseException("Creating the product is failed, no rows is affected");
                }
                generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    productId = generatedKeys.getLong(1);
                } else {
                    logger.error("The product key isn't fetched from database");
                    throw new DatabaseException("The product key isn't fetched from database");
                }
                sql = "INSERT INTO descriptions (id, screen, color, processor, front_camera"
                        + ", rear_camera, capacity, battery, display_technology, graphics, wireless_communication)"
                        + "VALUES(?, '', '', '', '', '', '', '', '', '', '')";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1, productId);
                if (preparedStatement.executeUpdate() == 0) {
                    logger.error("Creating the description is failed, no rows is affected");
                    throw new DatabaseException("Creating the description is failed, no rows is affected");
                }
                id = productId;
                entity.setId(id);
                connection.commit();
                logger.info("The product was added, its id = {}", id);
            } catch (BackendLogicalException e) {
                logger.error("The product wasn't added", e);
                throw e;
            } catch (SQLException e) {
                logger.error("SQLException", e);
                throw new DatabaseException("SQLException", e);
            } finally {
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
        return id;
    }

    @Override
    public Product getById(long id) throws BackendException {
        logger.info("Invoking of getById(long id), id = {}", id);
        Product product;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                product = getProductById(id, connection);
                connection.commit();
                logger.info("The product was fetched from database: {}", product);
            } catch (BackendLogicalException e) {
                connection.rollback();
                logger.error("The product wasn't fetched from database", e);
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
        return product;
    }

    @Override
    public boolean edit(Product entity) throws BackendException {
        logger.info("Invoking of edit(Product entity): {}", entity);
        String sql = "UPDATE products SET name = ?, code = ?, brand_id = ?, unit_price = ?, quantity = ?, active = ?"
                + ", category_id = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(8, entity.getId());
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getCode());
            preparedStatement.setLong(3, entity.getBrand().getId());
            preparedStatement.setDouble(4, entity.getUnitPrice());
            preparedStatement.setInt(5, entity.getQuantity());
            preparedStatement.setBoolean(6, entity.isActive());
            preparedStatement.setLong(7, entity.getCategory().getId());
            if (preparedStatement.executeUpdate() == 0) {
                logger.error("Updating the product is failed, no rows is affected");
                throw new DatabaseException("Updating the product is failed, no rows is affected");
            }
            logger.info("The product was updated");
            return true;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            throw new DatabaseException("SQLException", e);
        }
    }

    @Override
    public boolean remove(Product entity) throws BackendException {
        logger.info("Invoking of remove(Product entity): {}", entity);
        entity.setActive(false);
        logger.info("Setting the active field to false");
        return edit(entity);
    }


    static Product getProductById(long id, Connection connection) throws SQLException, BackendLogicalException {
        String sql = "SELECT id, name, code, brand_id, unit_price, quantity, active, category_id "
                + "FROM products WHERE id = " + id;
        Product product = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)
             ; ResultSet resultSet = preparedStatement.executeQuery()) {
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
                product.setCategory(getCategoryById(categoryId, connection));
                product.setBrand(getBrandById(brandId, connection));
            }
        }
        return product;
    }
}

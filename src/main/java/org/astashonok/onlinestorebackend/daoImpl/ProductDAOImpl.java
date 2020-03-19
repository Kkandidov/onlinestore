package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dao.ProductDAO;
import org.astashonok.onlinestorebackend.dto.Brand;
import org.astashonok.onlinestorebackend.dto.Category;
import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.util.pool.Pool;
import org.astashonok.onlinestorebackend.util.pool.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.pool.Pools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.astashonok.onlinestorebackend.daoImpl.BrandDAOImpl.getBrandById;
import static org.astashonok.onlinestorebackend.daoImpl.CategoryDAOImpl.getCategoryById;

public class ProductDAOImpl implements ProductDAO {

    private Pool pool;

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
    public List<Product> getAll() {
        String sql = "SELECT id, name, code, brand_id, unit_price, quantity, active, category_id FROM products";
        Product product;
        List<Product> list = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
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
                    product.setBrand(getBrandById(brandId, connection, preparedStatement, resultSet));
                    product.setCategory(getCategoryById(categoryId, connection, preparedStatement, resultSet));
                    list.add(product);
                }
                connection.commit();
            } catch (SQLException | OnlineStoreLogicalException e) {
                list = null;
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
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Product> getAllActive() {
        String sql = "SELECT id, name, code, brand_id, unit_price, quantity, active, category_id FROM "
                + "products WHERE active = 1";
        Product product;
        List<Product> list = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
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
                    product.setBrand(getBrandById(brandId, connection, preparedStatement, resultSet));
                    product.setCategory(getCategoryById(categoryId, connection, preparedStatement, resultSet));
                    list.add(product);
                }
                connection.commit();
            } catch (SQLException | OnlineStoreLogicalException e) {
                list = null;
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
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Product> getAllActiveByBrand(Brand brand) {
        String sql = "SELECT id, name, code, brand_id, unit_price, quantity, active, category_id FROM "
                + "products WHERE active = 1 AND brand_id = " + brand.getId();
        Product product;
        List<Product> list = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    product = new Product();
                    product.setId(resultSet.getLong("id"));
                    product.setName(resultSet.getString("name"));
                    product.setCode(resultSet.getString("code"));
                    product.setBrand(brand);
                    product.setUnitPrice(resultSet.getDouble("unit_price"));
                    product.setQuantity(resultSet.getInt("quantity"));
                    product.setActive(resultSet.getBoolean("active"));
                    long categoryId = resultSet.getLong("category_id");
                    product.setCategory(getCategoryById(categoryId, connection, preparedStatement, resultSet));
                    list.add(product);
                }
                connection.commit();
            } catch (SQLException | OnlineStoreLogicalException e) {
                list = null;
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
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Product> getAllActiveByCategory(Category category) {
        String sql = "SELECT id, name, code, brand_id, unit_price, quantity, active, category_id FROM "
                + "products WHERE active = 1 AND category_id = " + category.getId();
        Product product;
        List<Product> list = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    product = new Product();
                    product.setId(resultSet.getLong("id"));
                    product.setName(resultSet.getString("name"));
                    product.setCode(resultSet.getString("code"));
                    product.setUnitPrice(resultSet.getDouble("unit_price"));
                    product.setQuantity(resultSet.getInt("quantity"));
                    product.setActive(resultSet.getBoolean("active"));
                    product.setCategory(category);
                    long brandId = resultSet.getLong("brand_id");
                    product.setBrand(getBrandById(brandId, connection, preparedStatement, resultSet));
                    list.add(product);
                }
                connection.commit();
            } catch (SQLException | OnlineStoreLogicalException e) {
                list = null;
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
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public long add(Product entity) {
        String sql = "INSERT INTO products (name, code, brand_id, unit_price, quantity, active"
                + ", category_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        long id = 0;
        long productId;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        try (Connection connection = getConnection()) {
            //transaction execution
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
                    throw new SQLException("Creating user is failed, no rows is affected! ");
                }
                generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    productId = generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating user is failed, no id is obtained! ");
                }
                sql = "INSERT INTO descriptions (id, screen, color, processor, front_camera"
                        + ", rear_camera, capacity, battery, display_technology, graphics, wireless_communication)"
                        + "VALUES(?, '', '', '', '', '', '', '', '', '', '')";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1, productId);
                if (preparedStatement.executeUpdate() == 0) {
                    throw new SQLException("Creating user's cart is failed, no rows is affected! ");
                }
                id = productId;
                entity.setId(id);
                connection.commit();
            } catch (SQLException | OnlineStoreLogicalException e) {
                connection.rollback();
                e.printStackTrace();
            } finally {
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public Product getById(long id) {
        String sql = "SELECT id, name, code, brand_id, unit_price, quantity, active, category_id "
                + "FROM products WHERE id = " + id;
        Product product = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sql);
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
                    product.setBrand(getBrandById(brandId, connection, preparedStatement, resultSet));
                    product.setCategory(getCategoryById(categoryId, connection, preparedStatement, resultSet));
                    connection.commit();
                }
            } catch (SQLException | OnlineStoreLogicalException e) {
                product = null;
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
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public boolean edit(Product entity) {
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
                throw new SQLException("Creating product is failed, no rows is affected! ");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(Product entity) {
        entity.setActive(false);
        return edit(entity);
    }


    public static Product getProductById(long id, Connection connection, PreparedStatement preparedStatement
            , ResultSet resultSet) throws SQLException, OnlineStoreLogicalException {
        String sql = "SELECT id, name, code, brand_id, unit_price, quantity, active, category_id "
                + "FROM products WHERE id = " + id;
        Product product = null;
        preparedStatement = connection.prepareStatement(sql);
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
            product.setCategory(getCategoryById(categoryId, connection, preparedStatement, resultSet));
            product.setBrand(getBrandById(brandId, connection, preparedStatement, resultSet));
        }
        return product;
    }
}

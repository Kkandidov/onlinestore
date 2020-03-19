package org.astashonok.onlinestorebackend.dao.daoImpl;

import org.astashonok.onlinestorebackend.dao.ProductDAO;
import org.astashonok.onlinestorebackend.dto.Brand;
import org.astashonok.onlinestorebackend.dto.Category;
import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.util.Pool;
import org.astashonok.onlinestorebackend.util.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.Pools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String sqlForProduct = "SELECT id, name, code, brand_id, unit_price, quantity, active, category_id FROM products";
        String sqlForBrand = "SELECT id, name, description, active FROM brands WHERE id = ?";
        String sqlForCategory = "SELECT id, name, active FROM categories WHERE id = ?";
        List<Product> list = new ArrayList<>();
        Product product;
        Brand brand;
        Category category;
        Map<Product, Tuple<Long, Long>> map = new HashMap<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sqlForProduct);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    product = new Product();
                    product.setId(resultSet.getLong("id"));
                    product.setName(resultSet.getString("name"));
                    product.setCode(resultSet.getString("code"));
                    product.setUnitPrice(resultSet.getDouble("unit_price"));
                    product.setQuantity(resultSet.getInt("quantity"));
                    product.setActive(resultSet.getBoolean("active"));
                    Long brandId = resultSet.getLong("brand_id");
                    Long categoryId = resultSet.getLong("category_id");
                    map.put(product, new Tuple<>(brandId, categoryId));
                }
                for (Map.Entry<Product, Tuple<Long, Long>> entry : map.entrySet()) {
                    preparedStatement = connection.prepareStatement(sqlForBrand);
                    preparedStatement.setLong(1, entry.getValue().getFirstValue());
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        brand = new Brand();
                        brand.setId(resultSet.getLong("id"));
                        brand.setName(resultSet.getString("name"));
                        brand.setDescription(resultSet.getString("description"));
                        brand.setActive(resultSet.getBoolean("active"));

                        preparedStatement = connection.prepareStatement(sqlForCategory);
                        preparedStatement.setLong(1, entry.getValue().getSecondValue());
                        resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            category = new Category();
                            category.setId(resultSet.getLong("id"));
                            category.setName(resultSet.getString("name"));
                            category.setActive(resultSet.getBoolean("active"));

                            entry.getKey().setBrand(brand);
                            entry.getKey().setCategory(category);
                            list.add(entry.getKey());
                        }
                    }
                }
                connection.commit();
                return list;
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
        return null;
    }

    @Override
    public List<Product> getAllActive() {
        String sqlForProduct = "SELECT id, name, code, brand_id, unit_price, quantity, active, category_id FROM "
                + "products WHERE active = 1";
        String sqlForBrand = "SELECT id, name, description, active FROM brands WHERE id = ?";
        String sqlForCategory = "SELECT id, name, active FROM categories WHERE id = ?";
        List<Product> list = new ArrayList<>();
        Product product;
        Brand brand;
        Category category;
        Map<Product, Tuple<Long, Long>> map = new HashMap<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sqlForProduct);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    product = new Product();
                    product.setId(resultSet.getLong("id"));
                    product.setName(resultSet.getString("name"));
                    product.setCode(resultSet.getString("code"));
                    product.setUnitPrice(resultSet.getDouble("unit_price"));
                    product.setQuantity(resultSet.getInt("quantity"));
                    product.setActive(resultSet.getBoolean("active"));
                    Long brandId = resultSet.getLong("brand_id");
                    Long categoryId = resultSet.getLong("category_id");
                    map.put(product, new Tuple<>(brandId, categoryId));
                }
                for (Map.Entry<Product, Tuple<Long, Long>> entry : map.entrySet()) {
                    preparedStatement = connection.prepareStatement(sqlForBrand);
                    preparedStatement.setLong(1, entry.getValue().getFirstValue());
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        brand = new Brand();
                        brand.setId(resultSet.getLong("id"));
                        brand.setName(resultSet.getString("name"));
                        brand.setDescription(resultSet.getString("description"));
                        brand.setActive(resultSet.getBoolean("active"));

                        preparedStatement = connection.prepareStatement(sqlForCategory);
                        preparedStatement.setLong(1, entry.getValue().getSecondValue());
                        resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            category = new Category();
                            category.setId(resultSet.getLong("id"));
                            category.setName(resultSet.getString("name"));
                            category.setActive(resultSet.getBoolean("active"));

                            entry.getKey().setBrand(brand);
                            entry.getKey().setCategory(category);
                            list.add(entry.getKey());
                        }
                    }
                }
                connection.commit();
                return list;
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
        return null;
    }

    @Override
    public List<Product> getAllActiveByBrand(Brand brand) {
        String sqlForProduct = "SELECT id, name, code, brand_id, unit_price, quantity, active, category_id FROM "
                + "products WHERE active = 1 AND brand_id = " + brand.getId();
        String sqlForCategory = "SELECT id, name, active FROM categories WHERE id = ?";
        List<Product> list = new ArrayList<>();
        Product product;
        Category category;
        Map<Product, Long> map = new HashMap<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sqlForProduct);
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
                    Long categoryId = resultSet.getLong("category_id");
                    map.put(product, categoryId);
                }
                preparedStatement = connection.prepareStatement(sqlForCategory);
                for (Map.Entry<Product, Long> entry : map.entrySet()) {
                    preparedStatement.setLong(1, entry.getValue());
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        category = new Category();
                        category.setId(resultSet.getLong("id"));
                        category.setName(resultSet.getString("name"));
                        category.setActive(resultSet.getBoolean("active"));
                        product = entry.getKey();
                        product.setCategory(category);
                        list.add(product);
                    }
                }
                connection.commit();
                return list;
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
        return null;
    }

    @Override
    public List<Product> getAllActiveByCategory(Category category) {
        String sqlForProduct = "SELECT id, name, code, brand_id, unit_price, quantity, active, category_id FROM "
                + "products WHERE active = 1 AND category_id = " + category.getId();
        String sqlForBrand = "SELECT id, name, description, active FROM brands WHERE id = ?";
        List<Product> list = new ArrayList<>();
        Product product;
        Brand brand;
        Map<Product, Long> map = new HashMap<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sqlForProduct);
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
                    Long brandId = resultSet.getLong("brand_id");
                    map.put(product, brandId);
                }
                preparedStatement = connection.prepareStatement(sqlForBrand);
                for (Map.Entry<Product, Long> entry : map.entrySet()) {
                    preparedStatement.setLong(1, entry.getValue());
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        brand = new Brand();
                        brand.setId(resultSet.getLong("id"));
                        brand.setName(resultSet.getString("name"));
                        brand.setDescription(resultSet.getString("description"));
                        brand.setActive(resultSet.getBoolean("active"));

                        product = entry.getKey();
                        product.setBrand(brand);
                        list.add(product);
                    }
                }
                connection.commit();
                return list;
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
        return null;
    }

    @Override
    public boolean add(Product entity) {
        String sqlForProductCreation = "INSERT INTO products (name, code, brand_id, unit_price, quantity, active"
                + ", category_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlForProductFetching = "SELECT id FROM products WHERE code = '" + entity.getCode() + "'";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sqlForProductCreation);
                preparedStatement.setString(1, entity.getName());
                preparedStatement.setString(2, entity.getCode());
                preparedStatement.setLong(3, entity.getBrand().getId());
                preparedStatement.setDouble(4, entity.getUnitPrice());
                preparedStatement.setInt(5, entity.getQuantity());
                preparedStatement.setBoolean(6, entity.isActive());
                preparedStatement.setLong(7, entity.getCategory().getId());
                preparedStatement.executeUpdate();
                preparedStatement = connection.prepareStatement(sqlForProductFetching);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String sqlForDescriptionCreation = "INSERT INTO descriptions (id, screen, color, processor, front_camera"
                            + ", rear_camera, capacity, battery, display_technology, graphics, wireless_communication)"
                            + "VALUES(?, '', '', '', '', '', '', '', '', '', '')";
                    preparedStatement = connection.prepareStatement(sqlForDescriptionCreation);
                    preparedStatement.setLong(1, resultSet.getLong("id"));
                    preparedStatement.executeUpdate();
                    connection.commit();
                    return true;
                }
            } catch (SQLException e) {
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
        return false;
    }

    @Override
    public Product getById(long id) {
        String sqlForProduct = "SELECT id, name, code, brand_id, unit_price, quantity, active, category_id "
                + "FROM products WHERE id = " + id;
        String sqlForBrand = "SELECT id, name, description, active FROM brands WHERE id = ?";
        String sqlForCategory = "SELECT id, name, active FROM categories WHERE id = ?";
        Product product;
        Brand brand;
        Category category;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
            connection.setAutoCommit(false);
            try {
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
                            connection.commit();
                            return product;
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
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return null;
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
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //?
    @Override
    public boolean remove(Product entity) {
        return false;
    }

    private class Tuple<A, B> {
        public final A firstValue;
        public final B secondValue;

        public Tuple(A first, B second) {
            this.firstValue = first;
            this.secondValue = second;
        }

        public A getFirstValue() {
            return firstValue;
        }

        public B getSecondValue() {
            return secondValue;
        }
    }
}

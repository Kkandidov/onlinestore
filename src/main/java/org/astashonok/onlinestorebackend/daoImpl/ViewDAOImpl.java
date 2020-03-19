package org.astashonok.onlinestorebackend.dao.daoImpl;

import org.astashonok.onlinestorebackend.dao.ViewDAO;
import org.astashonok.onlinestorebackend.dto.Brand;
import org.astashonok.onlinestorebackend.dto.Category;
import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.dto.View;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.util.Pool;
import org.astashonok.onlinestorebackend.util.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.Pools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewDAOImpl implements ViewDAO {

    private Pool pool;

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
    public List<View> getByProduct(Product product) {
        String sql = "SELECT id, code, product_id FROM views WHERE product_id = " + product.getId();
        List<View> views = new ArrayList<>();
        View view;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                view = new View();
                view.setId(resultSet.getLong("id"));
                view.setCode(resultSet.getString("code"));
                view.setProduct(product);
                views.add(view);
            }
        } catch (SQLException | OnlineStoreLogicalException e) {
            e.printStackTrace();
        }
        return views;
    }

    @Override
    public boolean add(View entity) {
        String sql = "INSERT INTO views (code, product_id) VALUES(?, ?)";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getCode());
            preparedStatement.setLong(2, entity.getProduct().getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public View getById(long id) {
        String sqlForView = "SELECT id, code, product_id FROM views WHERE id = " + id;
        String sqlForProduct = "SELECT id, name, code, brand_id, unit_price, quantity, active, category_id "
                + "FROM products WHERE id = ?";
        String sqlForBrand = "SELECT id, name, description, active FROM brands WHERE id = ?";
        String sqlForCategory = "SELECT id, name, active FROM categories WHERE id = ?";
        View view = null;
        Product product;
        Brand brand;
        Category category;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            //transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sqlForView);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    view = new View();
                    view.setId(resultSet.getLong("id"));
                    view.setCode(resultSet.getString("code"));
                    long productId = resultSet.getLong("product_id");

                    preparedStatement = connection.prepareStatement(sqlForProduct);
                    preparedStatement.setLong(1, productId);
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
                        if(resultSet.next()){
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
                                view.setProduct(product);
                                connection.commit();
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
        return view;
    }

    @Override
    public boolean edit(View entity) {
        String sql = "UPDATE views SET code = ?, product_id = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(3, entity.getId());
            preparedStatement.setString(1, entity.getCode());
            preparedStatement.setLong(2, entity.getProduct().getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(View entity) {
        String sql = "DELETE FROM views WHERE id = " + entity.getId();
        try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

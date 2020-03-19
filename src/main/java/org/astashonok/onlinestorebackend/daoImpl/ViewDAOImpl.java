package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dao.ViewDAO;
import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.dto.View;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.util.pool.Pool;
import org.astashonok.onlinestorebackend.util.pool.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.pool.Pools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.astashonok.onlinestorebackend.daoImpl.ProductDAOImpl.getProductById;

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
        } catch (SQLException | OnlineStoreLogicalException e) {
            views = null;
            e.printStackTrace();
        }
        return views;
    }

    @Override
    public long add(View entity) {
        String sql = "INSERT INTO views (code, product_id) VALUES(?, ?)";
        long id = 0;
        ResultSet generatedKeys = null;
        try (Connection connection = getConnection()
             ; PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getCode());
            preparedStatement.setLong(2, entity.getProduct().getId());
            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException("Creating address is failed, no rows is affected! ");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
                entity.setId(id);
            } else {
                throw new SQLException("Creating address is failed, no id is obtained! ");
            }
        } catch (SQLException | OnlineStoreLogicalException e) {
            e.printStackTrace();
        } finally {
            if (generatedKeys != null){
                try {
                    generatedKeys.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return id;
    }

    @Override
    public View getById(long id) {
        String sqlForView = "SELECT id, code, product_id FROM views WHERE id = " + id;
        View view = null;
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
                    view.setProduct(getProductById(productId, connection, preparedStatement, resultSet));
                }
            } catch (SQLException | OnlineStoreLogicalException e) {
                view = null;
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
            if (preparedStatement.executeUpdate() == 0) {
                throw new SQLException("Creating view is failed, no rows is affected! ");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(View entity) {
        String sql = "DELETE FROM views WHERE id = " + entity.getId();
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            if (statement.executeUpdate(sql) == 0) {
                throw new SQLException("Deleting views is failed, no rows is affected! ");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static View getViewById(long id, Connection connection, PreparedStatement preparedStatement
            , ResultSet resultSet) throws SQLException, OnlineStoreLogicalException {
        String sql = "SELECT id, code, product_id FROM views WHERE id = " + id;
        View view = null;
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            view = new View();
            view.setId(resultSet.getLong("id"));
            view.setCode(resultSet.getString("code"));
            long productId = resultSet.getLong("product_id");
            view.setProduct(getProductById(productId, connection, preparedStatement, resultSet));
        }
        return view;
    }
}

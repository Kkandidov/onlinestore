package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dao.CartItemDAO;
import org.astashonok.onlinestorebackend.dto.*;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.util.pool.Pool;
import org.astashonok.onlinestorebackend.util.pool.PoolWithDataSource;
import org.astashonok.onlinestorebackend.util.pool.Pools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.astashonok.onlinestorebackend.daoImpl.CartDAOImpl.getCartById;
import static org.astashonok.onlinestorebackend.daoImpl.ProductDAOImpl.getProductById;

public class CartItemDAOImpl implements CartItemDAO {

    private Pool pool;

    public CartItemDAOImpl(Pool pool) {
        this.pool = pool;
    }

    public CartItemDAOImpl() {
        pool = Pools.newPool(PoolWithDataSource.class);
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    private Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    @Override
    public List<CartItem> getByCart(Cart cart) {
        String sql = "SELECT id, cart_id, total, product_id, product_count, product_price, available FROM "
                + "cart_items WHERE cart_id = " + cart.getId();
        List<CartItem> list = new ArrayList<>();
        CartItem cartItem;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            // transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    cartItem = new CartItem();
                    cartItem.setId(resultSet.getLong("id"));
                    cartItem.setCart(cart);
                    cartItem.setTotal(resultSet.getDouble("total"));
                    cartItem.setProductCount(resultSet.getInt("product_count"));
                    cartItem.setProductPrice(resultSet.getDouble("product_price"));
                    cartItem.setAvailable(resultSet.getBoolean("available"));
                    long productId = resultSet.getLong("product_id");
                    cartItem.setProduct(getProductById(productId, connection, preparedStatement, resultSet));
                    list.add(cartItem);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<CartItem> getAvailableByCart(Cart cart) {
        String sql = "SELECT id, cart_id, total, product_id, product_count, product_price, available FROM "
                + "cart_items WHERE available = 1 AND cart_id = " + cart.getId();
        List<CartItem> list = new ArrayList<>();
        CartItem cartItem;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            // transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    cartItem = new CartItem();
                    cartItem.setId(resultSet.getLong("id"));
                    cartItem.setCart(cart);
                    cartItem.setTotal(resultSet.getDouble("total"));
                    cartItem.setProductCount(resultSet.getInt("product_count"));
                    cartItem.setProductPrice(resultSet.getDouble("product_price"));
                    cartItem.setAvailable(resultSet.getBoolean("available"));
                    long productId = resultSet.getLong("product_id");
                    cartItem.setProduct(getProductById(productId, connection, preparedStatement, resultSet));
                    list.add(cartItem);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<CartItem> getByProduct(Product product) {
        String sqlForCartItem = "SELECT id, cart_id, total, product_id, product_count, product_price, available FROM "
                + "cart_items WHERE product_id = " + product.getId();
        List<CartItem> list = new ArrayList<>();
        CartItem cartItem;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            // transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sqlForCartItem);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    cartItem = new CartItem();
                    cartItem.setId(resultSet.getLong("id"));
                    cartItem.setTotal(resultSet.getDouble("total"));
                    cartItem.setProduct(product);
                    cartItem.setProductCount(resultSet.getInt("product_count"));
                    cartItem.setProductPrice(resultSet.getDouble("product_price"));
                    cartItem.setAvailable(resultSet.getBoolean("available"));
                    long cartId = resultSet.getLong("cart_id");
                    cartItem.setCart(getCartById(cartId, connection, preparedStatement, resultSet));
                    list.add(cartItem);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public CartItem getByCartAndProduct(Cart cart, Product product) {
        String sql = "SELECT id, cart_id, total, product_id, product_count, product_price, available FROM cart_items "
                + "WHERE cart_id = " + cart.getId() + " AND product_id = " + product.getId();
        CartItem cartItem = null;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                cartItem = new CartItem();
                cartItem.setId(resultSet.getLong("id"));
                cartItem.setCart(cart);
                cartItem.setTotal(resultSet.getDouble("total"));
                cartItem.setProduct(product);
                cartItem.setProductCount(resultSet.getInt("product_count"));
                cartItem.setProductPrice(resultSet.getDouble("product_price"));
                cartItem.setAvailable(resultSet.getBoolean("available"));
            }
        } catch (SQLException | OnlineStoreLogicalException e) {
            cartItem = null;
            e.printStackTrace();
        }
        return cartItem;
    }

    @Override
    public long add(CartItem entity) {
        String sql = "INSERT INTO cart_items (cart_id, total, product_id, product_count, product_price, available)" +
                " VALUES(?, ?, ?, ?, ?, ?)";
        long id = 0;
        ResultSet generatedKeys = null;
        try (Connection connection = getConnection()
             ; PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, entity.getCart().getId());
            preparedStatement.setDouble(2, entity.getTotal());
            preparedStatement.setLong(3, entity.getProduct().getId());
            preparedStatement.setInt(4, entity.getProductCount());
            preparedStatement.setDouble(5, entity.getProductPrice());
            preparedStatement.setBoolean(6, entity.isAvailable());
            if (preparedStatement.executeUpdate() == 0){
                throw new SQLException("Creating cartItem is failed, no rows is affected! ");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                id = generatedKeys.getLong(1);
                entity.setId(id);
            }
            else {
                throw new SQLException("Creating cartItem is failed, no id is obtained! ");
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
    public CartItem getById(long id) {
        String sqlForCartItem = "SELECT id, cart_id, total, product_id, product_count, product_price, available FROM "
                + "cart_items WHERE id = " + id;
        CartItem cartItem = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = getConnection()) {
            // transaction execution
            connection.setAutoCommit(false);
            try {
                preparedStatement = connection.prepareStatement(sqlForCartItem);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    cartItem = new CartItem();
                    cartItem.setId(resultSet.getLong("id"));
                    cartItem.setTotal(resultSet.getDouble("total"));
                    cartItem.setProductCount(resultSet.getInt("product_count"));
                    cartItem.setProductPrice(resultSet.getDouble("product_price"));
                    cartItem.setAvailable(resultSet.getBoolean("available"));
                    long cartId = resultSet.getLong("cart_id");
                    long productId = resultSet.getLong("product_id");
                    cartItem.setCart(getCartById(cartId, connection, preparedStatement, resultSet));
                    cartItem.setProduct(getProductById(productId, connection, preparedStatement, resultSet));
                }
                connection.commit();
            } catch (SQLException | OnlineStoreLogicalException e) {
                cartItem = null;
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
        return cartItem;
    }

    @Override
    public boolean edit(CartItem entity) {
        String sql = "UPDATE cart_items SET cart_id = ?, total = ?, product_id = ?, product_count = ?, product_price = ?"
                + ", available = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(7, entity.getId());
            preparedStatement.setLong(1, entity.getCart().getId());
            preparedStatement.setDouble(2, entity.getTotal());
            preparedStatement.setLong(3, entity.getProduct().getId());
            preparedStatement.setInt(4, entity.getProductCount());
            preparedStatement.setDouble(5, entity.getProductPrice());
            preparedStatement.setBoolean(6, entity.isAvailable());
            if (preparedStatement.executeUpdate() == 0){
                throw new SQLException("Creating cartItem is failed, no rows is affected! ");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(CartItem entity) {
        String sql = "DELETE FROM cart_items WHERE id = " + entity.getId();
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            if (statement.executeUpdate(sql) == 0) {
                throw new SQLException("Deleting cartItem is failed, no rows is affected! ");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static CartItem getCartItemById(long id, Connection connection, PreparedStatement preparedStatement
            , ResultSet resultSet) throws SQLException, OnlineStoreLogicalException {
        String sql = "SELECT id, cart_id, total, product_id, product_count, product_price, available FROM "
                + "cart_items WHERE id = " + id;
        CartItem cartItem = null;
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            cartItem = new CartItem();
            cartItem.setId(resultSet.getLong("id"));
            cartItem.setTotal(resultSet.getDouble("total"));
            cartItem.setProductCount(resultSet.getInt("product_count"));
            cartItem.setProductPrice(resultSet.getDouble("product_price"));
            cartItem.setAvailable(resultSet.getBoolean("available"));
            long cartId = resultSet.getLong("cart_id");
            long productId = resultSet.getLong("product_id");
            cartItem.setCart(getCartById(cartId, connection, preparedStatement, resultSet));
            cartItem.setProduct(getProductById(productId, connection, preparedStatement, resultSet));
        }
        return cartItem;
    }
}

package org.astashonok.onlinestorebackend.dao.daoImpl;

import org.astashonok.onlinestorebackend.dao.CartItemDAO;
import org.astashonok.onlinestorebackend.dto.*;
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
        String sqlForCartItem = "SELECT id, cart_id, total, product_id, product_count, product_price, available FROM "
                + "cart_items WHERE cart_id = " + cart.getId();
        String sqlForProduct = "SELECT id, name, code, brand_id, unit_price, quantity, active, category_id "
                + "FROM products WHERE id = ?";
        String sqlForBrand = "SELECT id, name, description, active FROM brands WHERE id = ?";
        String sqlForCategory = "SELECT id, name, active FROM categories WHERE id = ?";
        List<CartItem> list = new ArrayList<>();
        CartItem cartItem;
        Product product;
        Brand brand;
        Category category;
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
                    cartItem.setCart(cart);
                    cartItem.setTotal(resultSet.getDouble("total"));
                    cartItem.setProductCount(resultSet.getInt("product_count"));
                    cartItem.setProductPrice(resultSet.getDouble("product_price"));
                    cartItem.setAvailable(resultSet.getBoolean("available"));
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
                                product.setBrand(brand);
                                product.setCategory(category);
                                cartItem.setProduct(product);
                                list.add(cartItem);
                                connection.commit();
                            }
                        }
                    }
                }
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
    public List<CartItem> getAvailableByCart(Cart cart) {
        String sqlForCartItem = "SELECT id, cart_id, total, product_id, product_count, product_price, available FROM "
                + "cart_items WHERE available = 1 AND cart_id = " + cart.getId();
        String sqlForProduct = "SELECT id, name, code, brand_id, unit_price, quantity, active, category_id "
                + "FROM products WHERE id = ?";
        String sqlForBrand = "SELECT id, name, description, active FROM brands WHERE id = ?";
        String sqlForCategory = "SELECT id, name, active FROM categories WHERE id = ?";
        List<CartItem> list = new ArrayList<>();
        CartItem cartItem;
        Product product;
        Brand brand;
        Category category;
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
                    cartItem.setCart(cart);
                    cartItem.setTotal(resultSet.getDouble("total"));
                    cartItem.setProductCount(resultSet.getInt("product_count"));
                    cartItem.setProductPrice(resultSet.getDouble("product_price"));
                    cartItem.setAvailable(resultSet.getBoolean("available"));
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
                                product.setBrand(brand);
                                product.setCategory(category);
                                cartItem.setProduct(product);
                                list.add(cartItem);
                            }
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
    public List<CartItem> getByProduct(Product product) {
        String sqlForCartItem = "SELECT id, cart_id, total, product_id, product_count, product_price, available FROM "
                + "cart_items WHERE product_id = " + product.getId();
        String sqlForCart = "SELECT id, total, cart_items FROM carts WHERE id = ?";
        String sqlForUser = "SELECT id, first_name, last_name, email, password, contact_number, enabled, role_id FROM users"
                + " WHERE id = ?";
        String sqlForRole = "SELECT id, name, active FROM roles WHERE id = ?";
        List<CartItem> list = new ArrayList<>();
        CartItem cartItem;
        Cart cart;
        User user;
        Role role;
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
                    preparedStatement = connection.prepareStatement(sqlForCart);
                    preparedStatement.setLong(1, cartId);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        cart = new Cart();
                        cart.setTotal(resultSet.getDouble("total"));
                        cart.setCartItems(resultSet.getInt("cart_items"));
                        long userId = resultSet.getLong("id");
                        preparedStatement = connection.prepareStatement(sqlForUser);
                        preparedStatement.setLong(1, userId);
                        resultSet = preparedStatement.executeQuery();
                        if (resultSet != null) {
                            user = new User();
                            user.setId(resultSet.getLong("id"));
                            user.setFirstName(resultSet.getString("first_name"));
                            user.setLastName(resultSet.getString("last_name"));
                            user.setEmail(resultSet.getString("email"));
                            user.setPassword(resultSet.getString("password"));
                            user.setContactNumber(resultSet.getString("contact_number"));
                            user.setEnabled(resultSet.getBoolean("enabled"));
                            long roleId = resultSet.getLong("role_id");
                            preparedStatement = connection.prepareStatement(sqlForRole);
                            preparedStatement.setLong(1, roleId);
                            resultSet = preparedStatement.executeQuery();
                            if (resultSet.next()) {
                                role = new Role();
                                role.setId(resultSet.getLong("id"));
                                role.setName(resultSet.getString("name"));
                                role.setActive(resultSet.getBoolean("active"));
                                user.setRole(role);
                                cart.setUser(user);
                                cartItem.setCart(cart);
                                list.add(cartItem);
                            }
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
            e.printStackTrace();
        }
        return cartItem;
    }

    @Override
    public boolean add(CartItem entity) {
        String sql = "INSERT INTO cart_items (cart_id, total, product_id, product_count, product_price, available)" +
                " VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, entity.getCart().getId());
            preparedStatement.setDouble(2, entity.getTotal());
            preparedStatement.setLong(3, entity.getProduct().getId());
            preparedStatement.setInt(4, entity.getProductCount());
            preparedStatement.setDouble(5, entity.getProductPrice());
            preparedStatement.setBoolean(6, entity.isAvailable());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public CartItem getById(long id) {
        String sqlForCartItem = "SELECT id, cart_id, total, product_id, product_count, product_price, available FROM "
                + "cart_items WHERE id = " + id;
        String sqlForCart = "SELECT id, total, cart_items FROM carts WHERE id = ?";
        String sqlForUser = "SELECT id, first_name, last_name, email, password, contact_number, enabled, role_id FROM users"
                + " WHERE id = ?";
        String sqlForRole = "SELECT id, name, active FROM roles WHERE id = ?";
        String sqlForProduct = "SELECT id, name, code, brand_id, unit_price, quantity, active, category_id "
                + "FROM products WHERE id = ?";
        String sqlForBrand = "SELECT id, name, description, active FROM brands WHERE id = ?";
        String sqlForCategory = "SELECT id, name, active FROM categories WHERE id = ?";
        CartItem cartItem = null;
        Product product;
        Brand brand;
        Category category;
        Cart cart;
        User user;
        Role role;
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
                                preparedStatement = connection.prepareStatement(sqlForCart);
                                preparedStatement.setLong(1, cartId);
                                resultSet = preparedStatement.executeQuery();
                                if (resultSet.next()) {
                                    cart = new Cart();
                                    cart.setTotal(resultSet.getDouble("total"));
                                    cart.setCartItems(resultSet.getInt("cart_items"));
                                    long userId = resultSet.getLong("id");
                                    preparedStatement = connection.prepareStatement(sqlForUser);
                                    preparedStatement.setLong(1, userId);
                                    resultSet = preparedStatement.executeQuery();
                                    if (resultSet.next()) {
                                        user = new User();
                                        user.setId(resultSet.getLong("id"));
                                        user.setFirstName(resultSet.getString("first_name"));
                                        user.setLastName(resultSet.getString("last_name"));
                                        user.setEmail(resultSet.getString("email"));
                                        user.setPassword(resultSet.getString("password"));
                                        user.setContactNumber(resultSet.getString("contact_number"));
                                        user.setEnabled(resultSet.getBoolean("enabled"));
                                        long roleId = resultSet.getLong("role_id");
                                        preparedStatement = connection.prepareStatement(sqlForRole);
                                        preparedStatement.setLong(1, roleId);
                                        resultSet = preparedStatement.executeQuery();
                                        if (resultSet.next()) {
                                            role = new Role();
                                            role.setId(resultSet.getLong("id"));
                                            role.setName(resultSet.getString("name"));
                                            role.setActive(resultSet.getBoolean("active"));
                                            user.setRole(role);
                                            cart.setUser(user);
                                            product.setCategory(category);
                                            product.setBrand(brand);
                                            cartItem.setCart(cart);
                                            cartItem.setProduct(product);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                connection.commit();
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
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(CartItem entity) {
        return false;
    }
}

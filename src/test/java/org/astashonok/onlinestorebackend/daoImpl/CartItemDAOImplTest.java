package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dto.Cart;
import org.astashonok.onlinestorebackend.dto.CartItem;
import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.testconfig.SimpleSingleConnection;
import org.astashonok.onlinestorebackend.util.pool.Pools;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.astashonok.onlinestorebackend.testconfig.StaticInitializerDTO.*;
import static org.junit.Assert.*;

public class CartItemDAOImplTest {

    private static CartItemDAOImpl cartItemDAO;
    private static CartDAOImpl cartDAO;
    private static ProductDAOImpl productDAO;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        cartItemDAO = new CartItemDAOImpl(Pools.newPool(SimpleSingleConnection.class));
        cartDAO = new CartDAOImpl(Pools.newPool(SimpleSingleConnection.class));
        productDAO = new ProductDAOImpl(Pools.newPool(SimpleSingleConnection.class));
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destruction of our object! ");
        cartItemDAO = null;
        cartDAO = null;
        productDAO = null;
    }

    @Test
    public void getByCart() {
        int expected = 2;
        Cart cart = cartDAO.getById(2);
        int actual = cartItemDAO.getByCart(cart).size();
        assertEquals(expected, actual);
    }

    @Test
    public void getAvailableByCart() {
        int expected = 2;
        Cart cart = cartDAO.getById(2);
        int actual = cartItemDAO.getAvailableByCart(cart).size();
        assertEquals(expected, actual);
        CartItem cartItem = cartItemDAO.getById(1);
        cartItem.setAvailable(false);
        assertTrue(cartItemDAO.edit(cartItem));
        expected = 1;
        actual = cartItemDAO.getAvailableByCart(cart).size();
        assertEquals(expected, actual);

        //database reset
        cartItem.setAvailable(true);
        assertTrue(cartItemDAO.edit(cartItem));
    }

    @Test
    public void getByProduct() {
        CartItem expected = cartItem22;
        Product product = productDAO.getById(2);
        CartItem actual = cartItemDAO.getByProduct(product).get(0);
        assertEquals(expected, actual);
    }

    @Test
    public void getByCartAndProduct() {
        CartItem expected = cartItem21;
        Cart cart = cartDAO.getById(2);
        Product product = productDAO.getById(1);
        CartItem actual = cartItemDAO.getByCartAndProduct(cart, product);
        assertEquals(expected, actual);
    }

    @Test
    public void add() throws SQLException {
        Product product = productDAO.getById(2);
        Cart cart = cartDAO.getById(3);
        CartItem expected = new CartItem(cart, 686, product, 1, 710, true);
        long id = cartItemDAO.add(expected);
        CartItem actual =  cartItemDAO.getById(id);
        int expect = 2;
        int act = cartItemDAO.getByCart(cart).size();
        assertEquals(expect, act);

        // database reset
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        String sql = "DELETE FROM cart_items WHERE id = " + id;
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Test
    public void getById() {
        CartItem expected = cartItem31;
        CartItem actual = cartItemDAO.getById(3);
        assertEquals(expected, actual);
    }

    @Test
    public void edit() {
        CartItem expected = cartItemDAO.getById(1);
        expected.setAvailable(false);
        assertTrue(cartItemDAO.edit(expected));
        CartItem actual = cartItemDAO.getById(1);
        assertEquals(expected, actual);

        //database reset
        expected.setAvailable(true);
        assertTrue(cartItemDAO.edit(expected));
    }

    @Test
    public void remove() {
        CartItem expected = new CartItem(cart3, 1029, product1, 3, 343
                , true);
        long id = cartItemDAO.add(expected);
        CartItem actual = cartItemDAO.getById(id);
        assertEquals(expected, actual);
        assertTrue(cartItemDAO.remove(actual));
    }
}
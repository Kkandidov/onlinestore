package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dto.CartItem;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;
import org.astashonok.onlinestorebackend.testconfig.SimpleSingleConnection;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.astashonok.onlinestorebackend.testconfig.StaticTestInitializer.*;
import static org.junit.Assert.*;

public class CartItemDAOImplTest {

    @After
    public void resetDatabase() throws SQLException {
        System.out.println("Reset database...");
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.addBatch("SET FOREIGN_KEY_CHECKS=0");
        statement.addBatch("TRUNCATE TABLE cart_items");
        statement.addBatch("SET FOREIGN_KEY_CHECKS=1");
        statement.addBatch("INSERT INTO cart_items(cart_id, total, product_id, product_count, product_price, available)"
                + "VALUES(2, 800, 1, 2, 400, true)");
        statement.addBatch("INSERT INTO cart_items(cart_id, total, product_id, product_count, product_price, available)"
                + "VALUES(2, 710, 2, 1, 710, true)");
        statement.addBatch("INSERT INTO cart_items(cart_id, total, product_id, product_count, product_price, available)"
                + "VALUES(3, 1029, 3, 3, 343, true)");
        statement.executeBatch();
        connection.commit();
        System.out.println("Resetting is successfully!");
    }

    @Test
    public void getByCart() throws BackendException {
        int expected = 2;
        int actual = cartItemDAO.getByCart(cart2).size();
        assertEquals(expected, actual);
    }

    @Test
    public void getAvailableByCart() throws BackendException {
        CartItem cartItem = cartItem21;
        cartItem.setAvailable(false);
        cartItemDAO.edit(cartItem);
        int expected = 1;
        int actual = cartItemDAO.getAvailableByCart(cart2).size();
        assertEquals(expected, actual);
        cartItem.setAvailable(true);
    }

    @Test
    public void getByProduct() throws BackendException {
        CartItem expected = cartItem22;
        CartItem actual = cartItemDAO.getByProduct(product2).get(0);
        assertEquals(expected, actual);
    }

    @Test
    public void getByCartAndProduct() throws BackendException {
        CartItem expected = cartItem21;
        CartItem actual = cartItemDAO.getByCartAndProduct(cart2, product1);
        assertEquals(expected, actual);
    }

    @Test
    public void add() throws BackendException {
        CartItem expected = new CartItem(cart3, 686, product2, 1, 710, true);
        long id = cartItemDAO.add(expected);
        CartItem actual = cartItemDAO.getById(id);
        assertEquals(expected, actual);
    }

    @Test
    public void getById() throws BackendException {
        CartItem expected = cartItem31;
        CartItem actual = cartItemDAO.getById(3);
        assertEquals(expected, actual);
    }

    @Test
    public void edit() throws BackendException {
        CartItem expected = cartItem21;
        expected.setAvailable(false);
        cartItemDAO.edit(expected);
        CartItem actual = cartItemDAO.getById(1);
        assertEquals(expected, actual);
        expected.setAvailable(true);
    }

    @Test
    public void remove() throws BackendException {
        cartItemDAO.remove(cartItem21);
        assertNull(cartItemDAO.getById(1));
    }
}
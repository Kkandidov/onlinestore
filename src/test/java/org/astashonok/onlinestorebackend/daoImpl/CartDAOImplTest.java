package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dto.Cart;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;
import org.astashonok.onlinestorebackend.testconfig.SimpleSingleConnection;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.astashonok.onlinestorebackend.testconfig.StaticTestInitializer.*;
import static org.junit.Assert.*;

public class CartDAOImplTest {

    @After
    public void resetDatabase() throws SQLException {
        System.out.println("Reset database...");
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.addBatch("SET FOREIGN_KEY_CHECKS=0");
        statement.addBatch("TRUNCATE TABLE carts");
        statement.addBatch("SET FOREIGN_KEY_CHECKS=1");
        statement.addBatch("INSERT INTO carts(id, total, cart_items) VALUES(2, 1510, 2)");
        statement.addBatch("INSERT INTO carts(id, total, cart_items) VALUES(3, 1029, 1)");
        statement.executeBatch();
        connection.commit();
        System.out.println("Resetting is successfully!");
    }

    @Test
    public void getByUser() throws BackendException {
        Cart expected = cart2;
        Cart actual = cartDAO.getByUser(user2);
        assertEquals(expected, actual);
    }

    @Test
    public void getById() throws BackendException {
        Cart expected = cart2;
        Cart actual = cartDAO.getById(2);
        assertEquals(expected, actual);
    }

    @Test
    public void edit() throws BackendException {
        Cart expected = cart2;
        expected.setTotal(12000);
        cartDAO.edit(expected);
        Cart actual = cartDAO.getById(2);
        assertEquals(expected, actual);
        expected.setTotal(1510);
    }
}
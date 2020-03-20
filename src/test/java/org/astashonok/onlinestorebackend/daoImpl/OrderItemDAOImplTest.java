package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dto.OrderItem;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;
import org.astashonok.onlinestorebackend.testconfig.SimpleSingleConnection;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.astashonok.onlinestorebackend.testconfig.StaticTestInitializer.*;
import static org.junit.Assert.*;

public class OrderItemDAOImplTest {

    @After
    public void resetDatabase() throws SQLException {
        System.out.println("Reset database...");
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.addBatch("SET FOREIGN_KEY_CHECKS=0");
        statement.addBatch("TRUNCATE TABLE order_items");
        statement.addBatch("SET FOREIGN_KEY_CHECKS=1");
        statement.addBatch("INSERT INTO order_items(order_id, total, product_id, product_count, product_price)"
                + "VALUES(1, 800, 1, 2, 400)");
        statement.addBatch("INSERT INTO order_items(order_id, total, product_id, product_count, product_price)"
                + "VALUES(1, 710, 2, 1, 710)");
        statement.addBatch("INSERT INTO order_items(order_id, total, product_id, product_count, product_price)"
                + "VALUES(2, 1029, 3, 3, 343)");
        statement.executeBatch();
        connection.commit();
        System.out.println("Resetting is successfully!");
    }

    @Test
    public void getByOrder() throws BackendException {
        int expected = 2;
        int actual = orderItemDAO.getByOrder(order2).size();
        assertEquals(expected, actual);
    }

    @Test
    public void getByProduct() throws BackendException {
        OrderItem expected = orderItem21;
        OrderItem actual = orderItemDAO.getByProduct(product3).get(0);
        assertEquals(expected, actual);
    }

    @Test
    public void getByOrderAndProduct() throws BackendException {
        OrderItem expected = orderItem12;
        OrderItem actual = orderItemDAO.getByOrderAndProduct(order2, product2);
        assertEquals(expected, actual);
    }

    @Test
    public void add() throws BackendException {
        OrderItem expected = new OrderItem(order3, 710, product2, 1, 710);
        long id = orderItemDAO.add(expected);
        OrderItem actual = orderItemDAO.getById(id);
        assertEquals(expected, actual);
    }

    @Test
    public void getById() throws BackendException {
        OrderItem expected = orderItem21;
        OrderItem actual = orderItemDAO.getById(3);
        assertEquals(expected, actual);
    }

    @Test
    public void edit() throws BackendException {
        OrderItem expected = orderItem11;
        expected.setTotal(666666);
        orderItemDAO.edit(expected);
        OrderItem actual = orderItemDAO.getById(1);
        assertEquals(expected, actual);
        expected.setTotal(800);
    }

    @Test
    public void remove() throws BackendException {
        orderItemDAO.remove(orderItem11);
        assertNull(orderItemDAO.getById(1));
    }
}
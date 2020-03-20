package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dto.Order;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;
import org.astashonok.onlinestorebackend.testconfig.SimpleSingleConnection;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.astashonok.onlinestorebackend.testconfig.StaticTestInitializer.*;
import static org.junit.Assert.*;

public class OrderDAOImplTest {

    @After
    public void resetDatabase() throws SQLException {
        System.out.println("Reset database...");
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.addBatch("SET FOREIGN_KEY_CHECKS=0");
        statement.addBatch("TRUNCATE TABLE orders");
        statement.addBatch("TRUNCATE TABLE order_items");
        statement.addBatch("SET FOREIGN_KEY_CHECKS=1");
        statement.addBatch("INSERT INTO orders(user_id, total, count, shipping_id, billing_id, date)"
                + "VALUES(2, 1510, 3, 1, 3, '2020-03-03 10:37:22')");
        statement.addBatch("INSERT INTO orders(user_id, total, count, shipping_id, billing_id, date)"
                + "VALUES(3, 1029, 3, 2, 4, '2020-03-05 13:35:21')");
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
    public void getByUser() throws BackendException {
        Order expected = order3;
        Order actual = orderDAO.getByUser(user3).get(0);
        assertEquals(expected, actual);
    }

    @Test
    public void getByAddress() throws BackendException {
        List<Order> list = orderDAO.getByAddress(address11);
        List<Order> list1 = orderDAO.getByAddress(address12);
        assertEquals(list.get(0), list1.get(0));
    }

    @Test
    public void add() throws ParseException, BackendException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
        String dateString = "2020-01-03 00:00:00";
        Date date = formatter.parse(dateString);
        Order expected = new Order(user2, 1510.00, 3, address11, address12, date);
        long id = orderDAO.add(expected);
        Order actual = orderDAO.getById(id);
        assertEquals(expected, actual);
    }

    @Test
    public void getById() throws BackendException {
        Order expected = order2;
        Order actual = orderDAO.getById(1);
        assertEquals(expected, actual);
    }

    @Test
    public void edit() throws BackendException, ParseException {
        Order expected = order2;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
        String dateString = "2020-01-03 01:02:03";
        Date date = formatter.parse(dateString);
        expected.setDate(date);
        orderDAO.edit(expected);
        Order actual = orderDAO.getById(1);
        assertEquals(expected, actual);
        dateString = "2020-03-03 10:37:22";
        date = formatter.parse(dateString);
        expected.setDate(date);
    }

    @Test
    public void remove() throws BackendException {
        orderDAO.remove(order3);
        assertNull(orderDAO.getById(2));
    }
}
package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dto.Order;
import org.astashonok.onlinestorebackend.dto.OrderItem;
import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NegativeValueException;
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

public class OrderItemDAOImplTest {

    private static OrderItemDAOImpl orderItemDAO;
    private static OrderDAOImpl orderDAO;
    private static ProductDAOImpl productDAO;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        orderItemDAO = new OrderItemDAOImpl(Pools.newPool(SimpleSingleConnection.class));
        orderDAO = new OrderDAOImpl(Pools.newPool(SimpleSingleConnection.class));
        productDAO = new ProductDAOImpl(Pools.newPool(SimpleSingleConnection.class));
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destruction of our object! ");
        orderItemDAO = null;
        orderDAO = null;
        productDAO = null;
    }

    @Test
    public void getByOrder() {
        Order order = orderDAO.getById(1);
        int expected = 2;
        int actual = orderItemDAO.getByOrder(order).size();
        assertEquals(expected, actual);
    }

    @Test
    public void getByProduct() {
        OrderItem expected = orderItem21;
        Product product = productDAO.getById(3);
        OrderItem actual = orderItemDAO.getByProduct(product).get(0);
        assertEquals(expected, actual);
    }

    @Test
    public void getByOrderAndProduct() {
        OrderItem expected = orderItem12;
        Product product = productDAO.getById(2);
        Order order = orderDAO.getById(1);
        OrderItem actual = orderItemDAO.getByOrderAndProduct(order, product);
        assertEquals(expected, actual);
    }

    @Test
    public void add() throws SQLException {
        Product product = productDAO.getById(2);
        Order order = orderDAO.getById(2);
        OrderItem expected = new OrderItem(order, 710, product, 1, 710);
        long id = orderItemDAO.add(expected);
        OrderItem actual = orderItemDAO.getById(id);

        // database reset
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        String sql = "DELETE FROM order_items WHERE id = " + id;
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Test
    public void getById() {
        OrderItem expected = orderItem21;
        OrderItem actual = orderItemDAO.getById(3);
        assertEquals(expected, actual);
    }

    @Test
    public void edit() throws NegativeValueException {
        OrderItem expected = orderItemDAO.getById(1);
        expected.setTotal(666666);
        assertTrue(orderItemDAO.edit(expected));
        OrderItem actual = orderItemDAO.getById(1);
        assertEquals(expected, actual);

        // database reset
        expected.setTotal(800);
        assertTrue(orderItemDAO.edit(expected));
    }

    @Test
    public void remove() {
        OrderItem expected = new OrderItem(order3, 1000, product2, 2, 500);
        long id = orderItemDAO.add(expected);
        OrderItem actual = orderItemDAO.getById(id);
        assertEquals(expected, actual);
        assertTrue(orderItemDAO.remove(actual));
    }
}
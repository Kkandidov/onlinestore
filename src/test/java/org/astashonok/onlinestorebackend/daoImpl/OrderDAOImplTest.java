package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dto.Address;
import org.astashonok.onlinestorebackend.dto.Order;
import org.astashonok.onlinestorebackend.dto.OrderItem;
import org.astashonok.onlinestorebackend.dto.User;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NegativeValueException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceException;
import org.astashonok.onlinestorebackend.testconfig.SimpleSingleConnection;
import org.astashonok.onlinestorebackend.util.pool.Pools;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.astashonok.onlinestorebackend.testconfig.StaticInitializerDTO.*;
import static org.junit.Assert.*;

public class OrderDAOImplTest {

    private static OrderDAOImpl orderDAO;
    private static UserDAOImpl userDAO;
    private static AddressDAOImpl addressDAO;
    private static OrderItemDAOImpl orderItemDAO;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        orderDAO = new OrderDAOImpl(Pools.newPool(SimpleSingleConnection.class));
        userDAO = new UserDAOImpl(Pools.newPool(SimpleSingleConnection.class));
        addressDAO = new AddressDAOImpl(Pools.newPool(SimpleSingleConnection.class));
        orderItemDAO = new OrderItemDAOImpl(Pools.newPool(SimpleSingleConnection.class));
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destruction of our object! ");
        orderDAO = null;
        userDAO = null;
        addressDAO = null;
        orderItemDAO = null;
    }

    @Test
    public void getByUser() throws SQLException {
        Order expected = order3;
        User user = userDAO.getById(3);
        Order actual = orderDAO.getByUser(user).get(0);
        assertEquals(expected, actual);
        Order order = new Order(user, 1510, 3, address21, address22, new Date());
        long id = orderDAO.add(order);
        int expect = 2;
        int act = orderDAO.getByUser(user).size();
        assertEquals(expect, act);

        // database reset
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        String sql = "DELETE FROM orders WHERE id = " + id;
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Test
    public void getByAddress() {
        Address shippingAddress = addressDAO.getById(1);
        Address billingAddress = addressDAO.getById(3);
        List<Order> list = orderDAO.getByAddress(shippingAddress);
        List<Order> list1 = orderDAO.getByAddress(billingAddress);
        assertEquals(list.get(0), list1.get(0));
    }

    @Test
    public void add() throws ParseException, SQLException {
        Address shipping = addressDAO.getById(1);
        Address billing = addressDAO.getById(3);
        User user = userDAO.getById(2);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
        String dateInString = "2020-01-03 00:00:00";
        Date date = formatter.parse(dateInString);
        Order order = new Order(user, 1510.00, 3, shipping, billing, date);
        long id = orderDAO.add(order);
        int expected = 2;
        int actual = orderDAO.getByUser(user).size();
        assertEquals(expected, actual);

        // database reset
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        String sql = "DELETE FROM orders WHERE id = " + id ;
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Test
    public void getById() throws ParseException {
        Order expected = order2;
        Order actual = orderDAO.getById(1);
        assertEquals(expected, actual);
    }

    @Test
    public void edit() throws NegativeValueException, ParseException, NullReferenceException {
        Order expected = orderDAO.getById(1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
        String dateInString = "2020-01-03 01:02:03";
        Date date = formatter.parse(dateInString);
        expected.setDate(date);
        assertTrue(orderDAO.edit(expected));
        Order actual = orderDAO.getById(1);
        assertEquals(expected, actual);

        // database reset
        dateInString = "2020-03-03 10:37:22";
        date = formatter.parse(dateInString);
        expected.setDate(date);
        assertTrue(orderDAO.edit(expected));
    }

    @Test
    public void remove() {
        Order order3 = new Order(user3, 1000, 2, address21, address22, new Date());
        long ord3 = orderDAO.add(order3);
        order3 = orderDAO.getById(ord3);
        OrderItem orderItem1 = new OrderItem(order3, 500, product1, 1, 500);
        OrderItem orderItem2 = new OrderItem(order3, 500, product2, 1, 500);
        long orderItemId1 = orderItemDAO.add(orderItem1);
        long orderItemId2 = orderItemDAO.add(orderItem2);
        assertTrue(orderDAO.remove(order3));
        assertNull(orderItemDAO.getById(orderItemId1));
        assertNull(orderItemDAO.getById(orderItemId2));
        assertNull(orderDAO.getById(ord3));
    }
}
package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dto.Address;
import org.astashonok.onlinestorebackend.dto.Order;
import org.astashonok.onlinestorebackend.dto.OrderItem;
import org.astashonok.onlinestorebackend.dto.User;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.testconfig.SimpleSingleConnection;
import org.astashonok.onlinestorebackend.util.pool.Pools;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import static org.astashonok.onlinestorebackend.testconfig.StaticInitializerDTO.*;
import static org.junit.Assert.*;

public class AddressDAOImplTest {

    private static AddressDAOImpl addressDAO;
    private static UserDAOImpl userDAO;
    private static OrderDAOImpl orderDAO;
    private static OrderItemDAOImpl orderItemDAO;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        addressDAO = new AddressDAOImpl(Pools.newPool(SimpleSingleConnection.class));
        userDAO = new UserDAOImpl(Pools.newPool(SimpleSingleConnection.class));
        orderDAO = new OrderDAOImpl(Pools.newPool(SimpleSingleConnection.class));
        orderItemDAO = new OrderItemDAOImpl(Pools.newPool(SimpleSingleConnection.class));
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destruction of our object! ");
        addressDAO = null;
        userDAO = null;
        orderDAO = null;
        orderItemDAO = null;
    }

    @Test
    public void getBillingAddressByUser() {
        Address expected = address22;
        User user = userDAO.getById(3);
        Address actual = addressDAO.getBillingAddressByUser(user);
        assertEquals(expected, actual);
    }

    @Test
    public void getShippingAddressesByUser() throws SQLException {
        Address expected = address21;
        User user = userDAO.getById(3);
        Address actual = addressDAO.getShippingAddressesByUser(user).get(0);
        assertEquals(expected, actual);
        Address address = new Address(user, "Platonov street", "", "Minsk", "Minsk"
                , "Belarus", "220034", false, true);
        long id = addressDAO.add(address);
        int expect = 2;
        int act = addressDAO.getShippingAddressesByUser(user).size();
        assertEquals(expect, act);

        //database reset
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        String sql = "DELETE FROM addresses WHERE id = " + id;
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Test
    public void add() throws SQLException {
        User user = userDAO.getById(3);
        Address expected = new Address(user, "Platonov street", "", "Minsk", "Minsk"
                , "Belarus", "220034", false, true);
        long id = addressDAO.add(expected);
        Address actual = addressDAO.getById(id);
        assertEquals(expected, actual);

        //database reset
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        String sql = "DELETE FROM addresses WHERE id = " + id;
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Test
    public void getById() {
        Address expected = address21;
        Address actual = addressDAO.getById(2);
        System.out.println(address21);
        System.out.println(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void edit() throws OnlineStoreLogicalException {
        Address expected = addressDAO.getById(3);
        expected.setCountry("Russia");
        assertTrue(addressDAO.edit(expected));
        Address actual = addressDAO.getById(3);
        assertEquals(expected, actual);

        //database reset
        expected.setCountry("Belarus");
        assertTrue(addressDAO.edit(expected));
    }

    @Test
    public void remove() throws SQLException {
        // entries adding
        Address address11 = new Address(1, user2, "Platonov street", "", "Minsk"
                , "Minsk", "Belarus", "220034", false, true);
        Address address21 = new Address(2, user3, "Serdich street", "", "Minsk"
                , "Minsk", "Belarus", "220035", false, true);
         Address address12 = new Address(3, user2, "Platonov street", "", "Minsk"
                , "Minsk", "Belarus", "220034", true, false);
        Address address22 = new Address(4, user3, "Serdich street", "", "Minsk"
                , "Minsk", "Belarus", "220035", true, false);
        Order order2 = new Order(1,user2, 1510, 3, address11, address12, new Date());
        Order order3 = new Order(2,user3, 1029, 3, address21, address22, new Date());
        OrderItem orderItem1 = new OrderItem(order2, 800, product1, 2, 400);
        OrderItem orderItem2 = new OrderItem(order2, 710, product2, 1, 710);
        OrderItem orderItem3 = new OrderItem(order3, 1029, product3, 3, 343);
        long id1 = addressDAO.add(address11);
        long id2 = addressDAO.add(address21);
        long id3 = addressDAO.add(address12);
        long id4 = addressDAO.add(address22);
        long id5 = orderDAO.add(order2);
        long id6 = orderDAO.add(order3);
        long id7 = orderItemDAO.add(orderItem1);
        long id8 = orderItemDAO.add(orderItem2);
        long id9 = orderItemDAO.add(orderItem3);

        // entries removing
        assertTrue(addressDAO.remove(address11));
        assertTrue(addressDAO.remove(address21));
        assertTrue(addressDAO.remove(address12));
        assertTrue(addressDAO.remove(address22));
        assertNull(addressDAO.getById(id1));
        assertNull(addressDAO.getById(id2));
        assertNull(addressDAO.getById(id3));
        assertNull(addressDAO.getById(id4));
        assertNull(orderDAO.getById(id5));
        assertNull(orderDAO.getById(id6));
        assertNull(orderItemDAO.getById(id7));
        assertNull(orderItemDAO.getById(id8));
        assertNull(orderItemDAO.getById(id9));
    }
}
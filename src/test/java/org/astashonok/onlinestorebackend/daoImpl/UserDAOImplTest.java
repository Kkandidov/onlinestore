package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dto.*;
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

public class UserDAOImplTest {

    private static UserDAOImpl userDAO;
    private static RoleDAOImpl roleDAO;
    private static AddressDAOImpl addressDAO;
    private static CartDAOImpl cartDAO;
    private static CartItemDAOImpl cartItemDAO;
    private static OrderDAOImpl orderDAO;
    private static OrderItemDAOImpl orderItemDAO;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        userDAO = new UserDAOImpl(Pools.newPool(SimpleSingleConnection.class));
        roleDAO = new RoleDAOImpl(Pools.newPool(SimpleSingleConnection.class));
        cartDAO = new CartDAOImpl(Pools.newPool(SimpleSingleConnection.class));
        addressDAO = new AddressDAOImpl(Pools.newPool(SimpleSingleConnection.class));
        cartItemDAO = new CartItemDAOImpl(Pools.newPool(SimpleSingleConnection.class));
        orderDAO = new OrderDAOImpl(Pools.newPool(SimpleSingleConnection.class));
        orderItemDAO = new OrderItemDAOImpl(Pools.newPool(SimpleSingleConnection.class));
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destruction of our object! ");
        userDAO = null;
        roleDAO = null;
        cartDAO = null;
        addressDAO = null;
        cartItemDAO = null;
        orderDAO = null;
        orderItemDAO = null;
    }

    @Test
    public void getByEmail() {
        User expected = user3;
        User actual = userDAO.getByEmail("sergey@gmail.com");
        assertEquals(expected, actual);
    }

    @Test
    public void getAll() {
        int expected = 3;
        int actual = userDAO.getAll().size();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllEnable() {
        int expected = 3;
        int actual = userDAO.getAllEnable().size();
        assertEquals(expected, actual);
        User user = userDAO.getByEmail("sergey@gmail.com");
        user.setEnabled(false);
        assertTrue(userDAO.edit(user));
        expected = 2;
        actual = userDAO.getAllEnable().size();
        assertEquals(expected, actual);
        assertFalse(userDAO.getByEmail("sergey@gmail.com").isEnabled());

        //database reset
        user.setEnabled(true);
        userDAO.edit(user);
    }

    @Test
    public void getByRole() {
        Role userRole = roleDAO.getById(2);
        int expected = 2;
        int actual = userDAO.getByRole(userRole).size();
        assertEquals(expected, actual);
        Role adminRole = roleDAO.getById(1);
        expected = 1;
        actual = userDAO.getByRole(adminRole).size();
        assertEquals(expected, actual);
    }

    @Test
    public void add() throws SQLException {
        Role role = roleDAO.getById(2);
        User user = new User("Konstantin", "Konstantinov", "kost@gmail.com",
                "$2y$12$r4EhYmgRbDrbmfAMvE1usO/fY8yv1Z.Hp6D9OSIcYelIfjYxUj3e.",
                "+375293456789", true, role);
        long id = userDAO.add(user);
        int expected = 4;
        int actual = userDAO.getAll().size();
        assertEquals(expected, actual);
        user = userDAO.getById(id);
        Cart expect = new Cart(user, 0, 0);
        Cart act = cartDAO.getByUser(user);
        assertEquals(expect, act);

        // database reset
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        String sqlForUser = "DELETE FROM users WHERE id = " + id;
        String sqlForCart = "DELETE FROM carts WHERE id = " + id;
        Statement statement = connection.createStatement();
        connection.setAutoCommit(false);
        statement.addBatch(sqlForCart);
        statement.addBatch(sqlForUser);
        statement.executeBatch();
        connection.commit();
    }

    @Test
    public void getById() {
        User expected = user3;
        User actual = userDAO.getById(3);
        assertEquals(expected, actual);
    }

    @Test
    public void edit() {
        User expected = userDAO.getById(2);
        expected.setLastName("Fedorov");
        assertTrue(userDAO.edit(expected));
        User actual = userDAO.getById(2);
        assertEquals(expected, actual);

        //database reset
        expected.setLastName("Petrov");
        userDAO.edit(expected);
    }

    @Test
    public void remove() {
        // entries adding
        User user = new User("Kostya", "Konstantinov", "kostya@gmail.com"
                , "$2y$12$ZSE/h0gS.Mg.qZnuwzfCxuBd3D1qH3KeY4wL9qZEcAt0FQYNRrBIO", "+375-29-765-23-54"
                , true, role2);
        long id1 = userDAO.add(user);
        user = userDAO.getById(id1);
        Cart cart = cartDAO.getByUser(user);
        Order order1 = new Order(user, 1510, 3, address11, address12, new Date());
        Order order2 = new Order(user, 1029, 3, address21, address22, new Date());
        long id2 = orderDAO.add(order1);
        long id3 = orderDAO.add(order2);
        order1 = orderDAO.getById(id2);
        order2 = orderDAO.getById(id3);
        OrderItem orderItem1 = new OrderItem(order1, 800, product1, 2, 400);
        OrderItem orderItem2 = new OrderItem(order1, 710, product2, 1, 710);
        OrderItem orderItem3 = new OrderItem(order2, 1029, product3, 3, 343);
        long id4 = orderItemDAO.add(orderItem1);
        long id5 = orderItemDAO.add(orderItem2);
        long id6 = orderItemDAO.add(orderItem3);
        Address address1 = new Address(user, "Platonov street", "", "Minsk"
                , "Minsk", "Belarus", "220034", false, true);
        Address address2 = new Address(user, "Serdich street", "", "Minsk"
                , "Minsk", "Belarus", "220035", false, true);
        long id7 = addressDAO.add(address1);
        long id8 = addressDAO.add(address2);
        CartItem cartItem1 = new CartItem(cart, 800, product1, 2, 400
                , true);
        CartItem cartItem2 = new CartItem(cart, 710, product2, 1, 710
                , true);
        CartItem cartItem3 = new CartItem(cart, 1029, product3, 3, 343
                , true);
        long id10 = cartItemDAO.add(cartItem1);
        long id11 = cartItemDAO.add(cartItem2);
        long id12 = cartItemDAO.add(cartItem3);
        // entries removing
        assertTrue(userDAO.remove(user));

        assertNull(userDAO.getById(id1));
        assertNull(orderDAO.getById(id2));
        assertNull(orderDAO.getById(id3));
        assertNull(orderItemDAO.getById(id4));
        assertNull(orderItemDAO.getById(id5));
        assertNull(orderItemDAO.getById(id6));
        assertNull(addressDAO.getById(id7));
        assertNull(addressDAO.getById(id8));
        assertNull(cartDAO.getById(id1));
        assertNull(cartItemDAO.getById(id10));
        assertNull(cartItemDAO.getById(id11));
        assertNull(cartItemDAO.getById(id12));
    }
}
package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dto.Cart;
import org.astashonok.onlinestorebackend.dto.User;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NegativeValueException;
import org.astashonok.onlinestorebackend.testconfig.SimpleSingleConnection;
import org.astashonok.onlinestorebackend.util.pool.Pools;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.astashonok.onlinestorebackend.testconfig.StaticInitializerDTO.cart2;
import static org.junit.Assert.*;

public class CartDAOImplTest {

    private static CartDAOImpl cartDAO;
    private static UserDAOImpl userDAO;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        cartDAO = new CartDAOImpl(Pools.newPool(SimpleSingleConnection.class));
        userDAO = new UserDAOImpl(Pools.newPool(SimpleSingleConnection.class));
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destruction of our object! ");
        cartDAO = null;
        userDAO = null;
    }

    @Test
    public void getByUser() {
        Cart expected = cart2;
        User user = userDAO.getById(2);
        Cart actual = cartDAO.getByUser(user);
        assertEquals(expected, actual);
    }

    @Test
    public void getById() {
        Cart expected = cart2;
        Cart actual = cartDAO.getById(2);
        assertEquals(expected, actual);
    }

    @Test
    public void edit() throws NegativeValueException {
        Cart expected = cartDAO.getById(2);
        expected.setTotal(12000);
        assertTrue(cartDAO.edit(expected));
        Cart actual = cartDAO.getById(2);
        assertEquals(expected, actual);

        // database reset
        expected.setTotal(1510.00);
        assertTrue(cartDAO.edit(expected));
    }
}
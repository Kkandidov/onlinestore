package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.exceptions.logicalexception.NegativeValueException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.astashonok.onlinestorebackend.testconfig.StaticTestInitializer.user2;
import static org.junit.Assert.*;

public class CartTest {
    private static Cart cart;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        cart = new Cart();
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destruction of our object! ");
        cart = null;
    }

    // if the reference to user is null
    @Test(expected = NullReferenceException.class)
    public void setUserIsNull() throws NullReferenceException {
        cart.setUser(null);
    }

    // if the reference to user is correct
    @Test
    public void setUser() throws NullReferenceException {
        cart.setUser(user2);
        assertSame(user2, cart.getUser());
    }

    // if the reference to total is negative
    @Test(expected = NegativeValueException.class)
    public void setTotalIsNegative() throws NegativeValueException {
        cart.setTotal(-5);
    }

    // if the total is 0
    @Test
    public void setTotalIs0() throws NegativeValueException {
        cart.setTotal(0);
        double expected = 0;
        double actual = cart.getTotal();
        assertEquals(expected, actual, 0.0);
    }

    // if the total is correct
    @Test
    public void setTotal() throws NegativeValueException {
        cart.setTotal(139);
        double expected = 139;
        double actual = cart.getTotal();
        assertEquals(expected, actual, 0.0);
    }


    // if the count of cartItems is negative
    @Test(expected = NegativeValueException.class)
    public void setCartItemsIsNegative() throws NegativeValueException {
        cart.setCartItems(-4);
    }

    // if the count of cartItems is 0
    @Test
    public void setCartItemsIs0() throws NegativeValueException {
        cart.setCartItems(0);
        int expected = 0;
        int actual = cart.getCartItems();
        assertEquals(expected, actual);
    }

    // if the count of cartItems is correct
    @Test
    public void setCartItems() throws NegativeValueException {
        cart.setCartItems(2);
        int expected = 2;
        int actual = cart.getCartItems();
        assertEquals(expected, actual);
    }
}
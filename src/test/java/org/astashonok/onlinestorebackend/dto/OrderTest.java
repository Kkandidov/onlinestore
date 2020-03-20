package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.exceptions.logicalexception.NegativeValueException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

import static org.astashonok.onlinestorebackend.testconfig.StaticTestInitializer.*;
import static org.junit.Assert.*;

public class OrderTest {
    private static Order order;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        order = new Order();
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destruction of our object! ");
        order = null;
    }

    // if the reference to user is null
    @Test(expected = NullReferenceException.class)
    public void setUserIsNull() throws NullReferenceException {
        order.setUser(null);
    }

    // if the reference to user is correct
    @Test
    public void setUser() throws NullReferenceException {
        order.setUser(user3);
        assertSame(user3, order.getUser());
    }

    // if the total is negative
    @Test(expected = NegativeValueException.class)
    public void setTotalIsNegative() throws NegativeValueException {
        order.setTotal(-567);
    }

    // if the total is 0
    @Test
    public void setTotalIs0() throws NegativeValueException {
        order.setTotal(0);
        double expected = 0;
        double actual = order.getTotal();
        assertEquals(expected, actual, 0.0);
    }

    // if the total is correct
    @Test
    public void setTotal() throws NegativeValueException {
        order.setTotal(567);
        double expected = 567;
        double actual = order.getTotal();
        assertEquals(expected, actual, 0.0);
    }

    // if the count is negative
    @Test(expected = NegativeValueException.class)
    public void setCountIsNegative() throws NegativeValueException {
        order.setCount(-5);
    }

    // if the count is 0
    @Test
    public void setCountIs0() throws NegativeValueException {
        order.setCount(0);
        int expected = 0;
        int actual = order.getCount();
        assertEquals(expected, actual);
    }

    // if the count is correct
    @Test
    public void setCount() throws NegativeValueException {
        order.setCount(57);
        int expected = 57;
        int actual = order.getCount();
        assertEquals(expected, actual);
    }

    // if the reference to shipping address is null
    @Test(expected = NullReferenceException.class)
    public void setShippingIsNull() throws NullReferenceException {
        order.setShipping(null);
    }

    // if the reference to shipping address is correct
    @Test
    public void setShipping() throws NullReferenceException {
        order.setShipping(address11);
        assertSame(address11, order.getShipping());
    }

    // if the reference to shipping address is null
    @Test(expected = NullReferenceException.class)
    public void setBillingIsNull() throws NullReferenceException {
        order.setBilling(null);
    }

    // if the reference to shipping address is correct
    @Test
    public void setBilling() throws NullReferenceException {
        order.setBilling(address21);
        assertSame(address21, order.getBilling());
    }

    // if the reference to date is null
    @Test(expected = NullReferenceException.class)
    public void setDateIsNull() throws NullReferenceException {
        order.setDate(null);
    }

    // if the reference to date is correct
    @Test
    public void setDate() throws NullReferenceException {
        Date date = new Date();
        order.setDate(date);
        assertSame(date, order.getDate());
    }

    // if the reference to orderItems is null
    @Test(expected = NullReferenceException.class)
    public void setOrderItemsIsNull() throws NullReferenceException {
        order.setOrderItems(null);
    }

    // if the references to orderItems are correct
    @Test
    public void setOrderItems() throws NullReferenceException {
        order.setOrderItems(orderItem11);
        assertSame(orderItem11, order.getOrderItems().iterator().next());
    }
}
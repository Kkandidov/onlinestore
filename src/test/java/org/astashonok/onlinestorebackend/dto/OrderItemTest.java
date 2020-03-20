package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.exceptions.logicalexception.NegativeValueException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.astashonok.onlinestorebackend.testconfig.StaticTestInitializer.order2;
import static org.astashonok.onlinestorebackend.testconfig.StaticTestInitializer.product2;
import static org.junit.Assert.*;

public class OrderItemTest {
    private static OrderItem orderItem;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        orderItem = new OrderItem();
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destruction of our object! ");
        orderItem = null;
    }

    // if the reference to order is null
    @Test(expected = NullReferenceException.class)
    public void setOrderIsNull() throws NullReferenceException {
        orderItem.setOrder(null);
    }

    // if the reference to order is correct
    @Test
    public void setOrder() throws NullReferenceException {
        orderItem.setOrder(order2);
        assertSame(order2, orderItem.getOrder());
    }

    // if the reference to total is negative
    @Test(expected = NegativeValueException.class)
    public void setTotalIsNull() throws NegativeValueException {
        orderItem.setTotal(-5);
    }

    // if the reference to total is 0
    @Test
    public void setTotalIs0() throws NegativeValueException {
        orderItem.setTotal(0);
        double expected = 0;
        double actual = orderItem.getTotal();
        assertEquals(expected, actual, 0.0);
    }

    // if the reference to total is correct
    @Test
    public void setTotal() throws NegativeValueException {
        orderItem.setTotal(897);
        double expected = 897;
        double actual = orderItem.getTotal();
        assertEquals(expected, actual, 0.0);
    }

    // if the reference to product is null
    @Test(expected = NullReferenceException.class)
    public void setProductIsNull() throws NullReferenceException {
        orderItem.setProduct(null);
    }

    // if the reference to product is correct
    @Test
    public void setProduct() throws NullReferenceException {
        orderItem.setProduct(product2);
        assertSame(product2, orderItem.getProduct());
    }

    // if the reference to product count is negative
    @Test(expected = NegativeValueException.class)
    public void setProductCountIsNegative() throws NegativeValueException {
        orderItem.setProductCount(-5);
    }

    // if the reference to product count is 0
    @Test
    public void setProductCountIs0() throws NegativeValueException {
        orderItem.setProductCount(0);
        int expected = 0;
        int actual = orderItem.getProductCount();
        assertEquals(expected, actual);
    }

    // if the reference to product count is correct
    @Test
    public void setProductCount() throws NegativeValueException {
        orderItem.setProductCount(5);
        int expected = 5;
        int actual = orderItem.getProductCount();
        assertEquals(expected, actual);
    }

    // if the reference to product price is negative
    @Test(expected = NegativeValueException.class)
    public void setProductPriceIsNegative() throws NegativeValueException {
        orderItem.setProductPrice(-5);
    }

    // if the reference to product price is 0
    @Test
    public void setProductPriceIs0() throws NegativeValueException {
        orderItem.setProductPrice(0);
        double expected = 0;
        double actual = orderItem.getProductPrice();
        assertEquals(expected, actual, 0.0);
    }

    // if the reference to product price is correct
    @Test
    public void setProductPrice() throws NegativeValueException {
        orderItem.setProductPrice(987);
        double expected = 987;
        double actual = orderItem.getProductPrice();
        assertEquals(expected, actual, 0.0);
    }
}
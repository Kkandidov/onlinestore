package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.exceptions.logicalexception.NegativeValueException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.astashonok.onlinestorebackend.testconfig.StaticTestInitializer.cart2;
import static org.astashonok.onlinestorebackend.testconfig.StaticTestInitializer.product1;
import static org.junit.Assert.*;

public class CartItemTest {
    private static CartItem cartItem;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        cartItem = new CartItem();
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destruction of our object! ");
        cartItem = null;
    }

    // if the reference to cart is null
    @Test(expected = NullReferenceException.class)
    public void setCartIsNull() throws NullReferenceException {
        cartItem.setCart(null);
    }

    // if the reference to cart is correct
    @Test
    public void setCart() throws NullReferenceException {
        cartItem.setCart(cart2);
        assertSame(cart2, cartItem.getCart());
    }

    // if the total is negative
    @Test(expected = NegativeValueException.class)
    public void setTotalIsNegative() throws NegativeValueException {
        cartItem.setTotal(-5);
    }

    // if the total is 0
    @Test
    public void setTotalIs0() throws NegativeValueException {
        cartItem.setTotal(0);
        double expected = 0;
        double actual = cartItem.getTotal();
        assertEquals(expected, actual, 0.0);
    }

    // if the total is correct
    @Test
    public void setTotal() throws NegativeValueException {
        cartItem.setTotal(45);
        double expected = 45;
        double actual = cartItem.getTotal();
        assertEquals(expected, actual, 0.0);
    }

    // if the reference to product is null
    @Test(expected = NullReferenceException.class)
    public void setProductIsNull() throws NullReferenceException {
        cartItem.setProduct(null);
    }

    // if the reference to product is correct
    @Test
    public void setProduct() throws NullReferenceException {
        cartItem.setProduct(product1);
        assertSame(product1, cartItem.getProduct());
    }

    // if the reference to product count is negative
    @Test(expected = NegativeValueException.class)
    public void setProductCountIsNegative() throws NegativeValueException {
        cartItem.setProductCount(-5);
    }

    // if the product count is 0
    @Test
    public void setProductCountIs0() throws NegativeValueException {
        cartItem.setProductCount(0);
        int expected = 0;
        int actual = cartItem.getProductCount();
        assertEquals(expected, actual);
    }

    // if the product count is correct
    @Test
    public void setProductCount() throws NegativeValueException {
        cartItem.setProductCount(56);
        int expected = 56;
        int actual = cartItem.getProductCount();
        assertEquals(expected, actual);
    }

    // if the product price is negative
    @Test(expected = NegativeValueException.class)
    public void setProductPriceIsNegative() throws NegativeValueException {
        cartItem.setProductPrice(-36);
    }

    // if the product price is 0
    @Test
    public void setProductPriceIs0() throws NegativeValueException {
        cartItem.setProductPrice(0);
        double expected = 0;
        double actual = cartItem.getProductPrice();
        assertEquals(expected, actual, 0.0);
    }

    // if the product price is correct
    @Test
    public void setProductPrice() throws NegativeValueException {
        cartItem.setProductPrice(536);
        double expected = 536;
        double actual = cartItem.getProductPrice();
        assertEquals(expected, actual, 0.0);
    }
}
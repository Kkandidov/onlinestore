package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendLogicalException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.EmptyFieldException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NegativeValueException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

import static org.astashonok.onlinestorebackend.testconfig.StaticTestInitializer.*;
import static org.junit.Assert.*;

public class ProductTest {

    private static Product product;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        product = new Product();
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destruction of our object! ");
        product = null;
    }

    // if the reference to name is null
    @Test(expected = NullReferenceException.class)
    public void setNameIsNull() throws BackendLogicalException {
        product.setName(null);
    }

    // if the reference to name is empty
    @Test(expected = EmptyFieldException.class)
    public void setNameIsEmpty() throws BackendLogicalException {
        product.setName("");
    }

    // if the reference to name is correct
    @Test
    public void setName() throws BackendLogicalException {
        product.setName("HONOR 20 international version");
        String expected = "HONOR 20 international version";
        String actual = product.getName();
        assertEquals(expected, actual);
    }

    // if the reference to code is null
    @Test(expected = NullReferenceException.class)
    public void setCodeIsNull() throws BackendLogicalException {
        product.setCode(null);
    }

    // if the reference to code is empty
    @Test(expected = EmptyFieldException.class)
    public void setCodeIsEmpty() throws BackendLogicalException {
        product.setCode("");
    }

    // if the reference to code is correct
    @Test
    public void setCode() throws BackendLogicalException {
        product.setCode("MAIN1581865663258");
        String expected = "MAIN1581865663258";
        String actual = product.getCode();
        assertEquals(expected, actual);
    }

    // if the reference to brand is null
    @Test(expected = NullReferenceException.class)
    public void setBrandIsNull() throws NullReferenceException {
        product.setBrand(null);
    }

    // if the reference to brand is correct
    @Test
    public void setBrand() throws NullReferenceException {
        product.setBrand(brand3);
        assertSame(brand3, product.getBrand());
    }

    // if the price is negative
    @Test(expected = NegativeValueException.class)
    public void setUnitPriceIsNegative() throws NegativeValueException {
        product.setUnitPrice(-4);
    }

    // if the price is 0
    @Test
    public void setUnitPriceIs0() throws NegativeValueException {
        product.setUnitPrice(0);
        double expected = 0;
        double actual = product.getUnitPrice();
        assertEquals(expected, actual, 0.0);
    }

    // if the price is correct
    @Test
    public void setUnitPrice() throws NegativeValueException {
        product.setUnitPrice(345.8);
        double expected = 345.8;
        double actual = product.getUnitPrice();
        assertEquals(expected, actual, 0.0);
    }

    // if the quantity is negative
    @Test(expected = NegativeValueException.class)
    public void setQuantityIsNegative() throws NegativeValueException {
        product.setQuantity(-5);
    }

    // if the quantity is 0
    @Test
    public void setQuantityIs0() throws NegativeValueException {
        product.setQuantity(0);
        int expected = 0;
        int actual = product.getQuantity();
        assertEquals(expected, actual);
    }

    // if the quantity is correct
    @Test
    public void setQuantity() throws NegativeValueException {
        product.setQuantity(3);
        int expected = 3;
        int actual = product.getQuantity();
        assertEquals(expected, actual);
    }

    // if the reference to category is null
    @Test(expected = NullReferenceException.class)
    public void setCategory() throws NullReferenceException {
        product.setCategory(null);
    }

    // if the reference to category is correct
    @Test
    public void setDescription() throws NullReferenceException {
        product.setCategory(category1);
        assertSame(category1, product.getCategory());
    }
}
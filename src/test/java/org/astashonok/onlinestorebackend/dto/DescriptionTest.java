package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class DescriptionTest {
    private static Description description;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        description = new Description();
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destruction of our object! ");
        description = null;
    }

    // if the reference to product is null
    @Test(expected = NullReferenceException.class)
    public void setProductIsNull() throws NullReferenceException {
        description.setProduct(null);
    }

    // if the reference to product is correct
    @Test
    public void setProduct() throws NullReferenceException {
        Product product = new Product();
        description.setProduct(product);
        assertSame(product, description.getProduct());
    }
}
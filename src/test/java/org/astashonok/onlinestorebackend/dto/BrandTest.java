package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.EmptyFieldException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class BrandTest {

    private static Brand brand;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        brand = new Brand();
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destroying of our object! ");
        brand = null;
    }

    // if the reference to name is empty
    @Test(expected = EmptyFieldException.class)
    public void setNameIsEmpty() throws OnlineStoreLogicalException {
        brand.setName("");
    }

    // if the reference to name is null
    @Test(expected = NullReferenceException.class)
    public void setNameIsNull() throws OnlineStoreLogicalException {
        brand.setName(null);
    }

    // if the reference to is correct
    @Test
    public void setName() throws OnlineStoreLogicalException {
        brand.setName("Apple");
        String expected = "Apple";
        String actual = brand.getName();
        assertEquals(expected, actual);
    }
}
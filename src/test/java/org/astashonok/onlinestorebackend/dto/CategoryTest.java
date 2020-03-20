package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendLogicalException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.EmptyFieldException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTest {

    private static Category category;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        category = new Category();
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destroying of our object! ");
        category = null;
    }

    // if the reference to name is empty
    @Test(expected = EmptyFieldException.class)
    public void setNameIsEmpty() throws BackendLogicalException {
        category.setName("");
    }

    // if the reference to name is null
    @Test(expected = NullReferenceException.class)
    public void setNameNullReference() throws BackendLogicalException {
        category.setName(null);
    }

    // if the reference to is correct
    @Test
    public void setName() throws BackendLogicalException {
        category.setName("Mobile phone");
        String expected = "Mobile phone";
        String actual = category.getName();
        assertEquals(expected, actual);
    }
}
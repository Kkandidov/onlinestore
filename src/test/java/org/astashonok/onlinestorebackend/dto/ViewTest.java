package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendLogicalException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.EmptyFieldException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.astashonok.onlinestorebackend.testconfig.StaticTestInitializer.product2;
import static org.junit.Assert.*;

public class ViewTest {
    private static View view;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        view = new View();
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destruction of our object! ");
        view = null;
    }

    // if the reference to code is null
    @Test(expected = NullReferenceException.class)
    public void setCodeIsNull() throws BackendLogicalException {
        view.setCode(null);
    }

    // if the reference to code is empty
    @Test(expected = EmptyFieldException.class)
    public void setCodeIsEmpty() throws BackendLogicalException {
        view.setCode("");
    }

    // if the reference to code is correct
    @Test
    public void setCode() throws BackendLogicalException {
        view.setCode("PRD1581866965493");
        String expected = "PRD1581866965493";
        String actual = view.getCode();
        assertEquals(expected, actual);
    }

    // if the reference to product is null
    @Test(expected = NullReferenceException.class)
    public void setProductIsNull() throws NullReferenceException {
        view.setProduct(null);
    }

    // if the reference to product is correct
    @Test
    public void setProduct() throws NullReferenceException {
        view.setProduct(product2);
        assertSame(product2, view.getProduct());
    }
}
package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendLogicalException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.EmptyFieldException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoleTest {

    private static Role role;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        role = new Role();
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destroying of our object! ");
        role = null;
    }

    // if the reference to name is null
    @Test(expected = NullReferenceException.class)
    public void setNameNullReference() throws BackendLogicalException {
        role.setName(null);
    }

    // if the reference to name is empty
    @Test(expected = EmptyFieldException.class)
    public void setNameIsEmpty() throws BackendLogicalException {
        role.setName("");
    }

    // if the reference to name is correct
    @Test
    public void setName() throws BackendLogicalException {
        role.setName("ADMIN");
        String expected = "ADMIN";
        String actual = role.getName();
        assertEquals(expected, actual);
    }
}
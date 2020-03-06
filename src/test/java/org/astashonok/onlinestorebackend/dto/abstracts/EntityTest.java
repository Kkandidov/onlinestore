package org.astashonok.onlinestorebackend.dto.abstracts;

import org.astashonok.onlinestorebackend.exceptions.logicalexception.NotPositiveValueException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class EntityTest {

    private static Entity entity;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        entity = new Entity();
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destroying of our object! ");
        entity = null;
    }

    // if the value of id is equal to 0
    @Test (expected = NotPositiveValueException.class)
    public void setIdEqualTo0() throws NotPositiveValueException {
        entity.setId(0);
    }

    // if the value of id is equal to less than 0
    @Test (expected = NotPositiveValueException.class)
    public void setIdEqualToLessThan0() throws NotPositiveValueException {
        entity.setId(-5);
    }

    // if the value of id is normal
    @Test
    public void setId() throws NotPositiveValueException {
        entity.setId(5);
        long expected = 5;
        long actual = entity.getId();
        assertEquals(expected, actual);
    }
}
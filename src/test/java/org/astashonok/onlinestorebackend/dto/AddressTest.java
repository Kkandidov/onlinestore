package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.EmptyFieldException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceException;
import org.junit.*;
import static org.astashonok.onlinestorebackend.testconfig.StaticInitializerDTO.user2;
import static org.junit.Assert.*;

public class AddressTest {

    private static Address address;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        address = new Address();
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destruction of our object! ");
        address = null;
    }

    // if the reference to user is null
    @Test(expected = NullReferenceException.class)
    public void setUserNullReference() throws NullReferenceException {
        address.setUser(null);
    }

    // if the reference to user is correct
    @Test
    public void setUser() throws NullReferenceException {
        address.setUser(user2);
        assertSame(user2, address.getUser());
    }

    // if the reference to lineOne is null
    @Test(expected = NullReferenceException.class)
    public void setLineOneIsNull() throws OnlineStoreLogicalException {
        address.setLineOne(null);
    }

    // if the reference to lineOne is empty
    @Test(expected = EmptyFieldException.class)
    public void setLineOneIsEmpty() throws OnlineStoreLogicalException {
        address.setLineOne("");
    }

    // if the reference to line one is correct
    @Test
    public void setLineOne() throws OnlineStoreLogicalException {
        address.setLineOne("Platonov street");
    }

    // if the reference to city is null
    @Test(expected = NullReferenceException.class)
    public void setCityIsNull() throws OnlineStoreLogicalException {
        address.setCity(null);
    }

    // if the reference to city is empty
    @Test(expected = EmptyFieldException.class)
    public void setCityIsEmpty() throws OnlineStoreLogicalException {
        address.setCity("");
    }

    // if the reference to city is correct
    @Test
    public void setCity() throws OnlineStoreLogicalException {
        address.setCity("Minsk");
        String expected = "Minsk";
        String actual = address.getCity();
        assertEquals(expected,actual);
    }

    // if the reference to state is null
    @Test(expected = NullReferenceException.class)
    public void setStateIsNull() throws OnlineStoreLogicalException {
        address.setState(null);
    }

    // if the reference to state is empty
    @Test(expected = EmptyFieldException.class)
    public void setStateIsEmpty() throws OnlineStoreLogicalException {
        address.setState("");
    }

    // if the reference to state is correct
    @Test
    public void setState() throws OnlineStoreLogicalException {
        address.setState("Minsk");
        String expected = "Minsk";
        String actual = address.getState();
        assertEquals(expected,actual);
    }

    // if the reference to country is null
    @Test(expected = NullReferenceException.class)
    public void setCountryIsNull() throws OnlineStoreLogicalException {
        address.setCountry(null);
    }

    // if the reference to country is empty
    @Test(expected = EmptyFieldException.class)
    public void setCountryIsEmpty() throws OnlineStoreLogicalException {
        address.setCountry("");
    }

    // if the reference to country is correct
    @Test
    public void setCountry() throws OnlineStoreLogicalException {
        address.setCountry("Belarus");
        String expected = "Belarus";
        String actual = address.getCountry();
        assertEquals(expected,actual);
    }

    // if the reference to postalCode is null
    @Test(expected = NullReferenceException.class)
    public void setPostalCodeIsNull() throws OnlineStoreLogicalException {
        address.setPostalCode(null);
    }

    // if the reference to postalCode is empty
    @Test(expected = EmptyFieldException.class)
    public void setPostalCodeIsEmpty() throws OnlineStoreLogicalException {
        address.setPostalCode("");
    }

    // if the reference to postalCode is correct
    @Test
    public void setPostalCode() throws OnlineStoreLogicalException {
        address.setPostalCode("12345");
        String expected = "12345";
        String actual = address.getPostalCode();
        assertEquals(expected,actual);
    }
}
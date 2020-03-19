package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.astashonok.onlinestorebackend.testconfig.StaticInitializerDTO.*;
import static org.junit.Assert.*;

public class UserTest {

    private static User user;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        user = new User();
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destroying of our object! ");
        user = null;
    }

    // if the reference to firstName is null
    @Test(expected = NullReferenceException.class)
    public void setFirstNameIsNull() throws OnlineStoreLogicalException {
        user.setFirstName(null);
    }

    // if the reference to firstName is empty
    @Test(expected = EmptyFieldException.class)
    public void setFirstNameIsEmpty() throws OnlineStoreLogicalException {
        user.setFirstName("");
    }

    // if the reference to firstName is correct
    @Test
    public void setFirstName() throws OnlineStoreLogicalException {
        user.setFirstName("Mike");
    }

    // if the reference to email is empty
    @Test (expected = EmptyFieldException.class)
    public void setEmailIsEmpty() throws OnlineStoreLogicalException {
        user.setEmail("");
    }

    // if the reference to email is null
    @Test(expected = NullReferenceException.class)
    public void setEmailIsNull() throws OnlineStoreLogicalException {
        user.setEmail(null);
    }

    // if the reference to email is correct
    @Test
    public void setEmail1() throws OnlineStoreLogicalException {
        user.setEmail("petr@gmail.com");
        String expected = "petr@gmail.com";
        String actual = user.getEmail();
        assertEquals(expected, actual);
    }

    // if the reference to password is empty
    @Test(expected = EmptyFieldException.class)
    public void setPasswordIsEmpty() throws OnlineStoreLogicalException {
        user.setPassword("");
    }

    // if the reference to password is null
    @Test(expected = NullReferenceException.class)
    public void setPasswordIsNull() throws OnlineStoreLogicalException {
        user.setPassword(null);
    }

    // if the reference to password is correct
    @Test
    public void setPassword1() throws OnlineStoreLogicalException {
        user.setPassword("n!k@sn1Kos");
        String expected = "n!k@sn1Kos";
        String actual = user.getPassword();
        assertEquals(expected, actual);
    }

    // if the reference to number is empty
    @Test(expected = EmptyFieldException.class)
    public void setContactNumberIsEmpty() throws OnlineStoreLogicalException {
        user.setContactNumber("");
    }

    // if the reference to number is null
    @Test(expected = NullReferenceException.class)
    public void setContactNumberIsNull() throws OnlineStoreLogicalException {
        user.setContactNumber(null);
    }

    // if the reference to number is correct
    @Test
    public void setContactNumber1() throws OnlineStoreLogicalException {
        user.setContactNumber("+375296543218");
        String expected = "+375296543218";
        String actual = user.getContactNumber();
        assertEquals(expected,actual);
    }

    // if the reference to role is null
    @Test(expected = NullReferenceException.class)
    public void setRoleIsNull() throws NullReferenceException {
        user.setRole(null);
    }

    // if the reference to role is correct
    @Test
    public void setRole() throws NullReferenceException {
        user.setRole(role2);
        assertSame(role2, user.getRole());
    }

    // if the reference to confirmPassword is null
    @Test(expected = NullReferenceException.class)
    public void setConfirmPasswordIsNull() throws OnlineStoreLogicalException {
        user.setConfirmPassword(null);
    }

    // if the reference to confirmPassword is empty
    @Test(expected = EmptyFieldException.class)
    public void setConfirmPasswordIsEmpty() throws OnlineStoreLogicalException {
        user.setConfirmPassword("");
    }

    // if the reference to confirmPassword is correct
    @Test
    public void setConfirmPassword() throws OnlineStoreLogicalException {
        user.setConfirmPassword("n!k@sn1Kos");
        String expected = "n!k@sn1Kos";
        String actual = user.getConfirmPassword();
        assertEquals(expected, actual);
    }

    // if the reference to address is null
    @Test(expected = NullReferenceException.class)
    public void setAddressesIsNull() throws NullReferenceException {
        user.setAddresses(null);
    }

    // if the reference to address is correct
    @Test
    public void setAddresses() throws NullReferenceException {
        user.setAddresses(address12);
        assertSame(address12, user.getAddresses().iterator().next());
    }

    // if the reference to cart is null
    @Test(expected = NullReferenceException.class)
    public void setCartIsNull() throws NullReferenceException {
        user.setCart(null);
    }

    // if the reference to cart is correct
    @Test
    public void setCart() throws NullReferenceException {
        user.setCart(cart3);
        assertSame(cart3, user.getCart());
    }
}
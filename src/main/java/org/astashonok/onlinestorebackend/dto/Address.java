package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.EmptyFieldException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Address extends Entity {
    private User user;
    private String lineOne;
    private String lineTwo;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private boolean billing;
    private boolean shipping;

    private Set<Order> orders;

    public Address() {
        this.orders = new HashSet<>();
    }

    public Address(User user, String lineOne, String lineTwo, String city, String state, String country,
                   String postalCode, boolean billing, boolean shipping) {
        this();
        this.user = user;
        this.lineOne = lineOne;
        this.lineTwo = lineTwo;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
        this.billing = billing;
        this.shipping = shipping;
    }

    public Address(long id, User user, String lineOne, String lineTwo, String city, String state, String country,
                   String postalCode, boolean billing, boolean shipping) {
        this(user, lineOne, lineTwo, city, state, country, postalCode, billing, shipping);
        super.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) throws NullReferenceException {
        if (user == null) {
            throw new NullReferenceException("The address can't exist without a user! ");
        }
        this.user = user;
    }

    public String getLineOne() {
        return lineOne;
    }

    public void setLineOne(String lineOne) throws OnlineStoreLogicalException {
        if (lineOne == null) {
            throw new NullReferenceException("The lineOne must be indicated in the address! ");
        }
        if (lineOne.isEmpty()) {
            throw new EmptyFieldException("The lineOne must be filled in the address! ");
        }
        this.lineOne = lineOne;
    }

    public String getLineTwo() {
        return lineTwo;
    }

    public void setLineTwo(String lineTwo) {
        this.lineTwo = lineTwo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) throws OnlineStoreLogicalException {
        if (city == null) {
            throw new NullReferenceException("The city must be indicated in the address! ");
        }
        if (city.isEmpty()) {
            throw new EmptyFieldException("The city must be filled in the address! ");
        }
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) throws OnlineStoreLogicalException {
        if (state == null) {
            throw new NullReferenceException("The state must be indicated in the address! ");
        }
        if (state.isEmpty()) {
            throw new EmptyFieldException("The state must be filled in the address! ");
        }
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) throws OnlineStoreLogicalException {
        if (country == null) {
            throw new NullReferenceException("The country must be indicated in the address! ");
        }
        if (country.isEmpty()) {
            throw new EmptyFieldException("The country must be filled in the address! ");
        }
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) throws OnlineStoreLogicalException {
        if (postalCode == null) {
            throw new NullReferenceException("The postalCode must be indicated in the address! ");
        }
        if (postalCode.isEmpty()) {
            throw new EmptyFieldException("The postalCode must be filled in the address! ");
        }
        this.postalCode = postalCode;
    }

    public boolean isBilling() {
        return billing;
    }

    public void setBilling(boolean billing) {
        this.billing = billing;
    }

    public boolean isShipping() {
        return shipping;
    }

    public void setShipping(boolean shipping) {
        this.shipping = shipping;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Order... orders) {
        this.orders.addAll(Arrays.asList(orders));
    }

    @Override
    public String toString() {
        return "Address{" +
                super.toString() +
                ", user=" + user +
                ", lineOne='" + lineOne + '\'' +
                ", lineTwo='" + lineTwo + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", billing=" + billing +
                ", shipping=" + shipping +
                '}';
    }
}

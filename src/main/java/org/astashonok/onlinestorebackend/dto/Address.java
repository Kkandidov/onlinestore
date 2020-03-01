package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.EmptyFieldException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceToRequiredObject;

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
    }

    public Address(User user, String lineOne, String lineTwo, String city, String state, String country,
                   String postalCode, boolean billing, boolean shipping) {
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

    public void setUser(User user) throws NullReferenceToRequiredObject {
        if (user == null) {
            throw new NullReferenceToRequiredObject("The address can't exist without a user! ");
        }

        this.user = user;
    }

    public String getLineOne() {
        return lineOne;
    }

    public void setLineOne(String lineOne) throws OnlineStoreLogicalException {
        if (lineOne == null) {
            throw new NullReferenceToRequiredObject();
        }

        if (lineOne.isEmpty()) {
            throw new EmptyFieldException();
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
            throw new NullReferenceToRequiredObject();
        }

        if (city.isEmpty()) {
            throw new EmptyFieldException();
        }

        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) throws OnlineStoreLogicalException {
        if (state == null) {
            throw new NullReferenceToRequiredObject();
        }

        if (state.isEmpty()) {
            throw new EmptyFieldException();
        }

        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) throws OnlineStoreLogicalException {
        if (country == null) {
            throw new NullReferenceToRequiredObject();
        }

        if (country.isEmpty()) {
            throw new EmptyFieldException();
        }

        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) throws OnlineStoreLogicalException {
        if (postalCode == null) {
            throw new NullReferenceToRequiredObject();
        }

        if (postalCode.isEmpty()) {
            throw new EmptyFieldException();
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

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
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

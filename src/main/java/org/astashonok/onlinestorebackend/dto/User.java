package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.EmptyFieldException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceToRequiredObject;

import java.util.Set;

public class User extends Entity {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String contactNumber;
    private boolean enabled;
    private Role role;

    private Set<Address> addresses;
    private Set<Order> orders;
    private Cart cart;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, String contactNumber,
                boolean enabled, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.contactNumber = contactNumber;
        this.enabled = enabled;
        this.role = role;
    }

    public User(long id, String firstName, String lastName, String email, String password, String contactNumber,
                boolean enabled, Role role) {
        this(firstName, lastName, email, password, contactNumber, enabled, role);
        super.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws OnlineStoreLogicalException {
        if (firstName == null) {
            throw new NullReferenceToRequiredObject("The user has to have firs name ");
        }

        if (firstName.isEmpty()) {
            throw new EmptyFieldException();
        }

        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws OnlineStoreLogicalException {
        if (email == null) {
            throw new NullReferenceToRequiredObject("The user has to have email ");
        }

        if (email.isEmpty()) {
            throw new EmptyFieldException();
        }

        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws OnlineStoreLogicalException {
        if (password == null) {
            throw new NullReferenceToRequiredObject("The user has to have password ");
        }

        if (password.isEmpty()) {
            throw new EmptyFieldException();
        }

        this.password = password;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) throws OnlineStoreLogicalException {
        if (contactNumber == null) {
            throw new NullReferenceToRequiredObject("The user has to have contact number ");
        }

        if (contactNumber.isEmpty()) {
            throw new EmptyFieldException();
        }

        this.contactNumber = contactNumber;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) throws NullReferenceToRequiredObject {
        if (role == null) {
            throw new NullReferenceToRequiredObject("User has to have a role ");
        }

        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                super.toString() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}

package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendLogicalException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User extends Entity {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String contactNumber;
    private boolean enabled;
    private Role role;

    private String confirmPassword;

    private Set<Address> addresses;
    private Set<Order> orders;
    private Cart cart;

    public User() {
        this.addresses = new HashSet<>();
        this.orders = new HashSet<>();
    }

    public User(String firstName, String lastName, String email, String password, String contactNumber,
                boolean enabled, Role role) {
        this();
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

    public void setFirstName(String firstName) throws BackendLogicalException {
        if (firstName == null) {
            throw new NullReferenceException("The firstName must be indicated in the user! ");
        }
        if (firstName.isEmpty()) {
            throw new EmptyFieldException("The firstName must be filled in the user! ");
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

    public void setEmail(String email) throws BackendLogicalException {
        if (email == null) {
            throw new NullReferenceException("The email must be indicated in the user! ");
        }
        if (email.isEmpty()) {
            throw new EmptyFieldException("The email must be filled in the user! ");
        }
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws BackendLogicalException {
        if (password == null) {
            throw new NullReferenceException("The password must be indicated in the user! ");
        }
        if (password.isEmpty()) {
            throw new EmptyFieldException("The password must be filled in the user! ");
        }
        this.password = password;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) throws BackendLogicalException {
        if (contactNumber == null) {
            throw new NullReferenceException("The contactNumber must be indicated in the user! ");
        }
        if (contactNumber.isEmpty()) {
            throw new EmptyFieldException("The contactNumber must be filled in the user! ");
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

    public void setAddresses(Address...addresses) throws NullReferenceException {
        if (addresses == null) {
            throw new NullReferenceException("The user can't be without an address! ");
        }
        this.addresses.addAll(Arrays.asList(addresses));
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Order...orders) {
        this.orders.addAll(Arrays.asList(orders));
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) throws NullReferenceException {
        if (cart == null) {
            throw new NullReferenceException("The user mast be have a cart by default! ");
        }
        this.cart = cart;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) throws NullReferenceException {
        if (role == null) {
            throw new NullReferenceException("The role must be indicated in the user! ");
        }
        this.role = role;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) throws BackendLogicalException {
        if (confirmPassword == null) {
            throw new NullReferenceException("The confirmPassword must not be null! ");
        }
        if (confirmPassword.isEmpty()){
            throw new EmptyFieldException("The confirmPassword must not be empty! ");
        }
        this.confirmPassword = confirmPassword;
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
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return enabled == user.enabled &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(contactNumber, user.contactNumber) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, password, contactNumber, enabled, role);
    }
}

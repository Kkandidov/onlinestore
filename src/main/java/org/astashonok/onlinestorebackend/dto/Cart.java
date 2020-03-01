package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NegativeValueException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceToRequiredObject;

import java.util.Set;

public class Cart extends Entity {
    private User user;
    private double total;
    private int cartItems;

    private Set<CartItem> cartItemSet;

    public Cart() {
    }

    public Cart(User user, double total, int cartItems) {
        this.user = user;
        this.total = total;
        this.cartItems = cartItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) throws NullReferenceToRequiredObject {
        if (user == null) {
            throw new NullReferenceToRequiredObject("The cart can't exist without a user! ");
        }

        this.user = user;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) throws NegativeValueException {
        if (total < 0){
            throw new NegativeValueException();
        }
        this.total = total;
    }

    public int getCartItems() {
        return cartItems;
    }

    public void setCartItems(int cartItems) throws NegativeValueException {
        if (cartItems < 0){
            throw new NegativeValueException();
        }
        this.cartItems = cartItems;
    }

    public Set<CartItem> getCartItemSet() {
        return cartItemSet;
    }

    public void setCartItemSet(Set<CartItem> cartItemSet) {
        this.cartItemSet = cartItemSet;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "user=" + user +
                ", total=" + total +
                ", cartItems=" + cartItems +
                '}';
    }
}

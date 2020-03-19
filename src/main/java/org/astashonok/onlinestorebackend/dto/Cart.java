package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NegativeValueException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Cart extends Entity {
    private User user;
    private double total;
    private int cartItems;

    private Set<CartItem> cartItemSet;

    public Cart() {
        this.cartItemSet = new HashSet<>();
    }

    public Cart(User user, double total, int cartItems) {
        this();
        this.user = user;
        this.total = total;
        this.cartItems = cartItems;
        super.id = user.getId();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) throws NullReferenceException {
        if (user == null) {
            throw new NullReferenceException("The cart can't exist without a user! ");
        }
        super.id = user.getId();
        this.user = user;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) throws NegativeValueException {
        if (total < 0) {
            throw new NegativeValueException("The total must be from 0! ");
        }
        this.total = total;
    }

    public int getCartItems() {
        return cartItems;
    }

    public void setCartItems(int cartItems) throws NegativeValueException {
        if (cartItems < 0) {
            throw new NegativeValueException("The count of cartItems must be from 0! ");
        }
        this.cartItems = cartItems;
    }

    public Set<CartItem> getCartItemSet() {
        return cartItemSet;
    }

    public void setCartItemSet(CartItem... cartItemSet) {
        this.cartItemSet.addAll(Arrays.asList(cartItemSet));
    }

    @Override
    public String toString() {
        return "Cart{" +
                "user=" + user +
                ", total=" + total +
                ", cartItems=" + cartItems +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Double.compare(cart.total, total) == 0 &&
                cartItems == cart.cartItems &&
                Objects.equals(user, cart.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, total, cartItems);
    }
}

package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.EmptyFieldException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NegativeValueException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NotPositiveValue;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceToRequiredObject;

import java.util.Set;

public class Product extends Entity {
    private String name;
    private String code;
    private Brand brand;
    private double unitPrice;
    private int quantity;
    private boolean active;
    private Category category;

    private Description description;
    private Set<View> views;
    private Set<CartItem> cartItems;
    private Set<OrderItem> orderItems;

    public Product() {
    }

    public Product(String name, String code, Brand brand, double unitPrice, int quantity, boolean active
                   , Category category) {
        this.name = name;
        this.code = code;
        this.brand = brand;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.active = active;
        this.category = category;
    }

    public Product(long id, String name, String code, Brand brand, double unitPrice, int quantity, boolean active,
                   Category category) {
        this(name, code, brand, unitPrice, quantity, active, category);
        super.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws OnlineStoreLogicalException {
        if (name == null) {
            throw new NullReferenceToRequiredObject("The product has to have name ");
        }

        if (name.isEmpty()) {
            throw new EmptyFieldException();
        }

        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) throws OnlineStoreLogicalException {
        if (code == null) {
            throw new NullReferenceToRequiredObject("The product has to have code ");
        }

        if (code.isEmpty()) {
            throw new EmptyFieldException();
        }

        this.code = code;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) throws NullReferenceToRequiredObject {
        if (brand == null) {
            throw new NullReferenceToRequiredObject("The product has to have brand ");
        }

        this.brand = brand;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) throws NegativeValueException {
        if (unitPrice < 0) {
            throw new NegativeValueException("The price can't be negative ");
        }

        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) throws NotPositiveValue {
        if (quantity < 1) {
            throw new NotPositiveValue("The quantity has to have positive value! ");
        }

        this.quantity = quantity;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) throws NullReferenceToRequiredObject {
        if (category == null) {
            throw new NullReferenceToRequiredObject("The product has to have category ");
        }

        this.category = category;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) throws NullReferenceToRequiredObject {
        if (description == null) {
            throw new NullReferenceToRequiredObject("The product has to have description ");
        }

        this.description = description;
    }

    public Set<View> getViews() {
        return views;
    }

    public void setViews(Set<View> views) {
        this.views = views;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Product{ " +
                super.toString() +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", brand=" + brand +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", active=" + active +
                ", category=" + category +
                '}';
    }
}

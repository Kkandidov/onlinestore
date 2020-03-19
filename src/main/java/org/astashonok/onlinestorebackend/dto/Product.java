package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.EmptyFieldException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NegativeValueException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
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
        this.views = new HashSet<>();
        this.cartItems = new HashSet<>();
        this.orderItems = new HashSet<>();
    }

    public Product(String name, String code, Brand brand, double unitPrice, int quantity, boolean active
            , Category category) {
        this();
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
            throw new NullReferenceException("The name must be indicated in the product! ");
        }
        if (name.isEmpty()) {
            throw new EmptyFieldException("The name must be filled in the address! ");
        }
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) throws OnlineStoreLogicalException {
        if (code == null) {
            throw new NullReferenceException("The name must be indicated in the product! ");
        }
        if (code.isEmpty()) {
            throw new EmptyFieldException("The name must be filled in the address! ");
        }
        this.code = code;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) throws NullReferenceException {
        if (brand == null) {
            throw new NullReferenceException("The brand must be indicated in the product! ");
        }
        this.brand = brand;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) throws NegativeValueException {
        if (unitPrice < 0) {
            throw new NegativeValueException("The price must be from 0! ");
        }
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) throws NegativeValueException {
        if (quantity < 0) {
            throw new NegativeValueException("The quantity must be from 0! ");
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

    public void setCategory(Category category) throws NullReferenceException {
        if (category == null) {
            throw new NullReferenceException("The category must be indicated in the product! ");
        }
        this.category = category;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) throws NullReferenceException {
        if (description == null) {
            throw new NullReferenceException("The description must be indicated in the product!");
        }
        this.description = description;
    }

    public Set<View> getViews() {
        return views;
    }

    public void setViews(View... views) {
        this.views.addAll(Arrays.asList(views));
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(CartItem... cartItems) {
        this.cartItems.addAll(Arrays.asList(cartItems));
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(OrderItem... orderItems) {
        this.orderItems.addAll(Arrays.asList(orderItems));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.unitPrice, unitPrice) == 0 &&
                quantity == product.quantity &&
                active == product.active &&
                Objects.equals(name, product.name) &&
                Objects.equals(code, product.code) &&
                Objects.equals(brand, product.brand) &&
                Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, code, brand, unitPrice, quantity, active, category);
    }
}

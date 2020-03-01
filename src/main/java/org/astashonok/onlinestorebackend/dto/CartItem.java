package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NegativeValueException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NotPositiveValue;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceToRequiredObject;

public class CartItem extends Entity {
    private Cart cart;
    private double total;
    private Product product;
    private int productCount;
    private double productPrice;
    private boolean available;

    public CartItem() {
    }

    public CartItem(Cart cart, double total, Product product, int productCount, double productPrice,
                    boolean available) {
        this.cart = cart;
        this.total = total;
        this.product = product;
        this.productCount = productCount;
        this.productPrice = productPrice;
        this.available = available;
    }

    public CartItem(long id, Cart cart, double total, Product product, int productCount, double productPrice,
                    boolean available) {
        this(cart, total, product, productCount, productPrice, available);
        super.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) throws NullReferenceToRequiredObject {
        if (cart == null) {
            throw new NullReferenceToRequiredObject("The cart item can't exist without cart ");
        }

        this.cart = cart;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) throws NegativeValueException {
        if (total < 0) {
            throw new NegativeValueException();
        }

        this.total = total;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) throws NullReferenceToRequiredObject {
        if (product == null) {
            throw new NullReferenceToRequiredObject("If there are cart item, then the product has to match it ");
        }

        this.product = product;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) throws NotPositiveValue {
        if (productCount < 1) {
            throw new NotPositiveValue("The product count has to have positive value! ");
        }

        this.productCount = productCount;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) throws NegativeValueException {
        if (productPrice < 0) {
            throw new NegativeValueException("The price can't be negative ");
        }

        this.productPrice = productPrice;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                super.toString() +
                ", cart=" + cart +
                ", total=" + total +
                ", product=" + product +
                ", productCount=" + productCount +
                ", productPrice=" + productPrice +
                ", available=" + available +
                '}';
    }
}

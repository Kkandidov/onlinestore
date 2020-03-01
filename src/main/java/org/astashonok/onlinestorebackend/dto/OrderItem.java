package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NegativeValueException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NotPositiveValue;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceToRequiredObject;

public class OrderItem extends Entity {
    private Order order;
    private double total;
    private Product product;
    private int productCount;
    private double productPrice;

    public OrderItem() {
    }

    public OrderItem(Order order, double total, Product product, int productCount, double productPrice) {
        this.order = order;
        this.total = total;
        this.product = product;
        this.productCount = productCount;
        this.productPrice = productPrice;
    }

    public OrderItem(long id, Order order, double total, Product product, int productCount, double productPrice) {
        this(order, total, product, productCount, productPrice);
        super.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) throws NullReferenceToRequiredObject {
        if (order == null) {
            throw new NullReferenceToRequiredObject("The order item has to match a specific order ");
        }

        this.order = order;
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
            throw new NullReferenceToRequiredObject("If there are order item, then the product has to match it ");
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

    @Override
    public String toString() {
        return "OrderItem{" +
                super.toString() +
                ", order=" + order +
                ", total=" + total +
                ", product=" + product +
                ", productCount=" + productCount +
                ", productPrice=" + productPrice +
                '}';
    }
}

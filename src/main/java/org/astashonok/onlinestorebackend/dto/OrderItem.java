package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NegativeValueException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceException;

import java.util.Objects;

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

    public void setOrder(Order order) throws NullReferenceException {
        if (order == null) {
            throw new NullReferenceException("The order must be indicated in the orderItem! ");
        }
        this.order = order;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) throws NullReferenceException {
        if (product == null) {
            throw new NullReferenceException("If there are orderItem, then the product has to match it! ");
        }
        this.product = product;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) throws NegativeValueException {
        if (productCount < 0) {
            throw new NegativeValueException("The product count must be from 0! ");
        }
        this.productCount = productCount;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) throws NegativeValueException {
        if (productPrice < 0) {
            throw new NegativeValueException("The product price must be from 0! ");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Double.compare(orderItem.total, total) == 0 &&
                productCount == orderItem.productCount &&
                Double.compare(orderItem.productPrice, productPrice) == 0 &&
                Objects.equals(order, orderItem.order) &&
                Objects.equals(product, orderItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, total, product, productCount, productPrice);
    }
}

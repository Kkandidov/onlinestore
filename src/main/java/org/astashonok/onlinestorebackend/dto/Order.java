package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NegativeValueException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceException;

import java.util.*;

public class Order extends Entity {
    private User user;
    private double total;
    private int count;
    private Address shipping;
    private Address billing;
    private Date date;

    private Set<OrderItem> orderItems;

    public Order() {
        this.orderItems = new HashSet<>();
    }

    public Order(User user, double total, int count, Address shipping, Address billing, Date date) {
        this();
        this.user = user;
        this.total = total;
        this.count = count;
        this.shipping = shipping;
        this.billing = billing;
        this.date = date;
    }

    public Order(long id, User user, double total, int count, Address shipping, Address billing, Date date) {
        this(user, total, count, shipping, billing, date);
        super.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) throws NullReferenceException {
        if (user == null) {
            throw new NullReferenceException("The order can't exist without a user! ");
        }
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) throws NegativeValueException {
        if (count < 0) {
            throw new NegativeValueException("The product count must be from 0! ");
        }
        this.count = count;
    }

    public Address getShipping() {
        return shipping;
    }

    public void setShipping(Address shipping) throws NullReferenceException {
        if (shipping == null) {
            throw new NullReferenceException("Shipping address is required for order delivery! ");
        }
        this.shipping = shipping;
    }

    public Address getBilling() {
        return billing;
    }

    public void setBilling(Address billing) throws NullReferenceException {
        if (billing == null) {
            throw new NullReferenceException("Billing address needs to verify the plastic card! ");
        }
        this.billing = billing;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) throws NullReferenceException {
        if (date == null) {
            throw new NullReferenceException("The order must be placed on a specific date! ");
        }
        this.date = date;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(OrderItem...array) throws NullReferenceException {
        if (array == null) {
            throw new NullReferenceException("Order can' be empty! ");
        }
        this.orderItems.addAll(Arrays.asList(array));
    }

    @Override
    public String toString() {
        return "Order{" +
                super.toString() +
                ", user=" + user +
                ", total=" + total +
                ", count=" + count +
                ", shipping=" + shipping +
                ", billing=" + billing +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(order.total, total) == 0 &&
                count == order.count &&
                Objects.equals(user, order.user) &&
                Objects.equals(shipping, order.shipping) &&
                Objects.equals(billing, order.billing) &&
                Objects.equals(date, order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, total, count, shipping, billing, date);
    }
}

package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NegativeValueException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NotPositiveValue;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceToRequiredObject;

import java.util.Date;
import java.util.Set;

public class Order extends Entity {
    private User user;
    private double total;
    private int count;
    private Address shipping;
    private Address billing;
    private Date date;

    private Set<OrderItem> orderItems;

    public Order() {
    }

    public Order(User user, double total, int count, Address shipping, Address billing, Date date) {
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

    public void setUser(User user) throws NullReferenceToRequiredObject {
        if (user == null) {
            throw new NullReferenceToRequiredObject("The order can't exist without a user ");
        }

        this.user = user;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) throws NotPositiveValue {
        if (count < 1) {
            throw new NotPositiveValue("The product count has to have positive value! ");
        }

        this.count = count;
    }

    public Address getShipping() {
        return shipping;
    }

    public void setShipping(Address shipping) throws NullReferenceToRequiredObject {
        if (shipping == null) {
            throw new NullReferenceToRequiredObject("Shipping address is required for order delivery ");
        }

        this.shipping = shipping;
    }

    public Address getBilling() {
        return billing;
    }

    public void setBilling(Address billing) throws NullReferenceToRequiredObject {
        if (billing == null) {
            throw new NullReferenceToRequiredObject("Billing address needs to verify the plastic card ");
        }

        this.billing = billing;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) throws NullReferenceToRequiredObject {
        if (date == null) {
            throw new NullReferenceToRequiredObject("The order has to be placed on a specific date ");
        }

        this.date = date;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) throws NullReferenceToRequiredObject {
        if (orderItems == null) {
            throw new NullReferenceToRequiredObject("Order can' be empty ");
        }

        this.orderItems = orderItems;
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
}

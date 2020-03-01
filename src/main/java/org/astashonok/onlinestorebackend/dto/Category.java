package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.EmptyFieldException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceToRequiredObject;

import java.util.Set;

public class Category extends Entity {
    private String name;
    private boolean active;

    private Set<Product> products;

    public Category() {
    }

    public Category(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public Category(long id, String name, boolean active) {
        this(name, active);
        super.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws OnlineStoreLogicalException {
        if (name == null) {
            throw new NullReferenceToRequiredObject("The category has to have name ");
        }

        if (name.isEmpty()) {
            throw new EmptyFieldException();
        }

        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Category{" +
                super.toString() +
                ", name='" + name + '\'' +
                ", active=" + active +
                '}';
    }
}

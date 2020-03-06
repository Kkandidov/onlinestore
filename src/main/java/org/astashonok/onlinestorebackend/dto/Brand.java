package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.EmptyFieldException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Brand extends Entity {
    private String name;
    private String description;
    private boolean active;

    private Set<Product> products;

    public Brand() {
        this.products = new HashSet<>();
    }

    public Brand(String name, String description, boolean active) {
        this();
        this.name = name;
        this.description = description;
        this.active = active;
    }

    public Brand(long id, String name, String description, boolean active) {
        this(name, description, active);
        super.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws OnlineStoreLogicalException {
        if (name == null) {
            throw new NullReferenceException("The name must be indicated in the brand! ");
        }
        if (name.isEmpty()) {
            throw new EmptyFieldException("The name must be filled in the brand! ");
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Product...products) {
        this.products.addAll(Arrays.asList(products));
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Brand{" +
                super.toString() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

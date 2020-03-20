package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendLogicalException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.EmptyFieldException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Category extends Entity {
    private String name;
    private boolean active;

    private Set<Product> products;

    public Category() {
        products = new HashSet<>();
    }

    public Category(String name, boolean active) {
        this();
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

    public void setName(String name) throws BackendLogicalException {
        if (name == null) {
            throw new NullReferenceException("The name must be indicated in the category! ");
        }
        if (name.isEmpty()) {
            throw new EmptyFieldException("The name must be filled in the category! ");
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

    public void setProducts(Product... products) {

        this.products.addAll(Arrays.asList(products));
    }

    @Override
    public String toString() {
        return "Category{" +
                super.toString() +
                ", name='" + name + '\'' +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return active == category.active &&
                Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, active);
    }
}

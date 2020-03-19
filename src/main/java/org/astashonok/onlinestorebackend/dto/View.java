package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.EmptyFieldException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceException;

import java.util.Objects;

public class View extends Entity {
    private String code;
    private Product product;

    public View() {
    }

    public View(String code, Product product) {
        this.code = code;
        this.product = product;
    }

    public View(long id, String code, Product product) {
        this(code, product);
        super.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) throws OnlineStoreLogicalException {
        if (code == null) {
            throw new NullReferenceException("The code must be indicated in the view! ");
        }
        if (code.isEmpty()) {
            throw new EmptyFieldException("The code must be filled in the view! ");
        }
        this.code = code;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) throws NullReferenceException {
        if (product == null) {
            throw new NullReferenceException("The code must be product in the view! ");
        }
        this.product = product;
    }

    @Override
    public String toString() {
        return "View{" +
                super.toString() +
                ", code='" + code + '\'' +
                ", product=" + product +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        View view = (View) o;
        return Objects.equals(code, view.code) &&
                Objects.equals(product, view.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, product);
    }
}

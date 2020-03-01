package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.EmptyFieldException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceToRequiredObject;

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
            throw new NullReferenceToRequiredObject("The view has to have a code ");
        }

        if (code.isEmpty()) {
            throw new EmptyFieldException();
        }

        this.code = code;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) throws NullReferenceToRequiredObject {
        if (product == null) {
            throw new NullReferenceToRequiredObject("The view has to have a product ");
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
}

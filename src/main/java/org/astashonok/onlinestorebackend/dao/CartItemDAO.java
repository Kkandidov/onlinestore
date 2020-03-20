package org.astashonok.onlinestorebackend.dao;

import org.astashonok.onlinestorebackend.dao.abstracts.EntityDAO;
import org.astashonok.onlinestorebackend.dto.Cart;
import org.astashonok.onlinestorebackend.dto.CartItem;
import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;

import java.util.List;

public interface CartItemDAO extends EntityDAO<CartItem> {

    // read
    List<CartItem> getByCart(Cart cart) throws BackendException;

    List<CartItem> getAvailableByCart(Cart cart) throws BackendException;

    List<CartItem> getByProduct(Product product) throws BackendException;

    CartItem getByCartAndProduct(Cart cart, Product product) throws BackendException;

}

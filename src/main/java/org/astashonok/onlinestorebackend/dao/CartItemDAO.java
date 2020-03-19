package org.astashonok.onlinestorebackend.dao;

import org.astashonok.onlinestorebackend.dao.abstracts.EntityDAO;
import org.astashonok.onlinestorebackend.dto.Cart;
import org.astashonok.onlinestorebackend.dto.CartItem;
import org.astashonok.onlinestorebackend.dto.Product;

import java.util.List;

public interface CartItemDAO extends EntityDAO<CartItem> {

    // read
    List<CartItem> getByCart(Cart cart);

    List<CartItem> getAvailableByCart(Cart cart);

    List<CartItem> getByProduct(Product product);

    CartItem getByCartAndProduct(Cart cart, Product product);

}

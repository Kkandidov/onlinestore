package org.astashonok.onlinestorebackend.dao;

import org.astashonok.onlinestorebackend.dao.abstracts.EntityDAO;
import org.astashonok.onlinestorebackend.dto.Cart;
import org.astashonok.onlinestorebackend.dto.User;

public interface CartDAO extends EntityDAO<Cart> {

    //read
    Cart getByUser(User user);

}

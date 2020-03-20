package org.astashonok.onlinestorebackend.dao;

import org.astashonok.onlinestorebackend.dao.abstracts.EntityDAO;
import org.astashonok.onlinestorebackend.dto.Address;
import org.astashonok.onlinestorebackend.dto.Order;
import org.astashonok.onlinestorebackend.dto.User;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;

import java.util.List;

public interface OrderDAO extends EntityDAO<Order> {

    // read
    List<Order> getByUser(User user) throws BackendException;

    List<Order> getByAddress(Address address) throws BackendException;

}

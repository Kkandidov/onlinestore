package org.astashonok.onlinestorebackend.dao;

import org.astashonok.onlinestorebackend.dao.abstracts.EntityDAO;
import org.astashonok.onlinestorebackend.dto.Order;
import org.astashonok.onlinestorebackend.dto.OrderItem;
import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;

import java.util.List;

public interface OrderItemDAO extends EntityDAO<OrderItem> {

    // read
    List<OrderItem> getByOrder(Order order) throws BackendException;

    List<OrderItem> getByProduct(Product product) throws BackendException;

    OrderItem getByOrderAndProduct(Order order, Product product) throws BackendException;

}

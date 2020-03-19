package org.astashonok.onlinestorebackend.dao;

import org.astashonok.onlinestorebackend.dao.abstracts.EntityDAO;
import org.astashonok.onlinestorebackend.dto.Order;
import org.astashonok.onlinestorebackend.dto.OrderItem;
import org.astashonok.onlinestorebackend.dto.Product;

import java.util.List;

public interface OrderItemDAO extends EntityDAO<OrderItem> {

    // read
    List<OrderItem> getByOrder(Order order);

    List<OrderItem> getByProduct(Product product);

    OrderItem getByOrderAndProduct(Order order, Product product);

}

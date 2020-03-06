package org.astashonok.onlinestorebackend.dao;

import org.astashonok.onlinestorebackend.dao.abstracts.EntityDAO;
import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.dto.View;

import java.util.List;

public interface ViewDAO extends EntityDAO<View> {

    // read
    List<View> getByProduct(Product product);

}

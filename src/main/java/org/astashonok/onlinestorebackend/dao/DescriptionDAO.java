package org.astashonok.onlinestorebackend.dao;

import org.astashonok.onlinestorebackend.dao.abstracts.EntityDAO;
import org.astashonok.onlinestorebackend.dto.Description;
import org.astashonok.onlinestorebackend.dto.Product;

public interface DescriptionDAO extends EntityDAO<Description> {

    // read
    Description getByProduct(Product product);

}

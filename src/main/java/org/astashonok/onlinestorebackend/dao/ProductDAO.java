package org.astashonok.onlinestorebackend.dao;

import org.astashonok.onlinestorebackend.dao.abstracts.EntityDAO;
import org.astashonok.onlinestorebackend.dto.Brand;
import org.astashonok.onlinestorebackend.dto.Category;
import org.astashonok.onlinestorebackend.dto.Product;

import java.util.List;

public interface ProductDAO extends EntityDAO<Product> {

    // read
    List<Product> getAll();

    List<Product> getAllActive();

    List<Product> getAllActiveByBrand(Brand brand);

    List<Product> getAllActiveByCategory(Category category);
}

package org.astashonok.onlinestorebackend.dao;

import org.astashonok.onlinestorebackend.dao.abstracts.EntityDAO;
import org.astashonok.onlinestorebackend.dto.Brand;
import org.astashonok.onlinestorebackend.dto.Category;
import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;

import java.util.List;

public interface ProductDAO extends EntityDAO<Product> {

    // read
    List<Product> getAll() throws BackendException;

    List<Product> getAllActive() throws BackendException;

    List<Product> getAllActiveByBrand(Brand brand) throws BackendException;

    List<Product> getAllActiveByCategory(Category category) throws BackendException;
}

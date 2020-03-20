package org.astashonok.onlinestorebackend.dao;

import org.astashonok.onlinestorebackend.dao.abstracts.EntityDAO;
import org.astashonok.onlinestorebackend.dto.Brand;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;

import java.util.List;

public interface BrandDAO extends EntityDAO<Brand> {

    //read
    List<Brand> getAll() throws BackendException;

    List<Brand> getAllActive() throws BackendException;

    Brand getByName(String name) throws BackendException;

}

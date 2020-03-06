package org.astashonok.onlinestorebackend.dao;

import org.astashonok.onlinestorebackend.dao.abstracts.EntityDAO;
import org.astashonok.onlinestorebackend.dto.Brand;

import java.util.List;

public interface BrandDAO extends EntityDAO<Brand> {

    //read
    List<Brand> getAll();

    List<Brand> getAllActive();

    Brand getByName(String name);

}

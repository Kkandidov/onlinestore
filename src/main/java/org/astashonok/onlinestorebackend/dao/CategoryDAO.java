package org.astashonok.onlinestorebackend.dao;

import org.astashonok.onlinestorebackend.dao.abstracts.EntityDAO;
import org.astashonok.onlinestorebackend.dto.Category;

import java.util.List;

public interface CategoryDAO extends EntityDAO<Category> {

    //read
    List<Category> getAll();

    List<Category> getAllActive();

    Category getByName(String name);

}

package org.astashonok.onlinestorebackend.dao;

import org.astashonok.onlinestorebackend.dao.abstracts.EntityDAO;
import org.astashonok.onlinestorebackend.dto.Category;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;

import java.util.List;

public interface CategoryDAO extends EntityDAO<Category> {

    //read
    List<Category> getAll() throws BackendException;

    List<Category> getAllActive() throws BackendException;

    Category getByName(String name) throws BackendException;

}

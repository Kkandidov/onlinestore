package org.astashonok.onlinestorebackend.dao.abstracts;

import org.astashonok.onlinestorebackend.dto.abstracts.Entity;

public interface EntityDAO<T extends Entity> {
    //create
    boolean add(T entity);

    //read
    Entity getById(long id);

    //update
    boolean edit(T entity);

    //delete
    boolean remove(T entity);
}

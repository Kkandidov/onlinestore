package org.astashonok.onlinestorebackend.dao.abstracts;

import org.astashonok.onlinestorebackend.dto.abstracts.Entity;

public interface EntityDAO<T extends Entity> {
    //create (returns id or 0 if no entry is added)
    long add(T entity);

    //read
    T getById(long id);

    //update
    boolean edit(T entity);

    //delete
    boolean remove(T entity);
}

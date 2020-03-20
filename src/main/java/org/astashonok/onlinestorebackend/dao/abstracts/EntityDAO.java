package org.astashonok.onlinestorebackend.dao.abstracts;

import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;


public interface EntityDAO<T extends Entity> {
    //create (returns id or exception if no entry is added)
    long add(T entity) throws BackendException;

    //read
    T getById(long id) throws BackendException;

    //update
    boolean edit(T entity) throws BackendException;

    //delete
    boolean remove(T entity) throws BackendException;
}

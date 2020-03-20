package org.astashonok.onlinestorebackend.dao;

import org.astashonok.onlinestorebackend.dao.abstracts.EntityDAO;
import org.astashonok.onlinestorebackend.dto.Role;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;

import java.util.List;

public interface RoleDAO extends EntityDAO<Role> {

    //read
    List<Role> getAll() throws BackendException;

    List<Role> getAllActive() throws BackendException;

    Role getByName(String name) throws BackendException;

}

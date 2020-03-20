package org.astashonok.onlinestorebackend.dao;

import org.astashonok.onlinestorebackend.dao.abstracts.EntityDAO;
import org.astashonok.onlinestorebackend.dto.Role;
import org.astashonok.onlinestorebackend.dto.User;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;

import java.util.List;

public interface UserDAO extends EntityDAO<User> {

    //read
    User getByEmail(String email) throws BackendException;

    List<User> getAll() throws BackendException;

    List<User> getAllEnable() throws BackendException;

    List<User> getByRole(Role role) throws BackendException;

}

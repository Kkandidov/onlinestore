package org.astashonok.onlinestorebackend.dao;

import org.astashonok.onlinestorebackend.dao.abstracts.EntityDAO;
import org.astashonok.onlinestorebackend.dto.Role;
import org.astashonok.onlinestorebackend.dto.User;

import java.util.List;

public interface UserDAO extends EntityDAO<User> {

    //read
    User getByEmail(String email);

    List<User> getAll();

    List<User> getAllEnable();

    List<User> getByRole(Role role);

}

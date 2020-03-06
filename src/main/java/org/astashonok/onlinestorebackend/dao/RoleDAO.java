package org.astashonok.onlinestorebackend.dao;

import org.astashonok.onlinestorebackend.dao.abstracts.EntityDAO;
import org.astashonok.onlinestorebackend.dto.Role;

import java.util.List;

public interface RoleDAO extends EntityDAO<Role> {

    //read
    List<Role> getAll();

    List<Role> getAllActive();

    Role getByName(String name);

}

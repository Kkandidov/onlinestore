package org.astashonok.onlinestorebackend.dto;

import org.astashonok.onlinestorebackend.dto.abstracts.Entity;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendLogicalException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.EmptyFieldException;
import org.astashonok.onlinestorebackend.exceptions.logicalexception.NullReferenceException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Role extends Entity {
    private String name;
    private boolean active;

    private Set<User> users;

    public Role() {
        this.users = new HashSet<>();
    }

    public Role(String name, boolean active) {
        this();
        this.name = name;
        this.active = active;
    }

    public Role(long id, String name, boolean active) {
        this(name, active);
        super.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws BackendLogicalException {
        if (name == null) {
            throw new NullReferenceException("The name must be indicated in the role! ");
        }
        if (name.isEmpty()) {
            throw new EmptyFieldException("The name must be filled in the role! ");
        }
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(User...users) {

        this.users.addAll(Arrays.asList(users));
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Role{" +
                super.toString() +
                ", name='" + name + '\'' +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return active == role.active &&
                Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, active);
    }
}

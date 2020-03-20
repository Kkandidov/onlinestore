package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dto.Role;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;
import org.astashonok.onlinestorebackend.testconfig.SimpleSingleConnection;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.astashonok.onlinestorebackend.testconfig.StaticTestInitializer.*;
import static org.junit.Assert.*;

public class RoleDAOImplTest {

    @After
    public void resetDatabase() throws SQLException {
        System.out.println("Reset database...");
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.addBatch("SET FOREIGN_KEY_CHECKS=0");
        statement.addBatch("TRUNCATE TABLE roles");
        statement.addBatch("SET FOREIGN_KEY_CHECKS=1");
        statement.addBatch("INSERT INTO roles(name, active) VALUES('ADMIN', true)");
        statement.addBatch("INSERT INTO roles(name, active) VALUES('USER', true)");
        statement.executeBatch();
        connection.commit();
        System.out.println("Resetting is successfully!");
    }

    @Test
    public void getAll() throws BackendException {
        int expected = 2;
        int actual = roleDAO.getAll().size();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllActive() throws BackendException {
        Role role = role2;
        role.setActive(false);
        roleDAO.edit(role);
        assertFalse(roleDAO.getById(2).isActive());
        role.setActive(true);
    }

    @Test
    public void getByName() throws BackendException {
        Role expected = role1;
        Role actual = roleDAO.getByName("ADMIN");
        assertEquals(expected, actual);
    }

    @Test
    public void add() throws BackendException {
        Role expected = new Role("CUSTOMER", true);
        long id = roleDAO.add(expected);
        Role actual = roleDAO.getById(id);
        assertEquals(expected, actual);
    }

    @Test
    public void getById() throws BackendException {
        Role expected = role2;
        Role actual = roleDAO.getById(2);
        assertEquals(expected, actual);
    }

    @Test
    public void edit() throws BackendException {
        Role expected = role2;
        expected.setName("CUSTOMER");
        roleDAO.edit(expected);
        Role actual = roleDAO.getById(2);
        assertEquals(expected, actual);
        expected.setName("USER");
    }

    @Test
    public void remove() throws BackendException {
        roleDAO.remove(role1);
        assertFalse(roleDAO.getById(1).isActive());
        role1.setActive(true);
    }
}
package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dto.Category;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;
import org.astashonok.onlinestorebackend.testconfig.SimpleSingleConnection;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.astashonok.onlinestorebackend.testconfig.StaticTestInitializer.*;
import static org.junit.Assert.*;

public class CategoryDAOImplTest {

    @After
    public void resetDatabase() throws SQLException {
        System.out.println("Reset database...");
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.addBatch("SET FOREIGN_KEY_CHECKS=0");
        statement.addBatch("TRUNCATE TABLE categories");
        statement.addBatch("SET FOREIGN_KEY_CHECKS=1");
        statement.addBatch("INSERT INTO categories(name, active) VALUES('Mobile phone', true)");
        statement.addBatch("INSERT INTO categories(name, active) VALUES('Television', true)");
        statement.addBatch("INSERT INTO categories(name, active) VALUES('Laptop', true)");
        statement.addBatch("INSERT INTO categories(name, active) VALUES('Tablet', true)");
        statement.addBatch("INSERT INTO categories(name, active) VALUES('Player', true)");
        statement.executeBatch();
        connection.commit();
        System.out.println("Resetting is successfully!");
    }

    @Test
    public void getAll() throws BackendException {
        int expected = 5;
        int actual = categoryDAO.getAll().size();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllActive() throws BackendException {
        Category category = category1;
        category.setActive(false);
        categoryDAO.edit(category);
        int expected = 4;
        int actual = categoryDAO.getAllActive().size();
        assertEquals(expected, actual);
        category.setActive(true);
    }

    @Test
    public void getByName() throws BackendException {
        Category expected = category5;
        Category actual = categoryDAO.getByName("Player");
        assertEquals(expected, actual);
    }

    @Test
    public void add() throws SQLException, BackendException {
        Category expected = new Category("Watch", true);
        long id = categoryDAO.add(expected);
        Category actual = categoryDAO.getById(id);
        assertEquals(expected, actual);
    }

    @Test
    public void getById() throws BackendException {
        Category expected = category4;
        Category actual = categoryDAO.getById(4);
        assertEquals(expected, actual);
    }

    @Test
    public void edit() throws BackendException {
        Category expected = category1;
        expected.setName("Watch");
        categoryDAO.edit(expected);
        Category actual = categoryDAO.getById(1);
        assertEquals(expected, actual);
        expected.setName("Mobile phone");
    }

    @Test
    public void remove() throws BackendException {
        categoryDAO.remove(category1);
        assertFalse(categoryDAO.getById(1).isActive());
        category1.setActive(true);
    }
}
package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dto.Brand;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;
import org.astashonok.onlinestorebackend.testconfig.SimpleSingleConnection;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.astashonok.onlinestorebackend.testconfig.StaticTestInitializer.*;
import static org.junit.Assert.*;

public class BrandDAOImplTest {

    @After
    public void resetDatabase() throws SQLException {
        System.out.println("Reset database...");
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.addBatch("SET FOREIGN_KEY_CHECKS=0");
        statement.addBatch("TRUNCATE TABLE brands");
        statement.addBatch("SET FOREIGN_KEY_CHECKS=1");
        statement.addBatch("INSERT INTO brands(id, name, description, active)"
                + "VALUES(1, 'Samsung','Samsung, South Korean company that is one of the world’s largest "
                + "producers of electronic devices. It produces about a fifth of South Korea’s total exports', true)");
        statement.addBatch("INSERT INTO brands(id, name, description, active)" +
                "VALUES(3, 'Xiaomi','Xiaomi is a Chinese electronics company founded in 2010 by Lei Jun. The company "
                + "creates a wide range of products including hardware, software and Internet services', true)");
        statement.addBatch("INSERT INTO brands(id, name, description, active)"
                + "VALUES(10, 'Honor','Honor is a smartphone brand owned by Huawei Technologies. As part of the Huawei "
                + "Consumer Business Group\\'s dual-brand strategy, Honor provides smartphone handsets targeting young "
                + "consumers but has released tablet computers and wearable technology as well', true)");
        statement.executeBatch();
        connection.commit();
        System.out.println("Resetting is successfully!");
    }

    @Test
    public void getAll() throws BackendException {
        int expected = 3;
        int actual = brandDAO.getAll().size();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllActive() throws BackendException {
        Brand brand = brand3;
        brand.setActive(false);
        brandDAO.edit(brand);
        int expected = 2;
        int actual = brandDAO.getAllActive().size();
        assertEquals(expected, actual);
        brand.setActive(true);
    }

    @Test
    public void getByName() throws BackendException {
        Brand expected = brand3;
        Brand actual = brandDAO.getByName("Xiaomi");
        assertEquals(expected, actual);
    }

    @Test
    public void add() throws BackendException {
        Brand expected = new Brand("HP", "description", true);
        long id = brandDAO.add(expected);
        Brand actual = brandDAO.getById(id);
        assertEquals(expected, actual);
    }

    @Test
    public void getById() throws BackendException {
        Brand expected = brand10;
        Brand actual = brandDAO.getById(10);
        assertEquals(expected, actual);
    }

    @Test
    public void edit() throws BackendException {
        Brand expected = brand10;
        expected.setName("HP");
        brandDAO.edit(expected);
        Brand actual = brandDAO.getById(10);
        assertEquals(expected, actual);
        expected.setName("Honor");
    }

    @Test
    public void remove() throws BackendException {
        Brand brand = brand1;
        brandDAO.remove(brand);
        assertFalse(brandDAO.getById(1).isActive());
        brand.setActive(true);
    }
}
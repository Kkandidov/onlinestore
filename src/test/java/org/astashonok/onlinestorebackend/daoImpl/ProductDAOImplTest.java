package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;
import org.astashonok.onlinestorebackend.testconfig.SimpleSingleConnection;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.astashonok.onlinestorebackend.testconfig.StaticTestInitializer.*;
import static org.junit.Assert.*;

public class ProductDAOImplTest {

    @After
    public void resetDatabase() throws SQLException {
        System.out.println("Reset database...");
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.addBatch("SET FOREIGN_KEY_CHECKS=0");
        statement.addBatch("TRUNCATE TABLE products");
        statement.addBatch("SET FOREIGN_KEY_CHECKS=1");
        statement.addBatch("INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)"
                + "VALUES('HONOR 20 international version', 'MAIN1581865663258', 10, 400, 10, true, 1)");
        statement.addBatch("INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)"
                + "VALUES('Samsung Galaxy S10 G973', 'MAIN1581866051337', 1, 710, 8, true, 1)");
        statement.addBatch("INSERT INTO products(name, code, brand_id, unit_price, quantity, active, category_id)"
                + "VALUES('Xiaomi Mi 9T', 'MAIN1581866964489', 3, 343, 6, true, 1)");
        statement.addBatch("DELETE FROM descriptions WHERE id = 4");
        statement.executeBatch();
        connection.commit();
        System.out.println("Resetting is successfully!");
    }

    @Test
    public void getAll() throws BackendException {
        int expected = 3;
        int actual = productDAO.getAll().size();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllActive() throws BackendException {
        product1.setActive(false);
        assertTrue(productDAO.edit(product1));
        int expected = 2;
        int actual = productDAO.getAllActive().size();
        assertEquals(expected, actual);
        product1.setActive(true);
    }

    @Test
    public void getAllActiveByBrand() throws BackendException {
        Product expected = product1;
        Product actual = productDAO.getAllActiveByBrand(brand10).get(0);
        assertEquals(expected, actual);
    }

    @Test
    public void getAllActiveByCategory() throws BackendException {
        int expected = 3;
        int actual = productDAO.getAllActiveByCategory(category1).size();
        assertEquals(expected, actual);
    }

    @Test
    public void add() throws BackendException {
        Product expected = new Product("Samsung", "MAIN1581865663343", brand1, 300, 8
                , true, category1);
        long id = productDAO.add(expected);
        Product actual = productDAO.getById(id);
        assertEquals(expected, actual);
    }

    @Test
    public void getById() throws BackendException {
        Product expected = product1;
        Product actual = productDAO.getById(1);
        assertEquals(expected, actual);
    }

    @Test
    public void edit() throws BackendException {
        Product expected = product1;
        expected.setName("HONOR");
        productDAO.edit(expected);
        Product actual = productDAO.getById(1);
        assertEquals(expected, actual);
        expected.setName("HONOR 20 international version");
    }

    @Test
    public void remove() throws BackendException {
        productDAO.remove(product1);
        assertFalse(productDAO.getById(1).isActive());
        product1.setActive(true);
    }
}
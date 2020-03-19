package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dto.Brand;
import org.astashonok.onlinestorebackend.dto.Category;
import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.testconfig.SimpleSingleConnection;
import org.astashonok.onlinestorebackend.util.pool.Pools;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.astashonok.onlinestorebackend.testconfig.StaticInitializerDTO.*;
import static org.junit.Assert.*;

public class ProductDAOImplTest {

    private static ProductDAOImpl productDAO;
    private static BrandDAOImpl brandDAO;
    private static CategoryDAOImpl categoryDAO;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        productDAO = new ProductDAOImpl(Pools.newPool(SimpleSingleConnection.class));
        brandDAO = new BrandDAOImpl(Pools.newPool(SimpleSingleConnection.class));
        categoryDAO = new CategoryDAOImpl(Pools.newPool(SimpleSingleConnection.class));
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destruction of our object! ");
        productDAO = null;
        brandDAO = null;
        categoryDAO = null;
    }

    @Test
    public void getAll() {
        int expected = 3;
        int actual = productDAO.getAll().size();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllActive() {
        int expected = 3;
        int actual = productDAO.getAllActive().size();
        assertEquals(expected, actual);
        Product product = productDAO.getById(1);
        product.setActive(false);
        assertTrue(productDAO.edit(product));
        expected = 2;
        actual = productDAO.getAllActive().size();
        assertEquals(expected, actual);

//         database reset
        product.setActive(true);
        assertTrue(productDAO.edit(product));
    }

    @Test
    public void getAllActiveByBrand() throws SQLException {
        Product expected = productDAO.getById(1);
        Brand brand = brandDAO.getById(10);
        Product actual = productDAO.getAllActiveByBrand(brand).get(0);
        assertEquals(expected, actual);
        Product product = new Product("HONOR", "MAIN1581865663444", brand, 400, 10
                , true, category1);
        long id = productDAO.add(product);
        int expect = 2;
        int act = productDAO.getAllActiveByBrand(brand).size();
        assertEquals(expect, act);

//         database reset
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        String sqlForDescription = "DELETE FROM descriptions WHERE id = " + id;
        String sqlForProduct = "DELETE FROM products WHERE id = " + id;
        Statement statement = connection.createStatement();
        connection.setAutoCommit(false);
        statement.addBatch(sqlForDescription);
        statement.addBatch(sqlForProduct);
        statement.executeBatch();
        connection.commit();
    }

    @Test
    public void getAllActiveByCategory() {
        Category category = categoryDAO.getById(1);
        int expected = 3;
        int actual = productDAO.getAllActiveByCategory(category).size();
        assertEquals(expected, actual);
    }

    @Test
    public void add() throws SQLException {
        Brand brand = brandDAO.getById(1);
        Category category = categoryDAO.getById(1);
        Product expected = new Product("Samsung", "MAIN1581865663343", brand, 300, 8
                , true, category);
        long id = productDAO.add(expected);
        Product actual = productDAO.getById(id);
        assertEquals(expected, actual);

        //database reset
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        String sqlForDescription = "DELETE FROM descriptions WHERE id = " + id;
        String sqlForProduct = "DELETE FROM products WHERE id = " + id;
        Statement statement = connection.createStatement();
        connection.setAutoCommit(false);
        statement.addBatch(sqlForDescription);
        statement.addBatch(sqlForProduct);
        statement.executeBatch();
        connection.commit();
    }

    @Test
    public void getById() {
        Product expected = product1;
        Product actual = productDAO.getById(1);
        assertEquals(expected, actual);
    }

    @Test
    public void edit() throws OnlineStoreLogicalException {
        Product expected = productDAO.getById(1);
        expected.setName("HONOR");
        assertTrue(productDAO.edit(expected));
        Product actual = productDAO.getById(1);
        assertEquals(expected, actual);

        //database reset
        expected.setName("HONOR 20 international version");
        assertTrue(productDAO.edit(expected));
    }

    @Test
    public void remove() {
        Product product = productDAO.getById(1);
        assertTrue(product.isActive());
        assertTrue(productDAO.remove(product));
        assertFalse(productDAO.getById(1).isActive());

        // database reset
        product.setActive(true);
        assertTrue(productDAO.edit(product));
    }
}
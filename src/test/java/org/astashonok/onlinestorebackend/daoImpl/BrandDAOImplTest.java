package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dto.Brand;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.testconfig.SimpleSingleConnection;
import org.astashonok.onlinestorebackend.util.pool.Pools;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.astashonok.onlinestorebackend.testconfig.StaticInitializerDTO.brand10;
import static org.astashonok.onlinestorebackend.testconfig.StaticInitializerDTO.brand3;
import static org.junit.Assert.*;

public class BrandDAOImplTest {

    private static BrandDAOImpl brandDAO;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        brandDAO = new BrandDAOImpl(Pools.newPool(SimpleSingleConnection.class));
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destruction of our object! ");
        brandDAO = null;
    }

    @Test
    public void getAll() {
        int expected = 13;
        int actual = brandDAO.getAll().size();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllActive() {
        int expected = 13;
        int actual = brandDAO.getAllActive().size();
        assertEquals(expected, actual);
        Brand brand = brandDAO.getById(3);
        brand.setActive(false);
        assertTrue(brandDAO.edit(brand));
        expected = 12;
        actual = brandDAO.getAllActive().size();
        assertEquals(expected, actual);
        assertFalse(brandDAO.getById(3).isActive());

        //database reset
        brand.setActive(true);
        assertTrue(brandDAO.edit(brand));
    }

    @Test
    public void getByName() {
        Brand expected = brand3;
        Brand actual = brandDAO.getByName("Xiaomi");
        assertEquals(expected, actual);
    }

    @Test
    public void add() throws SQLException {
        Brand expected = new Brand("HP", "description", true);
        long id = brandDAO.add(expected);
        Brand actual = brandDAO.getById(id);
        assertEquals(expected, actual);

        //database reset
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        String sql = "DELETE FROM brands WHERE id = " + id;
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Test
    public void getById() {
        Brand expected = brand10;
        Brand actual = brandDAO.getById(10);
        assertEquals(expected, actual);
    }

    @Test
    public void edit() throws OnlineStoreLogicalException {
        Brand expected = brandDAO.getById(8);
        expected.setName("HP");
        assertTrue(brandDAO.edit(expected));
        Brand actual = brandDAO.getById(8);
        assertEquals(expected, actual);

        //database reset
        expected.setName("Sony");
        brandDAO.edit(expected);
    }

//    @Test
//    public void remove() {
//        Brand brand = brandDAO.getById(6);
//        assertTrue(brandDAO.remove(brand));
//        assertFalse(brandDAO.getById(6).isActive());
//
//        //database reset
//        brand.setActive(true);
//        brandDAO.edit(brand);
//    }
}
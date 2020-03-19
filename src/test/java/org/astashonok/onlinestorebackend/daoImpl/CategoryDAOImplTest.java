package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dto.Category;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.testconfig.SimpleSingleConnection;
import org.astashonok.onlinestorebackend.util.pool.Pools;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.astashonok.onlinestorebackend.testconfig.StaticInitializerDTO.category4;
import static org.astashonok.onlinestorebackend.testconfig.StaticInitializerDTO.category5;
import static org.junit.Assert.*;

public class CategoryDAOImplTest {

    private static CategoryDAOImpl categoryDAO;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        categoryDAO = new CategoryDAOImpl(Pools.newPool(SimpleSingleConnection.class));
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destruction of our object! ");
        categoryDAO = null;
    }

    @Test
    public void getAll() {
        int expected = 5;
        int actual = categoryDAO.getAll().size();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllActive() {
        int expected = 5;
        int actual = categoryDAO.getAllActive().size();
        assertEquals(expected, actual);
        Category category = categoryDAO.getById(1);
        category.setActive(false);
        assertTrue(categoryDAO.edit(category));
        expected = 4;
        actual = categoryDAO.getAllActive().size();
        assertEquals(expected, actual);

        // database reset
        category.setActive(true);
        assertTrue(categoryDAO.edit(category));
    }

    @Test
    public void getByName() {
        Category expected = category5;
        Category actual = categoryDAO.getByName("Player");
        assertEquals(expected, actual);
    }

    @Test
    public void add() throws SQLException {
        Category expected = new Category("Watch", true);
        long id = categoryDAO.add(expected);
        Category actual = categoryDAO.getById(id);
        assertEquals(expected, actual);

        //database reset
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        String sql = "DELETE FROM categories WHERE id = " + id;
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Test
    public void getById() {
        Category expected = category4;
        Category actual = categoryDAO.getById(4);
        assertEquals(expected, actual);
    }

    @Test
    public void edit() throws OnlineStoreLogicalException {
        Category expected = categoryDAO.getById(1);
        expected.setName("Watch");
        assertTrue(categoryDAO.edit(expected));
        Category actual = categoryDAO.getById(1);
        assertEquals(expected, actual);

        //database reset
        expected.setName("Mobile phone");
        categoryDAO.edit(expected);
    }

//    @Test
//    public void remove() {
//        Category category = categoryDAO.getById(1);
//        assertTrue(categoryDAO.remove(category));
//        assertFalse(categoryDAO.getById(1).isActive());
//
//        //database reset
//        category.setActive(true);
//        categoryDAO.edit(category);
//    }
}
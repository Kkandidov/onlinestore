package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dto.Product;
import org.astashonok.onlinestorebackend.dto.View;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.testconfig.SimpleSingleConnection;
import org.astashonok.onlinestorebackend.util.pool.Pools;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.astashonok.onlinestorebackend.testconfig.StaticInitializerDTO.view32;
import static org.junit.Assert.*;

public class ViewDAOImplTest {

    private static ViewDAOImpl viewDAO;
    private static ProductDAOImpl productDAO;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        viewDAO = new ViewDAOImpl(Pools.newPool(SimpleSingleConnection.class));
        productDAO = new ProductDAOImpl(Pools.newPool(SimpleSingleConnection.class));
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destruction of our object! ");
        viewDAO = null;
        productDAO = null;
    }

    @Test
    public void getByProduct() {
        Product product = productDAO.getById(1);
        int expected = 5;
        int actual = viewDAO.getByProduct(product).size();
        assertEquals(expected, actual);

        product = productDAO.getById(3);
        expected = 2;
        actual = viewDAO.getByProduct(product).size();
        assertEquals(expected, actual);
    }

    @Test
    public void add() throws SQLException {
        Product product = productDAO.getById(3);
        View view = new View("PRD1111111111111", product);
        long id = viewDAO.add(view);
        int expected = 3;
        int actual = viewDAO.getByProduct(product).size();
        assertEquals(expected, actual);

        // database reset
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        String sql = "DELETE FROM views WHERE id = " + id;
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Test
    public void getById() {
        View expected = view32;
        View actual = viewDAO.getById(12);
        assertEquals(expected, actual);
    }

    @Test
    public void edit() throws OnlineStoreLogicalException {
        View expected = viewDAO.getById(10);
        expected.setCode("PRD5555555555555");
        assertTrue(viewDAO.edit(expected));
        View actual = viewDAO.getById(10);
        assertEquals(expected, actual);

        //database reset
        expected.setCode("PRD1581866056338");
        assertTrue(viewDAO.edit(expected));
    }

    @Test
    public void remove() {
        Product product = productDAO.getById(3);
        View view = new View("PRD1111111111111", product);
        long id = viewDAO.add(view);
        assertTrue(viewDAO.remove(view));
    }
}
package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dto.View;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;
import org.astashonok.onlinestorebackend.testconfig.SimpleSingleConnection;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.astashonok.onlinestorebackend.testconfig.StaticTestInitializer.*;
import static org.junit.Assert.*;

public class ViewDAOImplTest {

    @After
    public void resetDatabase() throws SQLException {
        System.out.println("Reset database...");
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.addBatch("SET FOREIGN_KEY_CHECKS=0");
        statement.addBatch("TRUNCATE TABLE views");
        statement.addBatch("SET FOREIGN_KEY_CHECKS=1");
        statement.addBatch("INSERT INTO views(code, product_id) VALUES('PRD1581865664262', 1)");
        statement.addBatch("INSERT INTO views(code, product_id) VALUES('PRD1581865665263', 1)");
        statement.addBatch("INSERT INTO views(code, product_id) VALUES('PRD1581865666263', 1)");
        statement.addBatch("INSERT INTO views(code, product_id) VALUES('PRD1581865667263', 1)");
        statement.addBatch("INSERT INTO views(code, product_id) VALUES('PRD1581865668263', 1)");
        statement.addBatch("INSERT INTO views(code, product_id) VALUES('PRD1581866052337', 2)");
        statement.addBatch("INSERT INTO views(code, product_id) VALUES('PRD1581866053337', 2)");
        statement.addBatch("INSERT INTO views(code, product_id) VALUES('PRD1581866054337', 2)");
        statement.addBatch("INSERT INTO views(code, product_id) VALUES('PRD1581866055338', 2)");
        statement.addBatch("INSERT INTO views(code, product_id) VALUES('PRD1581866056338', 2)");
        statement.addBatch("INSERT INTO views(code, product_id) VALUES('PRD1581866965493', 3)");
        statement.addBatch("INSERT INTO views(code, product_id) VALUES('PRD1581866966495', 3)");
        statement.executeBatch();
        connection.commit();
        System.out.println("Resetting is successfully!");
    }
    @Test
    public void getByProduct() throws BackendException {
        int expected = 5;
        int actual = viewDAO.getByProduct(product1).size();
        assertEquals(expected, actual);
    }

    @Test
    public void add() throws SQLException, BackendException {
        View expected = new View("PRD1111111111111", product3);
        long id = viewDAO.add(expected);
        View actual = viewDAO.getById(id);
        assertEquals(expected, actual);
    }

    @Test
    public void getById() throws BackendException {
        View expected = view32;
        View actual = viewDAO.getById(12);
        assertEquals(expected, actual);
    }

    @Test
    public void edit() throws BackendException {
        View expected = view25;
        expected.setCode("PRD5555555555555");
        viewDAO.edit(expected);
        View actual = viewDAO.getById(10);
        assertEquals(expected, actual);
        expected.setCode("PRD1581866056338");
    }

    @Test
    public void remove() throws BackendException {
        View expected = new View("PRD1111111111111", product3);
        long id = viewDAO.add(expected);
        View actual = viewDAO.getById(id);
        assertEquals(expected, actual);
    }
}
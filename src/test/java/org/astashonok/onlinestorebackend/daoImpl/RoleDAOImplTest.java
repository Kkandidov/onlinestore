package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dto.Role;
import org.astashonok.onlinestorebackend.exceptions.basicexception.OnlineStoreLogicalException;
import org.astashonok.onlinestorebackend.testconfig.SimpleSingleConnection;
import org.astashonok.onlinestorebackend.util.pool.Pools;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.astashonok.onlinestorebackend.testconfig.StaticInitializerDTO.role1;
import static org.astashonok.onlinestorebackend.testconfig.StaticInitializerDTO.role2;
import static org.junit.Assert.*;

public class RoleDAOImplTest {

    private static RoleDAOImpl roleDAO;

    @BeforeClass
    public static void init() {
        System.out.println("Initialization of our object! ");
        roleDAO = new RoleDAOImpl(Pools.newPool(SimpleSingleConnection.class));
    }

    @AfterClass
    public static void destroy() {
        System.out.println("Destruction of our object! ");
        roleDAO = null;
    }

    @Test
    public void getAll() {
        int expected = 2;
        int actual = roleDAO.getAll().size();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllActive() {
        int expected = 2;
        int actual = roleDAO.getAllActive().size();
        assertEquals(expected, actual);
        Role role = roleDAO.getById(2);
        role.setActive(false);
        assertTrue(roleDAO.edit(role));
        expected = 1;
        actual = roleDAO.getAllActive().size();
        assertEquals(expected, actual);
        assertFalse(roleDAO.getById(2).isActive());

        //database reset
        role.setActive(true);
        roleDAO.edit(role);
    }

    @Test
    public void getByName() {
        Role expected = role1;
        Role actual = roleDAO.getByName("ADMIN");
        assertEquals(expected, actual);
    }

    @Test
    public void add() throws SQLException {
        Role expected = new Role("CUSTOMER", true);
        long id = roleDAO.add(expected);
        Role actual = roleDAO.getById(id);
        assertEquals(expected, actual);

        //database reset
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        String sql = "DELETE FROM roles WHERE id = " + id;
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Test
    public void getById() {
        Role expected = role2;
        Role actual = roleDAO.getById(2);
        assertEquals(expected, actual);
    }

    @Test
    public void edit() throws OnlineStoreLogicalException {
        Role expected = roleDAO.getById(2);
        expected.setName("CUSTOMER");
        assertTrue(roleDAO.edit(expected));
        Role actual = roleDAO.getById(2);
        assertEquals(expected, actual);

        //database reset
        expected.setName("USER");
        assertTrue(roleDAO.edit(expected));
    }

//    @Test
//    public void remove() {
//        Role role = roleDAO.getById(1);
//        assertTrue(roleDAO.remove(role));
//        assertFalse(roleDAO.getById(1).isActive());
//
//        //database reset
//        role.setActive(true);
//        roleDAO.edit(role);
//    }
}
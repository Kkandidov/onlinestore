package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dto.*;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;
import org.astashonok.onlinestorebackend.testconfig.SimpleSingleConnection;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.astashonok.onlinestorebackend.testconfig.StaticTestInitializer.*;
import static org.junit.Assert.*;

public class UserDAOImplTest {

    @After
    public void resetDatabase() throws SQLException {
        System.out.println("Reset database...");
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.addBatch("SET FOREIGN_KEY_CHECKS=0");
        statement.addBatch("TRUNCATE TABLE users");
        statement.addBatch("SET FOREIGN_KEY_CHECKS=1");
        statement.addBatch("INSERT INTO users(first_name, last_name, email, password, contact_number, enabled, role_id) "
                + "VALUES('Ivan', 'Ivanov', 'ivan@gmail.com', '$2y$12$i5iA/3OVxdeVLB4h5ttOSecMkd1E0Vj9ywhjL449OuemuD09buJvS'"
                + ", '+375296543218', true, 1)");
        statement.addBatch("INSERT INTO users(first_name, last_name, email, password, contact_number, enabled, role_id) "
                + "VALUES('Petr', 'Petrov', 'petr@gmail.com', '$2y$12$r4EhYmgRbDrbmfAMvE1usO/fY8yv1Z.Hp6D9OSIcYelIfjYxUj3e.'"
                + ", '375296543384', true, 2)");
        statement.addBatch("INSERT INTO users(first_name, last_name, email, password, contact_number, enabled, role_id) "
                + "VALUES('Sergey', 'Sergeev', 'sergey@gmail.com', '$2y$12$ZSE/h0gS.Mg.qZnuwzfCxuBd3D1qH3KeY4wL9qZEcAt0FQYNRrBIO'"
                + ", '+375-29-654-32-45', true, 2)");
        statement.addBatch("DELETE FROM carts WHERE id = 4");
        statement.executeBatch();
        connection.commit();
        System.out.println("Resetting is successfully!");
    }

    @Test
    public void getByEmail() throws BackendException {
        User expected = user3;
        User actual = userDAO.getByEmail("sergey@gmail.com");
        assertEquals(expected, actual);
    }

    @Test
    public void getAll() throws BackendException {
        int expected = 3;
        int actual = userDAO.getAll().size();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllEnable() throws BackendException {
        User user = user3;
        user.setEnabled(false);
        userDAO.edit(user);
        int expected = 2;
        int actual = userDAO.getAllEnable().size();
        assertEquals(expected, actual);
        user.setEnabled(true);
    }

    @Test
    public void getByRole() throws BackendException {
        int expected = 2;
        int actual = userDAO.getByRole(role2).size();
        assertEquals(expected, actual);
    }

    @Test
    public void add() throws SQLException, BackendException {
        User expected = new User("Konstantin", "Konstantinov", "kost@gmail.com",
                "$2y$12$r4EhYmgRbDrbmfAMvE1usO/fY8yv1Z.Hp6D9OSIcYelIfjYxUj3e.",
                "+375293456789", true, role2);
        long id = userDAO.add(expected);
        User actual = userDAO.getById(id);
        assertEquals(expected, actual);
    }

    @Test
    public void getById() throws BackendException {
        User expected = user3;
        User actual = userDAO.getById(3);
        assertEquals(expected, actual);
    }

    @Test
    public void edit() throws BackendException {
        User expected = user2;
        expected.setLastName("Fedorov");
        userDAO.edit(expected);
        User actual = userDAO.getById(2);
        assertEquals(expected, actual);
        expected.setLastName("Petrov");
    }

    @Test
    public void remove() throws BackendException {
        userDAO.remove(user1);
        assertNull(userDAO.getById(1));
    }
}
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
        statement.addBatch("TRUNCATE TABLE carts");
        statement.addBatch("TRUNCATE TABLE cart_items");
        statement.addBatch("TRUNCATE TABLE addresses");
        statement.addBatch("TRUNCATE TABLE orders");
        statement.addBatch("TRUNCATE TABLE order_items");
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
        statement.addBatch("INSERT INTO carts(id, total, cart_items) VALUES(2, 1510, 2)");
        statement.addBatch("INSERT INTO carts(id, total, cart_items) VALUES(3, 1029, 1)");
        statement.addBatch("DELETE FROM carts WHERE id = 4");
        statement.addBatch("INSERT INTO cart_items(cart_id, total, product_id, product_count, product_price, available)"
                + "VALUES(2, 800, 1, 2, 400, true)");
        statement.addBatch("INSERT INTO cart_items(cart_id, total, product_id, product_count, product_price, available)"
                + "VALUES(2, 710, 2, 1, 710, true)");
        statement.addBatch("INSERT INTO cart_items(cart_id, total, product_id, product_count, product_price, available)"
                + "VALUES(3, 1029, 3, 3, 343, true)");
        statement.addBatch("INSERT INTO addresses(user_id, line_one, line_two, city, state, country, postal_code, "
                + "billing, shipping) VALUES(2, 'Platonov street', '', 'Minsk', 'Minsk', 'Belarus', '220034', false, true)");
        statement.addBatch("INSERT INTO addresses(user_id, line_one, line_two, city, state, country, postal_code, "
                + "billing, shipping) VALUES(3, 'Serdich street', '', 'Minsk', 'Minsk', 'Belarus', '220035', false, true)");
        statement.addBatch("INSERT INTO addresses(user_id, line_one, line_two, city, state, country, postal_code, "
                + "billing, shipping) VALUES(2, 'Platonov street', '', 'Minsk', 'Minsk', 'Belarus', '220034', true, false)");
        statement.addBatch("INSERT INTO addresses(user_id, line_one, line_two, city, state, country, postal_code, "
                + "billing, shipping) VALUES(3, 'Serdich street', '', 'Minsk', 'Minsk', 'Belarus', '220035', true, false)");
        statement.addBatch("INSERT INTO orders(user_id, total, count, shipping_id, billing_id, date)"
                + "VALUES(2, 1510, 3, 1, 3, '2020-03-03 10:37:22')");
        statement.addBatch("INSERT INTO orders(user_id, total, count, shipping_id, billing_id, date)"
                + "VALUES(3, 1029, 3, 2, 4, '2020-03-05 13:35:21')");
        statement.addBatch("INSERT INTO order_items(order_id, total, product_id, product_count, product_price)"
                + "VALUES(1, 800, 1, 2, 400)");
        statement.addBatch("INSERT INTO order_items(order_id, total, product_id, product_count, product_price)"
                + "VALUES(1, 710, 2, 1, 710)");
        statement.addBatch("INSERT INTO order_items(order_id, total, product_id, product_count, product_price)"
                + "VALUES(2, 1029, 3, 3, 343)");
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
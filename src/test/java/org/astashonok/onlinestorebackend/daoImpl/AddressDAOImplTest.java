package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dto.Address;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;
import org.astashonok.onlinestorebackend.testconfig.SimpleSingleConnection;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.astashonok.onlinestorebackend.testconfig.StaticTestInitializer.*;
import static org.junit.Assert.*;

public class AddressDAOImplTest {

    @After
    public void resetDatabase() throws SQLException {
        System.out.println("Reset database...");
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.addBatch("SET FOREIGN_KEY_CHECKS=0");
        statement.addBatch("TRUNCATE TABLE addresses");
        statement.addBatch("TRUNCATE TABLE orders");
        statement.addBatch("TRUNCATE TABLE order_items");
        statement.addBatch("SET FOREIGN_KEY_CHECKS=1");
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
    public void getBillingAddressByUser() throws BackendException {
        Address expected = address22;
        Address actual = addressDAO.getBillingAddressByUser(user3);
        assertEquals(expected, actual);
    }

    @Test
    public void getShippingAddressesByUser() throws BackendException{
        Address address = new Address(user3, "Platonov street", "", "Minsk", "Minsk"
                , "Belarus", "220034", false, true);
        addressDAO.add(address);
        int expected = 2;
        int actual = addressDAO.getShippingAddressesByUser(user3).size();
        assertEquals(expected, actual);
    }

    @Test
    public void add() throws BackendException {
        Address expected = new Address(user3, "Platonov street", "", "Minsk", "Minsk"
                , "Belarus", "220034", false, true);
        long id = addressDAO.add(expected);
        Address actual = addressDAO.getById(id);
        assertEquals(expected, actual);
    }

    @Test
    public void getById() throws BackendException {
        Address expected = address21;
        Address actual = addressDAO.getById(2);
        assertEquals(expected, actual);
    }

    @Test
    public void edit() throws BackendException {
        Address expected = address12;
        expected.setCountry("Russia");
        addressDAO.edit(expected);
        Address actual = addressDAO.getById(3);
        assertEquals(expected, actual);
        expected.setCountry("Belarus");
    }

    @Test
    public void remove() throws BackendException {
        addressDAO.remove(address11);
        assertNull(addressDAO.getById(1));
    }
}
package org.astashonok.onlinestorebackend.daoImpl;

import org.astashonok.onlinestorebackend.dto.Description;
import org.astashonok.onlinestorebackend.exceptions.basicexception.BackendException;
import org.astashonok.onlinestorebackend.testconfig.SimpleSingleConnection;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.astashonok.onlinestorebackend.testconfig.StaticTestInitializer.*;
import static org.junit.Assert.*;

public class DescriptionDAOImplTest {

    @After
    public void resetDatabase() throws SQLException {
        System.out.println("Reset database...");
        Connection connection = SimpleSingleConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.addBatch("SET FOREIGN_KEY_CHECKS=0");
        statement.addBatch("TRUNCATE TABLE descriptions");
        statement.addBatch("SET FOREIGN_KEY_CHECKS=1");
        statement.addBatch("INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, "
                + "battery, display_technology, graphics, wireless_communication)"
                + "VALUES(1, '6.26\" IPS (1080x2340)', 'ice white', 'HiSilicon Kirin 980', '32 MP', '48 MP', '6 GB RAM / "
                + "128 GB flash memory', 'Li-ion 3 750 mAh', 'IPS', '16 million', 'Bluetooth / Wifi / Nfc')");
        statement.addBatch("INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, "
                + "battery, display_technology, graphics, wireless_communication)"
                + "VALUES(2, '6.1\" AMOLED (1440x3040)', 'black', 'Exynos 9820', '10 MP', '12 MP', '8 GB RAM / 128 GB "
                + "flash memory', 'Li-ion 3 400 mAh', 'AMOLED', '16 million', 'Bluetooth / Wifi / Nfc')");
        statement.addBatch("INSERT INTO descriptions(id, screen, color, processor, front_camera, rear_camera, capacity, "
                + "battery, display_technology, graphics, wireless_communication)"
                + "VALUES(3, '6.39\" AMOLED (1080x2340)', 'blue', 'Qualcomm Snapdragon 730', '20 MP', '48 MP', '6 GB RAM "
                + "/ 64 GB flash memory', 'Li-pol 4,000 mAh', 'AMOLED', '16 million', 'Bluetooth / Wifi / Nfc')");
        statement.executeBatch();
        connection.commit();
        System.out.println("Resetting is successfully!");
    }

    @Test
    public void getByProduct() throws BackendException {
        Description expected = description1;
        Description actual = descriptionDAO.getByProduct(product1);
        assertEquals(expected, actual);
    }

    @Test
    public void getById() throws BackendException {
        Description expected = description1;
        Description actual = descriptionDAO.getById(1);
        assertEquals(expected, actual);
    }

    @Test
    public void edit() throws BackendException {
        Description expected = description1;
        expected.setProcessor("HiSilicon");
        System.out.println(expected.getId());
        descriptionDAO.edit(expected);
        Description actual = descriptionDAO.getById(1);
        assertEquals(expected, actual);
        expected.setProcessor("HiSilicon Kirin 980");
    }
}
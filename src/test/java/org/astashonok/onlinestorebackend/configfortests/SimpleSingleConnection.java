package org.astashonok.onlinestorebackend.configfortests;

import org.astashonok.onlinestorebackend.util.Pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleSingleConnection implements Pool {
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/test_online_store?serverTimezone=Europe/Minsk&useSSL=false";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root";

    static {
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver isn't found! ");
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        System.out.println("Connecting...");
        Connection connection;
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            System.out.println("Connection is successful!");
        return connection;
    }
}

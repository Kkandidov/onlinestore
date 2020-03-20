package org.astashonok.onlinestorebackend.util.pool.handpool.pool.abstracts;

import org.astashonok.onlinestorebackend.util.pool.Pool;
import org.astashonok.onlinestorebackend.util.pool.handpool.Cache;
import org.astashonok.onlinestorebackend.util.pool.handpool.DatabasePropertiesManager;
import org.astashonok.onlinestorebackend.util.pool.handpool.PoolConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.astashonok.onlinestorebackend.util.pool.handpool.DatabaseProperties.*;

public abstract class AbstractPool implements Pool {
    private Cache cache;
    private ArrayList<Connection> allConnections;
    private static final DatabasePropertiesManager manager = DatabasePropertiesManager.getInstance();
    protected static int CONNECT_COUNT;

    static {
        CONNECT_COUNT = Integer.parseInt(manager.getProperty(CONNECTION_COUNT));
        try {
            Class.forName(manager.getProperty(DRIVER));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    {
        cache = new Cache();
        allConnections = new ArrayList<>();
    }

    protected PoolConnection takeConnection(AbstractPool pool) {
        System.out.println("Connecting...");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(manager.getProperty(URL), manager.getProperty(USER_NAME)
                    , manager.getProperty(PASSWORD));
        } catch (SQLException e) {
            System.out.println("Connection is failed!");
            e.printStackTrace();
        }
        System.out.println("Connection is successful!");
        allConnections.add(connection);
        return new PoolConnection(connection, cache, pool);
    }

    public abstract Connection getConnection() throws SQLException;

    public abstract boolean close(Connection connection) throws InterruptedException;

    public void shutDownNow() {
        System.out.println("Connections deleting...");
        try {
            Connection connection;
            for (int i = 0; i < allConnections.size(); i++) {
                connection = allConnections.get(i);
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("Connection is closed!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int size(){
        return allConnections.size();
    }
}

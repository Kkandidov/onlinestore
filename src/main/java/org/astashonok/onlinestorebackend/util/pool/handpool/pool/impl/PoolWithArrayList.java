package org.astashonok.onlinestorebackend.util.pool.handpool.pool.impl;

import org.astashonok.onlinestorebackend.util.pool.handpool.pool.abstracts.AbstractPool;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class PoolWithArrayList extends AbstractPool {
    private final List<Connection> availableConnections;
    private final List<Connection> usingConnections;

    public PoolWithArrayList(){
        availableConnections = new ArrayList<>();
        usingConnections = new ArrayList<>();

        for (int i = 0; i < CONNECT_COUNT; i++) {
            availableConnections.add(takeConnection(this));
        }
    }

    @Override
    public synchronized Connection getConnection() {
        Connection connection;
        if (availableConnections.size() != 0) {
            connection = availableConnections.remove(0);
            usingConnections.add(connection);
        } else {
            connection = null;
        }
        return connection;
    }

    @Override
    public synchronized boolean close(Connection connection) {
        if (connection == null) {
            throw new NullPointerException();
        }

        if (usingConnections.remove(connection)) {
            return availableConnections.add(connection);
        }

        return false;
    }
}

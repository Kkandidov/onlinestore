package org.astashonok.onlinestorebackend.util.pool.handpool.pool.impl;

import org.astashonok.onlinestorebackend.util.pool.handpool.pool.abstracts.AbstractPool;

import java.sql.Connection;

public class PoolWithArray extends AbstractPool {
    private final Object lock;
    private final Connection[] availableConnections;
    private final Connection[] usingConnections;
    private int pointer;

    public PoolWithArray() {
        this.availableConnections = new Connection[CONNECT_COUNT];
        this.usingConnections = new Connection[CONNECT_COUNT];
        this.lock = new Object();

        for (int i = 0; i < CONNECT_COUNT; i++) {
            availableConnections[i] = takeConnection(this);
        }
    }

    @Override
    public Connection getConnection() {
        synchronized (lock) {
            Connection connection;
            if (pointer < CONNECT_COUNT) {
                connection = availableConnections[pointer];
                availableConnections[pointer++] = null;
                if (add(connection)){
                    return connection;
                }
            }
            return null;
        }
    }

    @Override
    public boolean close(Connection connection) {
        synchronized (lock) {
            if (connection == null) {
                throw new NullPointerException();
            }

            if (remove(connection)){
                availableConnections[--pointer] = connection;
                return true;
            }
            return false;
        }
    }

    private boolean remove(Connection connection) {
        for (int i = 0; i < pointer; i++) {
            if (usingConnections[i] == connection) {
                usingConnections[i] = null;
                return true;
            }
        }
        return false;
    }

    private boolean add(Connection connection) {
        for (int i = 0; i < pointer; i++) {
            if (usingConnections[i] == null) {
                usingConnections[i] = connection;
                return true;
            }
        }
        return false;
    }
}

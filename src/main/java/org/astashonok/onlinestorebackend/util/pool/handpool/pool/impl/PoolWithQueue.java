package org.astashonok.onlinestorebackend.util.pool.handpool.pool.impl;

import org.astashonok.onlinestorebackend.util.pool.handpool.pool.abstracts.AbstractPool;

import java.sql.Connection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class PoolWithQueue extends AbstractPool {
    private BlockingQueue<Connection> availableConnections;
    private BlockingQueue<Connection> usingConnections;

    public PoolWithQueue() {
        this.availableConnections = new ArrayBlockingQueue<>(CONNECT_COUNT);
        this.usingConnections = new ArrayBlockingQueue<>(CONNECT_COUNT);

        for (int i = 0; i < CONNECT_COUNT; i++) {
            availableConnections.add(takeConnection(this));
        }
    }

    @Override
    public Connection getConnection() {
        try {
            Connection connection = availableConnections.poll(2, TimeUnit.SECONDS);
            if (connection != null && usingConnections.offer(connection, 2, TimeUnit.SECONDS)) {
                return connection;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean close(Connection connection) {
        try {
            if (usingConnections.remove(connection) && availableConnections.offer(connection, 2, TimeUnit.SECONDS)){
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}

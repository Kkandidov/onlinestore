package org.astashonok.onlinestorebackend.util.pool.handpool.pool.impl;

import org.astashonok.onlinestorebackend.util.pool.handpool.pool.abstracts.AbstractPool;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PoolWithLock extends AbstractPool {
    private final Lock lock = new ReentrantLock(true);
    private final Condition put = lock.newCondition();
    private final Condition extract = lock.newCondition();
    private final List<Connection> availableConnections;
    private final List<Connection> usingConnections;


    public PoolWithLock() {
        availableConnections = new ArrayList<>();
        usingConnections = new ArrayList<>();

        for (int i = 0; i < CONNECT_COUNT; i++) {
            availableConnections.add(takeConnection(this));
        }
    }

    @Override
    public Connection getConnection() {
        lock.lock();
        try {
            while (availableConnections.size() == 0) {
                extract.await();
            }
            Connection connection = availableConnections.remove(0);
            usingConnections.add(connection);
            put.signal();
            return connection;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public boolean close(Connection connection) {
        if (lock.tryLock()) {
            try {
                while (availableConnections.size() == CONNECT_COUNT) {
                    put.await();
                }

                if (usingConnections.remove(connection)) {
                    availableConnections.add(connection);
                    extract.signal();
                    return true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        return false;
    }
}

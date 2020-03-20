package org.astashonok.onlinestorebackend.util.pool.handpool;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cache {
    private final Map<String, PreparedStatement> map;
    private final ReadWriteLock rw = new ReentrantReadWriteLock();
    private final Lock readLock = rw.readLock();
    private final Lock writeLock = rw.readLock();
    private final int entryCount;

    public Cache(int entryCount) {
        this.map = new LinkedHashMap<>();
        this.entryCount = entryCount;
    }

    public Cache() {
        this(1000); // default entries count
    }

    public int size() {
        readLock.lock();
        try {
            return map.size();
        } finally {
            readLock.unlock();
        }
    }

    public boolean isEmpty() {
        readLock.lock();
        try {
            return map.isEmpty();
        } finally {
            readLock.unlock();
        }
    }

    public boolean containsKey(String key) {
        readLock.lock();
        try {
            return map.containsKey(key);
        } finally {
            readLock.unlock();
        }
    }

    public boolean containsValue(PreparedStatement value) {
        readLock.lock();
        try {
            return map.containsValue(value);
        } finally {
            readLock.unlock();
        }
    }

    public PreparedStatement get(String key) {
        readLock.lock();
        try {
            return map.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public PreparedStatement put(String key, PreparedStatement value) throws SQLException {
        writeLock.lock();
        try {
            if (entryCount <= map.size() && removeEldestEntry()) {
                return map.put(key, value);
            }
            return null;
        } finally {
            writeLock.unlock();
        }
    }

    public PreparedStatement remove(String key) {
        writeLock.lock();
        try {
            return map.remove(key);
        } finally {
            writeLock.unlock();
        }
    }


    private boolean removeEldestEntry() throws SQLException {
        Iterator<Map.Entry<String, PreparedStatement>> iterator = map.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<String, PreparedStatement> eldestEntry = iterator.next();
            PreparedStatement statement = map.remove(eldestEntry.getKey());
            if (statement != null) {
                statement.close();
                return true;
            }
        }
        return false;
    }
}

package org.astashonok.onlinestorebackend.util.pool;

import java.sql.Connection;
import java.sql.SQLException;

public interface Pool {
    Connection getConnection() throws SQLException;
}

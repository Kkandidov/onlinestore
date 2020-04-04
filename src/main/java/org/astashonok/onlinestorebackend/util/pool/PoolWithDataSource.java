package org.astashonok.onlinestorebackend.util.pool;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class PoolWithDataSource implements Pool {
    private DataSource dataSource;

    private PoolWithDataSource(){
        try{
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/DataSource");
        } catch (NamingException e){
            e.printStackTrace();
        }
    }

    private static class PoolWithDataSourceHandler{
        private final static PoolWithDataSource instance = new PoolWithDataSource();
    }

    public static PoolWithDataSource getInstance(){
        return PoolWithDataSourceHandler.instance;
    }

    public Connection getConnection() throws SQLException{
        return dataSource.getConnection();
    }
}

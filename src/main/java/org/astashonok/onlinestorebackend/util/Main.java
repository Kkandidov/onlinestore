package org.astashonok.onlinestorebackend.util;

import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
//        Pool pool = Pools.newPool(PoolWithDataSource.class);
//        pool.getConnection();
        try(T t = new T()){
            System.out.println("dfsfdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    static void method() throws RuntimeException {
//        try{
//            throw new SQLException();
//        } catch (SQLException | RuntimeException e) {
//            if (e instanceof SQLException) {
//                throw new RuntimeException(e);
//            }
////            if (e.getClass() == SQLException.class) {
////                throw new RuntimeException(e);
////            }
//            throw e;
////        }
//    }

    static class T implements Closeable{

        @Override
        public void close() throws IOException {
            throw new RuntimeException();
        }
    }

}

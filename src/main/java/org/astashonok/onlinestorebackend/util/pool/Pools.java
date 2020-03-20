package org.astashonok.onlinestorebackend.util.pool;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Pools {
    public static Pool newPool(Class<? extends Pool> pool) {
        for (Method method : pool.getMethods()){
            if(method.getName().equals("getInstance")){
                try {
                    return (Pool) method.invoke(pool);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}

package org.astashonok.onlinestorebackend.util.pool.handpool;

import java.util.ResourceBundle;

public final class DatabasePropertiesManager {
    private static final ResourceBundle resource = ResourceBundle.getBundle("database");

    private DatabasePropertiesManager(){}

    private static class DatabasePropertiesHandler{
        private final static DatabasePropertiesManager instance = new DatabasePropertiesManager();
    }

    public static DatabasePropertiesManager getInstance(){
        return DatabasePropertiesHandler.instance;
    }

    public String getProperty(DatabaseProperties property){
        String result = null;
        switch (property){
            case DRIVER:
                result = resource.getString("driver");
                break;
            case URL:
                result = resource.getString("url");
                break;
            case USER_NAME:
                result = resource.getString("username");
                break;
            case PASSWORD:
                result = resource.getString("password");
                break;
            case CONNECTION_COUNT:
                result = resource.getString("connectionCount");
                break;
        }
        return result;
    }
}
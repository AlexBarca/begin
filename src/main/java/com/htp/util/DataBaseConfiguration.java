package com.htp.util;

import java.util.ResourceBundle;

public class DataBaseConfiguration {
    private static DataBaseConfiguration instance;
    private ResourceBundle resourceBundle;

    private static final String BUNDLE_NAME="database";
    private static final String DATABASE_DRIVER_NAME="driverName";
    private static final String DATABASE_URL="url";
    private static final String DATABASE_LOGIN="login";
    private static final String BUNDLE_PASSWORD="password";
    private static final String BUNDLE_POOL_SIZE="initialSize";

    public static DataBaseConfiguration getInstance() {
        if (instance==null) {
            instance = new DataBaseConfiguration();
            instance.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
            }
        return instance;
    }
    public String getProperty (String key) {
        return (String)
        resourceBundle.getObject(key);
    }


}

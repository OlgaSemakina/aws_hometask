package com.amazon.aws;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestProperties {
    private String propertiesFile;
    private Properties currentProperties = new Properties();

    public TestProperties() {
        this.propertiesFile = propertiesFile;
    }

    private Properties getCurrentProperties() throws IOException {
        InputStream in = getClass().getResourceAsStream("/test.properties");
        currentProperties.load(in);
        in.close();
        return currentProperties;
    }

    public String getProperty(String propertyKey) throws IOException {
        if (!currentProperties.containsKey(propertyKey)) {
            currentProperties = getCurrentProperties();
        }
        return currentProperties.getProperty(propertyKey, null);
    }
}

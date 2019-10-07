package example.govno3.utils;

import example.govno3.Govno3Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperty {

    private static final String APP_PROPERTY_FILE_NAME = "/app.properties";

    private static final Logger LOGGER = LoggerFactory.getLogger(AppProperty.class);
    private static Properties PROPERTIES;

    static {
        try (InputStream is = Govno3Application.class.getResourceAsStream(APP_PROPERTY_FILE_NAME);) {
            PROPERTIES = new Properties();
            PROPERTIES.load(is);
        } catch (IOException e) {
            LOGGER.error("Cannot find property file", e);
        }
    }

    private AppProperty() {
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key, key);
    }
}

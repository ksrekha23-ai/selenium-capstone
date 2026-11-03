// ABOUTME: Loads test configuration from config.properties file.
// ABOUTME: Provides static access to all configuration values used across the framework.
package com.saucedemo.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found on classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getBrowser() {
        return properties.getProperty("browser", "chrome");
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    public static String getStandardUsername() {
        return properties.getProperty("standard.username");
    }

    public static String getStandardPassword() {
        return properties.getProperty("standard.password");
    }

    public static String getLockedUsername() {
        return properties.getProperty("locked.username");
    }

    public static int getExplicitWaitSeconds() {
        return Integer.parseInt(properties.getProperty("explicit.wait.seconds", "10"));
    }

    public static int getPageLoadTimeoutSeconds() {
        return Integer.parseInt(properties.getProperty("page.load.timeout.seconds", "15"));
    }
}

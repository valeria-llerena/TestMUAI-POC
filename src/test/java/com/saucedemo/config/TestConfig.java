package com.saucedemo.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class TestConfig {

    private static final Properties PROPERTIES = load();

    private TestConfig() {
    }

    public static String baseUrl() {
        return System.getProperty("base.url", PROPERTIES.getProperty("base.url"));
    }

    public static int explicitWaitSeconds() {
        return Integer.parseInt(
                System.getProperty("explicit.wait.seconds", PROPERTIES.getProperty("explicit.wait.seconds", "20")));
    }

    public static boolean headless() {
        String fromProperty = System.getProperty("headless");
        if (fromProperty != null && !fromProperty.isBlank()) {
            return Boolean.parseBoolean(fromProperty);
        }
        return Boolean.parseBoolean(System.getenv().getOrDefault("HEADLESS", "false"));
    }

    private static Properties load() {
        Properties properties = new Properties();
        try (InputStream input = TestConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("No se pudo leer config.properties", exception);
        }
        return properties;
    }
}

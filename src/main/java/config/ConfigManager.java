package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final Properties properties = new Properties();
    private static boolean loaded = false;

    public static void loadConfig() {
        if (loaded) return;

        try (InputStream input = ConfigManager.class.getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                System.err.println("config.properties not found in resources.");
                return;
            }

            properties.load(input);
            loaded = true;
            System.out.println("Configuration loaded successfully.");

        } catch (IOException e) {
            System.err.println("Error loading config: " + e.getMessage());
        }
    }

    public static String getYelpApiKey() {
        return getProperty("yelp.api.key");
    }

    public static String getGoogleApiKey() {
        return getProperty("google.api.key");
    }

    public static String getSpoonacularApiKey() {
        return getProperty("spoonacular.api.key");
    }

    public static String getSpoonacularHost() {
        return getProperty("spoonacular.host");
    }

    private static String getProperty(String keyName) {
        String value = properties.getProperty(keyName);
        if (value == null || value.isEmpty()) {
            System.err.println("Warning: Key '" + keyName + "' is missing or invalid.");
        }
        return value;
    }
}
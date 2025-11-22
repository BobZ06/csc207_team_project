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
        String key = properties.getProperty("yelp.api.key");
        if (key == null || key.isEmpty()) {
            System.err.println("The key is invalid.");
        }
        return key;
    }

    public static String getGoogleMapsHost() {
        return properties.getProperty("googlemaps.host", "google-maps-api-free.p.rapidapi.com");
    }
}
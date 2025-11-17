package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration manager for API keys and settings.
 * Loads configuration from config.properties file in resources folder.
 */
public class ConfigManager {

    private static Properties properties = new Properties();
    private static boolean loaded = false;

    /**
     * Load configuration from config.properties file.
     * Call this once at application startup.
     */
    public static void loadConfig() {
        if (loaded) {
            return;
        }

        try (InputStream input = ConfigManager.class.getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                System.err.println("config.properties not found");
                System.err.println("Please copy config.properties.template to config.properties");
                return;
            }

            properties.load(input);
            loaded = true;
            System.out.println("Configuration loaded successfully");

        } catch (IOException e) {
            System.err.println("Error loading config.properties: " + e.getMessage());
        }
    }

    /**
     * Get RapidAPI key.
     * @return API key or null if not configured
     */
    public static String getRapidApiKey() {
        return properties.getProperty("rapidapi.key");
    }

    /**
     * Get Google Maps API host.
     * @return API host URL
     */
    public static String getGoogleMapsHost() {
        return properties.getProperty("googlemaps.host",
                "google-maps-api-free.p.rapidapi.com");
    }

    /**
     * Get Yelp API host.
     * @return API host URL
     */
    public static String getYelpHost() {
        return properties.getProperty("yelp.host",
                "YelpAPIserg-osipchukV1.p.rapidapi.com");
    }

    /**
     * Get Documenu API host.
     * @return API host URL
     */
    public static String getDocumenuHost() {
        return properties.getProperty("documenu.host",
                "documenu.p.rapidapi.com");
    }

    /**
     * Check if configuration is properly loaded.
     * @return true if API key is configured
     */
    public static boolean isConfigured() {
        String key = getRapidApiKey();
        return key != null && !key.isEmpty();
    }
}

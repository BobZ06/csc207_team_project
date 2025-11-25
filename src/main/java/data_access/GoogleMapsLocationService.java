package data_access;

import config.ConfigManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * LocationService implementation using the Google Maps Geocoding API.
 * Documentation: https://developers.google.com/maps/documentation/geocoding/overview
 */
public class GoogleMapsLocationService implements LocationService {
    private final OkHttpClient client;
    private final String apiKey;
    private static final String API_HOST = "maps.googleapis.com";

    public GoogleMapsLocationService() {
        this.client = new OkHttpClient();
        ConfigManager.loadConfig();

        // Retrieve the Google API key from ConfigManager
        this.apiKey = ConfigManager.getGoogleApiKey();

        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("Google API key not configured.");
        }
    }

    @Override
    public List<Float> geocodeAddress(String address) throws LocationNotFoundException {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new LocationNotFoundException("API key not configured.");
        }

        try {
            String encodedAddress = java.net.URLEncoder.encode(address, "UTF-8");

            // Format: https://maps.googleapis.com/maps/api/geocode/json?address=...&key=...
            String url = String.format(
                    "https://%s/maps/api/geocode/json?address=%s&key=%s",
                    API_HOST,
                    encodedAddress,
                    apiKey
            );

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new LocationNotFoundException("API returned status: " + response.code());
            }

            String responseBody = response.body().string();
            JSONObject jsonObject = new JSONObject(responseBody);

            String status = jsonObject.getString("status");

            if ("OK".equals(status)) {
                JSONArray results = jsonObject.getJSONArray("results");

                if (results.length() > 0) {
                    JSONObject location = results.getJSONObject(0)
                            .getJSONObject("geometry")
                            .getJSONObject("location");

                    List<Float> coordinates = new ArrayList<>();
                    coordinates.add(location.getFloat("lat"));
                    coordinates.add(location.getFloat("lng"));
                    return coordinates;
                }
            } else if ("ZERO_RESULTS".equals(status)) {
                throw new LocationNotFoundException("Address not found: " + address);
            } else {
                throw new LocationNotFoundException("Google API Error: " + status);
            }

            throw new LocationNotFoundException("Unknown error for address: " + address);

        } catch (IOException e) {
            throw new LocationNotFoundException("Network error: " + e.getMessage());
        }
    }

    @Override
    public float calculateDistance(float originLat, float originLng, float destLat, float destLng) {
        // Use Haversine formula locally to save API calls
        // This calculates the straight-line distance in meters
        final double R = 6371000; // Earth's radius in meters

        double lat1Rad = Math.toRadians(originLat);
        double lat2Rad = Math.toRadians(destLat);
        double deltaLat = Math.toRadians(destLat - originLat);
        double deltaLng = Math.toRadians(destLng - originLng);

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(deltaLng / 2) * Math.sin(deltaLng / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (float) (R * c);
    }
}
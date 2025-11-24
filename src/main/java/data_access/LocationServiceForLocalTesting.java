package data_access;

import java.util.List;

/**
 * An implementation of LocationService for testing purposes.
 * Returns hardcoded coordinates for known cities to avoid API usage during tests.
 */
public class LocationServiceForLocalTesting implements LocationService {
    private int callCount = 0;

    @Override
    public List<Float> geocodeAddress(String address) throws LocationNotFoundException {
        callCount++;

        String lowerAddress = address.toLowerCase();

        if (lowerAddress.contains("toronto") || lowerAddress.contains("king's college")) {
            return List.of(43.6629f, -79.3957f); // U of T coordinates
        }

        if (lowerAddress.contains("new york") || lowerAddress.contains("nyc")) {
            return List.of(40.7128f, -74.0060f);
        }
        
        throw new LocationNotFoundException("Mock Address not found: " + address);
    }

    @Override
    public float calculateDistance(float originLat, float originLng, float destLat, float destLng) {
        callCount++;
        // Haversine formula
        final double R = 6371000;
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

    public int getCallCount() { return callCount; }
    public void resetCallCount() { this.callCount = 0; }
}
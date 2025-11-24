package data_access;

import java.util.List;

/**
 * Interface for location-related services including geocoding and distance calculation.
 *
 * Implementations of this interface should handle:
 * - Converting addresses to latitude/longitude coordinates (geocoding)
 * - Calculating distances between two geographic points
 *
 * Multiple implementations are expected:
 * - LocationServiceForLocalTesting: Returns mock data for testing
 * - GoogleMapsLocationService: Real API implementation using Google Maps
 * - CachingLocationService: Decorator that caches results
 *
 * @see LocationServiceForLocalTesting
 * @see GoogleMapsLocationService
 * @see CachingLocationService
 */
public interface LocationService {

    /**
     * Convert an address string to geographic coordinates.
     *
     * @param address the address to geocode (e.g., "27 King's College Circle, Toronto, ON")
     * @return a list containing exactly two elements: [latitude, longitude]
     *         For example: [43.6629, -79.3957] for University of Toronto
     * @throws LocationNotFoundException if the address cannot be found or geocoded
     */
    List<Float> geocodeAddress(String address) throws LocationNotFoundException;

    /**
     * Calculate the distance between two geographic points.
     *
     * @param originLat the latitude of the origin point
     * @param originLng the longitude of the origin point
     * @param destLat the latitude of the destination point
     * @param destLng the longitude of the destination point
     * @return the distance in meters between the two points
     * @throws LocationNotFoundException if the distance cannot be calculated
     */
    float calculateDistance(float originLat, float originLng, float destLat, float destLng)
            throws LocationNotFoundException;

    /**
     * Exception thrown when location-related operations fail.
     */
    class LocationNotFoundException extends Exception {
        public LocationNotFoundException(String message) {
            super("Location not found: " + message);
        }
    }
}

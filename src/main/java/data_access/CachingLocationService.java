package data_access;

import java.util.*;

/**
 * This LocationService caches geocoding results to improve performance.
 */
public class CachingLocationService implements LocationService {
    private int callsMade = 0;
    private final LocationService service;
    private final Map<String, List<Float>> geocodeCache;

    /**
     * Create a caching wrapper around any LocationService implementation.
     * @param service the underlying service to cache (can be real API or test)
     */
    public CachingLocationService(LocationService service) {
        this.service = service;
        this.geocodeCache = new HashMap<>();
    }

    @Override
    public List<Float> geocodeAddress(String address) throws LocationNotFoundException {
        String cacheKey = address.toLowerCase().trim();

        if (geocodeCache.containsKey(cacheKey)) {
            return new ArrayList<>(geocodeCache.get(cacheKey));
        }

        callsMade++;
        List<Float> coordinates = service.geocodeAddress(address);

        geocodeCache.put(cacheKey, new ArrayList<>(coordinates));

        return coordinates;
    }

    @Override
    public float calculateDistance(float originLat, float originLng, float destLat, float destLng)
            throws LocationNotFoundException {
        callsMade++;
        return service.calculateDistance(originLat, originLng, destLat, destLng);
    }

    public int getCallsMade() {
        return callsMade;
    }

    public void clearCache() {
        geocodeCache.clear();
    }

}

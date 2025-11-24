package data_access;

import entity.Restaurant;
import java.util.*;

/**
 * Caching wrapper to improve performance and reduce API usage.
 */
public class CachingRestaurantSearchService implements RestaurantSearchService {
    private int callsMade = 0;
    private final RestaurantSearchService service;

    // Caches
    private final Map<String, List<Restaurant>> searchCache = new HashMap<>();
    private final Map<String, Restaurant> detailsCache = new HashMap<>();
    private final Map<String, List<String>> reviewsCache = new HashMap<>();

    public CachingRestaurantSearchService(RestaurantSearchService service) {
        this.service = service;
    }

    @Override
    public List<Restaurant> searchRestaurants(float latitude, float longitude, String term, int limit)
            throws RestaurantSearchException {
        String cacheKey = String.format("%.4f_%.4f_%s_%d", latitude, longitude, term.toLowerCase(), limit);

        if (searchCache.containsKey(cacheKey)) {
            return new ArrayList<>(searchCache.get(cacheKey));
        }

        callsMade++;
        List<Restaurant> restaurants = service.searchRestaurants(latitude, longitude, term, limit);
        searchCache.put(cacheKey, new ArrayList<>(restaurants));
        return restaurants;
    }

    @Override
    public Restaurant getRestaurantDetails(String restaurantId) throws RestaurantSearchException {
        if (detailsCache.containsKey(restaurantId)) {
            return detailsCache.get(restaurantId);
        }

        callsMade++;
        Restaurant restaurant = service.getRestaurantDetails(restaurantId);
        detailsCache.put(restaurantId, restaurant);
        return restaurant;
    }

    /**
     * Implementation for User Story 4 with Caching.
     */
    @Override
    public List<String> getRestaurantReviews(String restaurantId) throws RestaurantSearchException {
        // Check cache
        if (reviewsCache.containsKey(restaurantId)) {
            return new ArrayList<>(reviewsCache.get(restaurantId));
        }

        // Cache miss - call API
        callsMade++;
        List<String> reviews = service.getRestaurantReviews(restaurantId);

        // Store in cache
        reviewsCache.put(restaurantId, new ArrayList<>(reviews));
        return reviews;
    }

    public int getCallsMade() { return callsMade; }

    public void clearCache() {
        searchCache.clear();
        detailsCache.clear();
        reviewsCache.clear();
    }
}
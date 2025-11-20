package data_access;

import entity.Restaurant;
import java.util.ArrayList;
import java.util.List;

/**
 * A minimal implementation of RestaurantSearchService for testing purposes.
 * Use this during development/testing to avoid API rate limits.
 */
public class RestaurantSearchServiceForLocalTesting implements RestaurantSearchService {
    private int callCount = 0;

    @Override
    public List<Restaurant> searchRestaurants(float latitude, float longitude, String term, int limit)
            throws RestaurantSearchException {
        callCount++;
        List<Restaurant> restaurants = new ArrayList<>();

        // Simulate search results
        if (latitude > 43.0f && latitude < 44.0f) {
            // Restaurant 1
            Restaurant r1 = new Restaurant("id_123", "Mario's Pizza", "123 Fake St", "M5V 2T6", 2.0f, List.of(43.6629f, -79.3957f), "Pizza");
            r1.addToRating(5);
            restaurants.add(r1);

            // Restaurant 2
            Restaurant r2 = new Restaurant("id_456", "Sushi King", "456 Queen St", "M5V 2T7", 3.0f, List.of(43.6650f, -79.4000f), "Sushi");
            r2.addToRating(4);
            restaurants.add(r2);

            return restaurants.subList(0, Math.min(limit, restaurants.size()));
        }
        throw new RestaurantSearchException("No restaurants found");
    }

    @Override
    public Restaurant getRestaurantDetails(String restaurantId) {
        callCount++;
        // Return a dummy restaurant for testing
        Restaurant r = new Restaurant(restaurantId, "Test Restaurant", "Test Address", "00000", 2.0f, List.of(0.0f, 0.0f), "Test Food");
        r.addToRating(5);
        return r;
    }

    /**
     * Mock Implementation for User Story 4 (Reviews).
     * Returns review data for testing.
     */
    @Override
    public List<String> getRestaurantReviews(String restaurantId) throws RestaurantSearchException {
        callCount++;
        List<String> testReviews = new ArrayList<>();

        if ("id_123".equals(restaurantId)) {
            testReviews.add("5/5 by Alice: Best pizza in Toronto!");
            testReviews.add("4/5 by Bob: Good crust but a bit oily.");
            testReviews.add("5/5 by Charlie: Highly recommended.");
        } else {
            testReviews.add("3/5 by TestUser: It was okay.");
            testReviews.add("4/5 by Foodie: Nice atmosphere.");
        }

        return testReviews;
    }

    public int getCallCount() { return callCount; }
    public void resetCallCount() { this.callCount = 0; }
}
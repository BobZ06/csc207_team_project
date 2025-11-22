package data_access;

import entity.Restaurant;
import java.util.List;

/**
 * Interface for restaurant search and details.
 */
public interface RestaurantSearchService {

    /**
     * Search for restaurants near given coordinates.
     * @param latitude latitude coordinate
     * @param longitude longitude coordinate
     * @param term search term (e.g., "restaurants", "pizza", "sushi")
     * @param limit maximum number of results (1-50)
     * @return list of restaurants matching search criteria
     * @throws RestaurantSearchException if search fails
     */
    List<Restaurant> searchRestaurants(float latitude, float longitude, String term, int limit)
            throws RestaurantSearchException;

    /**
     * Get detailed information for a specific restaurant.
     * @param restaurantId for the restaurant (Yelp ID)
     * @return restaurant with detailed information
     */
    Restaurant getRestaurantDetails(String restaurantId) throws RestaurantSearchException;

    /**
     * Get up to 3 reviews for a specific restaurant.
     * @param restaurantId The Yelp Business ID
     * @return A list of review strings (e.g., "5/5: Great food!")
     * @throws RestaurantSearchException if the API call fails
     */
    List<String> getRestaurantReviews(String restaurantId) throws RestaurantSearchException;

    /**
     * Exception thrown when restaurant search operations fail.
     */
    class RestaurantSearchException extends Exception {
        public RestaurantSearchException(String message) {
            super("Restaurant search failed: " + message);
        }
    }
}
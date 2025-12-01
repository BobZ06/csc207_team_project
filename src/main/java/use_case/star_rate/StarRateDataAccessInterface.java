package use_case.star_rate;

import data_access.RestaurantSearchService;
import entity.Restaurant;

public interface StarRateDataAccessInterface {
    /**
     * Gets the restaurant object based on the restaurant id.
     * @param id The id of the restaurant
     * @return a Restaurant object based on the id.
     * @throws RestaurantSearchService.RestaurantSearchException if the id doesn't exist in the file.
     */
    Restaurant getRestaurantById(String id) throws RestaurantSearchService.RestaurantSearchException;

    /**
     * Gets the ID of the current restaurant.
     * @return the id of the current restaurant.
     */
    String getCurrentRestaurantId();

    /**
     * Sets the ID of the current restaurant.
     * @param id The id of the restaurant.
     */
    void setCurrentRestaurantId(String id);

    /**
     * Saves the restaurant into the implemented database.
     * @param id The id of the restaurant.
     * @param rest The restaurant object of the corresponding id.
     */
    void save(String id, Restaurant rest);
}

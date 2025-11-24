package data_access;

import java.util.HashMap;
import java.util.Map;
import entity.Restaurant;
import star_rate.StarRateDataAccessInterface;

public class TempStarRateDataAccessObject implements StarRateDataAccessInterface {
    private String currentRestaurantId;
    private final Map<String, Restaurant> restaurants = new HashMap<>();

    @Override
    public Restaurant getRestaurantById(String id) {
        return restaurants.get(id);
    }

    @Override
    public String getCurrentRestaurantId() {
        return currentRestaurantId;
    }

    @Override
    public void setCurrentRestaurantId(String id) {
        this.currentRestaurantId = id;
    }

    @Override
    public void addRestaurant(String id, Restaurant rest) {
        restaurants.put(id, rest);
    }
}

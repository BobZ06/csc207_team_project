package data_access;

import entity.Restaurant;
import star_rate.StarRateDataAccessInterface;

public class APIStarRateDataAccessObject implements StarRateDataAccessInterface{
    private String currentRestaurantId;
    private YelpRestaurantSearchService apiCall = new YelpRestaurantSearchService();

    @Override
    public Restaurant getRestaurantById(String id) throws RestaurantSearchService.RestaurantSearchException {
        return apiCall.getRestaurantDetails(id);
    }

    @Override
    public String getCurrentRestaurantId() {
        return this.currentRestaurantId;
    }

    @Override
    public void setCurrentRestaurantId(String id) {
        this.currentRestaurantId = id;
    }

    @Override
    public void addRestaurant(String id, Restaurant rest) {

    }
}

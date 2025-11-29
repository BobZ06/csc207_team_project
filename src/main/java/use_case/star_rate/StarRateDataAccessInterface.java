package use_case.star_rate;
import data_access.RestaurantSearchService;
import entity.Restaurant;

public interface StarRateDataAccessInterface {
    Restaurant getRestaurantById(String id) throws RestaurantSearchService.RestaurantSearchException;
    String getCurrentRestaurantId();
    void setCurrentRestaurantId(String id);
    void save(String id, Restaurant rest);
}

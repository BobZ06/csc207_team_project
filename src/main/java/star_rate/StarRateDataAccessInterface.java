package star_rate;
import entity.Restaurant;

public interface StarRateDataAccessInterface {
    void save(Restaurant restaurant);
    Restaurant getRestaurantById(String id);
}

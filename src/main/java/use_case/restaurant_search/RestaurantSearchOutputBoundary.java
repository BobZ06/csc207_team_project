package use_case.restaurant_search;

import entity.Restaurant;
import java.util.List;

public interface RestaurantSearchOutputBoundary {
    void prepareSuccessView(List<Restaurant> restaurants);
    void prepareFailView(String errorMessage);
}

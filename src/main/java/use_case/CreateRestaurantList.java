package use_case;

import data_access.RestaurantSearchService;
import data_access.YelpRestaurantSearchService;
import entity.FoodFinderApp;
import entity.Restaurant;
import entity.User;

import java.util.List;

public class CreateRestaurantList {

    private final RestaurantSearchService searchService;
    private final FoodFinderApp app;

    // You can inject any RestaurantSearchService implementation here.
    public CreateRestaurantList(FoodFinderApp app) {
        this.app = app;
        this.searchService = new YelpRestaurantSearchService();
    }

    // Or optionally a constructor that allows injecting a mock in tests:
    public CreateRestaurantList(FoodFinderApp app, RestaurantSearchService searchService) {
        this.app = app;
        this.searchService = searchService;
    }

    public void execute() throws RestaurantSearchService.RestaurantSearchException {
        User currentUser = app.getCurrentUser();
        if (currentUser == null) {
            throw new IllegalStateException("Current user is not set in FoodFinderApp.");
        }

        float userLat = currentUser.getCoords()[0];
        float userLng = currentUser.getCoords()[1];

        // Call Yelp (or whatever service is injected)
        List<Restaurant> restaurants =
                searchService.searchRestaurants(userLat, userLng, "restaurant", 20);

        // Push result back into the entity
        app.setRestaurantList(restaurants);
    }
}
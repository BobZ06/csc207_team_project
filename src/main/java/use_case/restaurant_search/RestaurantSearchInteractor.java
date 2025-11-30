package use_case.restaurant_search;

import data_access.LocationService;
import data_access.RestaurantSearchService;
import entity.Restaurant;

import java.util.List;

public class RestaurantSearchInteractor implements RestaurantSearchInputBoundary {

    private final LocationService locationService;
    private final RestaurantSearchService restaurantService;
    private final RestaurantSearchOutputBoundary outputBoundary;

    public RestaurantSearchInteractor(LocationService locationService,
                                      RestaurantSearchService restaurantService,
                                      RestaurantSearchOutputBoundary outputBoundary) {

        this.locationService = locationService;
        this.restaurantService = restaurantService;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(RestaurantSearchInputData input) {
        try {
            List<Float> coords = locationService.geocodeAddress(input.getAddress());

            float lat = coords.get(0);
            float lng = coords.get(1);

            List<Restaurant> results =
                    restaurantService.searchRestaurants(lat, lng, input.getTerm(), 10);

            if (results.isEmpty()) {
                outputBoundary.prepareFailView("No restaurants found.");
                return;
            }

            outputBoundary.prepareSuccessView(results);

        } catch (Exception e) {
            outputBoundary.prepareFailView("Search error: " + e.getMessage());
        }
    }
}

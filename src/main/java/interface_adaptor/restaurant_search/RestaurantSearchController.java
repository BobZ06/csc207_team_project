package interface_adaptor.restaurant_search;

import use_case.restaurant_search.RestaurantSearchInputBoundary;
import use_case.restaurant_search.RestaurantSearchInputData;

public class RestaurantSearchController {

    private final RestaurantSearchInputBoundary interactor;

    public RestaurantSearchController(RestaurantSearchInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String address, String term) {
        RestaurantSearchInputData input = new RestaurantSearchInputData(address, term);
        interactor.execute(input);
    }
}

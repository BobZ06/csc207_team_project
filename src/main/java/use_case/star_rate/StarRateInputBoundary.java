package use_case.star_rate;

import data_access.RestaurantSearchService;

public interface StarRateInputBoundary {
    /**
     * The execute method is where the actual rating method is done.
     * @param inputData the input of the execute method.
     * @throws RestaurantSearchService.RestaurantSearchException when the API cannot call the restaurant
     */
    void execute(StarRateInputData inputData) throws RestaurantSearchService.RestaurantSearchException;
}

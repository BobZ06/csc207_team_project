package star_rate;

import data_access.RestaurantSearchService;

public interface StarRateInputBoundary {
    void execute(StarRateInputData inputData) throws RestaurantSearchService.RestaurantSearchException;
}

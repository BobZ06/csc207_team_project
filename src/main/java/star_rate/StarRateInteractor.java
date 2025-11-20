package star_rate;
import data_access.RestaurantSearchService;
import entity.Restaurant;

public class StarRateInteractor implements StarRateInputBoundary{
    final private StarRateOutputBoundary outputBoundary;
    final private StarRateDataAccessInterface dataAccess;

    public StarRateInteractor(StarRateOutputBoundary output, StarRateDataAccessInterface data){
        this.outputBoundary = output;
        this.dataAccess = data;
    }

    public void execute(StarRateInputData inputData) throws RestaurantSearchService.RestaurantSearchException {
        String restaurantId = dataAccess.getCurrentRestaurantId();

        if (dataAccess.getRestaurantById(restaurantId) == null){
            outputBoundary.prepareFailView("Restaurant not found.");
        }
        else{
            Restaurant restaurant = dataAccess.getRestaurantById(restaurantId);

            int starRate = inputData.getStarRating();

            restaurant.addToRating(starRate);
            float newAverage = restaurant.getAverageRating();

            StarRateOutputData outputData = new StarRateOutputData(starRate, newAverage);
            outputBoundary.prepareSuccessView(outputData);
        }
    }
}

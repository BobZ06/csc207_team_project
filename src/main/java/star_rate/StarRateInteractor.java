package star_rate;
import entity.Restaurant;

public class StarRateInteractor implements StarRateInputBoundary{
    final private StarRateOutputBoundary outputBoundary;
    final private StarRateDataAccessInterface dataAccess;

    public StarRateInteractor(StarRateOutputBoundary output, StarRateDataAccessInterface data){
        this.outputBoundary = output;
        this.dataAccess = data;
    }

    public void execute(StarRateInputData inputData){
        String restaurantId = inputData.getRestaurantId();
        Restaurant restaurant = dataAccess.getRestaurantById(restaurantId);

        int starRate = inputData.getStarRating();

        restaurant.addToRating(starRate);
        float newAverage = restaurant.getAverageRating();
        dataAccess.save(restaurant);

        StarRateOutputData outputData = new StarRateOutputData(starRate, newAverage);
        outputBoundary.PrepareSuccessView(outputData);
    }
}

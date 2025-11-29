package use_case.star_rate;
import data_access.RestaurantSearchService;
import entity.Restaurant;
import entity.User;
import use_case.log_in.LoginDataAccessInterface;

public class StarRateInteractor implements StarRateInputBoundary{
    private final StarRateOutputBoundary outputBoundary;
    private final StarRateDataAccessInterface dataAccess;
    private final LoginDataAccessInterface userDataAccess;

    public StarRateInteractor(StarRateOutputBoundary output,
                              StarRateDataAccessInterface data,
                              LoginDataAccessInterface login){
        this.outputBoundary = output;
        this.dataAccess = data;
        this.userDataAccess = login;
    }

    public void execute(StarRateInputData inputData) throws RestaurantSearchService.RestaurantSearchException {
        String restaurantId = inputData.getRestaurantId();
        String username = userDataAccess.getCurrentUsername();
        User user = userDataAccess.get(username);

        if (dataAccess.getRestaurantById(restaurantId) == null){
            outputBoundary.prepareFailView("Restaurant not found.");
        }
        else if (user.inReviewed(restaurantId)){
            outputBoundary.prepareFailView("You have already reviewed this restaurant.");
        }
        else{
            Restaurant restaurant = dataAccess.getRestaurantById(restaurantId);

            int starRate = inputData.getStarRating();

            restaurant.addToRating(starRate);
            user.addToReviewed(restaurantId);
            userDataAccess.save(user);
            float newAverage = restaurant.getAverageRating();
            dataAccess.save(restaurantId, restaurant);

            StarRateOutputData outputData = new StarRateOutputData(newAverage);
            outputBoundary.prepareSuccessView(outputData);
        }
    }
}

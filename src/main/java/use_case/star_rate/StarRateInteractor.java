package use_case.star_rate;

import data_access.RestaurantSearchService;
import entity.Restaurant;
import entity.User;
import use_case.log_in.LoginDataAccessInterface;

/**
 * The Star Rate Interactor.
 */
public class StarRateInteractor implements StarRateInputBoundary {
    private final StarRateOutputBoundary outputBoundary;
    private final StarRateDataAccessInterface dataAccess;
    private final LoginDataAccessInterface userDataAccess;

    /**
     * The Star Rate Constructor.
     * @param output The output boundary of the interactor
     * @param data The data access interface.
     * @param login the login data access interface
     */
    public StarRateInteractor(StarRateOutputBoundary output,
                              StarRateDataAccessInterface data,
                              LoginDataAccessInterface login) {
        this.outputBoundary = output;
        this.dataAccess = data;
        this.userDataAccess = login;
    }

    /**
     * The execute method is where the actual rating method is done.
     * @param inputData the input of the execute method.
     * @throws RestaurantSearchService.RestaurantSearchException when the API cannot call the restaurant
     */
    public void execute(StarRateInputData inputData) throws RestaurantSearchService.RestaurantSearchException {
        final String restaurantId = inputData.getRestaurantId();
        final String username = userDataAccess.getCurrentUsername();
        final User user = userDataAccess.get(username);

        if (dataAccess.getRestaurantById(restaurantId) == null) {
            outputBoundary.prepareFailView("Restaurant not found.");
        }
        else if (user.inReviewed(restaurantId)) {
            outputBoundary.prepareFailView("You have already reviewed this restaurant.");
        }
        else {
            final Restaurant restaurant = dataAccess.getRestaurantById(restaurantId);

            final int starRate = inputData.getStarRating();

            restaurant.addToRating(starRate);
            user.addToReviewed(restaurantId);
            userDataAccess.save(user);
            final float newAverage = restaurant.getAverageRating();
            dataAccess.save(restaurantId, restaurant);

            final StarRateOutputData outputData = new StarRateOutputData(newAverage);
            outputBoundary.prepareSuccessView(outputData);
        }
    }
}

package use_case;

import data_access.RestaurantSearchService;
import data_access.TempStarRateDataAccessObject;
import data_access.TempUserDataAccessObject;
import entity.Restaurant;
import entity.User;
import use_case.log_in.LoginDataAccessInterface;
import org.junit.jupiter.api.Test;
import use_case.star_rate.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class StarRateTest {
    @Test
    void successTest() throws RestaurantSearchService.RestaurantSearchException {
        StarRateInputData inputData = new StarRateInputData(5, "1042");
        StarRateDataAccessInterface dataAccess = new TempStarRateDataAccessObject();
        LoginDataAccessInterface userData = new TempUserDataAccessObject();

        // Add a new Restaurant entity to rate.
        ArrayList<Float> coords = new ArrayList<>();
        coords.add(10f);
        coords.add(10f);
        Restaurant rest = new Restaurant(10f, coords, "Burger", "1042");
        rest.setName("Burger King");
        rest.setAddress("220 Yonge Street");
        dataAccess.save(rest.getId(), rest);
        dataAccess.setCurrentRestaurantId(rest.getId());

        // Add a new user.
        User user = new User("username", "password");
        userData.save(user);
        userData.setCurrentUsername(user.getName());

        // Create a new Output Boundary of what we expect to output.
        StarRateOutputBoundary output = new StarRateOutputBoundary(){

            @Override
            public void prepareSuccessView(StarRateOutputData outputData) {
                assertEquals(5f, outputData.getAverage());
            }
            @Override
            public void prepareFailView(String errorMessage) {fail("Unexpected output");}
        };
        StarRateInputBoundary interactor = new StarRateInteractor(output, dataAccess, userData);
        interactor.execute(inputData);
    }

    @Test
    void FailTest() throws RestaurantSearchService.RestaurantSearchException {
        StarRateInputData inputData = new StarRateInputData(5, "1001010010");
        StarRateDataAccessInterface dataAccess = new TempStarRateDataAccessObject();
        LoginDataAccessInterface userData = new TempUserDataAccessObject();

        // Add a new Restaurant entity to rate.
        ArrayList<Float> coords = new ArrayList<>();
        coords.add(10f);
        coords.add(10f);
        Restaurant rest = new Restaurant(10f, coords, "Burger", "1042");
        rest.setName("Burger King");
        rest.setAddress("220 Yonge Street");
        dataAccess.save(rest.getId(), rest);
        dataAccess.setCurrentRestaurantId("1042");

        // Add a new user.
        User user = new User("username", "password");
        userData.save(user);
        userData.setCurrentUsername(user.getName());

        // Create a new Output Boundary of what we expect to output.
        StarRateOutputBoundary output = new StarRateOutputBoundary(){

            @Override
            public void prepareSuccessView(StarRateOutputData outputData) {
                fail("Should not be a success.");
            }
            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Restaurant not found.", errorMessage);
            }
        };
        StarRateInputBoundary interactor = new StarRateInteractor(output, dataAccess, userData);
        interactor.execute(inputData);

    }
    @Test
    void AlreadyReviewedTest() throws RestaurantSearchService.RestaurantSearchException{
        StarRateInputData inputData = new StarRateInputData(5, "1042");
        StarRateDataAccessInterface dataAccess = new TempStarRateDataAccessObject();
        LoginDataAccessInterface userData = new TempUserDataAccessObject();

        // Add a new Restaurant entity to rate.
        ArrayList<Float> coords = new ArrayList<>();
        coords.add(10f);
        coords.add(10f);
        Restaurant rest = new Restaurant(10f, coords, "Burger", "1042");
        rest.setName("Burger King");
        rest.setAddress("220 Yonge Street");
        dataAccess.save(rest.getId(), rest);
        dataAccess.setCurrentRestaurantId("1042");

        // Add a new user.
        User user = new User("username", "password");
        user.addToReviewed("1042");
        userData.save(user);
        userData.setCurrentUsername(user.getName());

        // Create a new Output Boundary of what we expect to output.
        StarRateOutputBoundary output = new StarRateOutputBoundary(){

            @Override
            public void prepareSuccessView(StarRateOutputData outputData) {
                fail("Should not be a success.");
            }
            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("You have already reviewed this restaurant.", errorMessage);
            }
        };
        StarRateInputBoundary interactor = new StarRateInteractor(output, dataAccess, userData);
        interactor.execute(inputData);
    }
}

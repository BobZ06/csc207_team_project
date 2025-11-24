package use_case;

import data_access.RestaurantSearchService;
import data_access.TempStarRateDataAccessObject;
import entity.Restaurant;
import org.junit.jupiter.api.Test;
import star_rate.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class starRate {
    @Test
    void successTest() throws RestaurantSearchService.RestaurantSearchException {
        StarRateInputData inputData = new StarRateInputData(5, "1042");
        StarRateDataAccessInterface dataAccess = new TempStarRateDataAccessObject();

        // Add a new Restaurant entity to rate.
        ArrayList<Float> coords = new ArrayList<>();
        coords.add(10f);
        coords.add(10f);
        Restaurant rest = new Restaurant(10f, coords, "Burger", "1042");
        rest.setName("Burger King");
        rest.setAddress("220 Yonge Street");
        dataAccess.addRestaurant(rest.getId(), rest);
        dataAccess.setCurrentRestaurantId(rest.getId());

        // Create a new Output Boundary of what we expect to output.
        StarRateOutputBoundary output = new StarRateOutputBoundary(){

            @Override
            public void prepareSuccessView(StarRateOutputData outputData) {
                assertEquals(5f, rest.getAverageRating());
            }
            @Override
            public void prepareFailView(String errorMessage) {fail("Unexpected output");}
        };
        StarRateInputBoundary interactor = new StarRateInteractor(output, dataAccess);
        interactor.execute(inputData);
    }

    @Test
    void FailTest() throws RestaurantSearchService.RestaurantSearchException {
        StarRateInputData inputData = new StarRateInputData(5, "1001010010");
        StarRateDataAccessInterface dataAccess = new TempStarRateDataAccessObject();

        // Add a new Restaurant entity to rate.
        ArrayList<Float> coords = new ArrayList<>();
        coords.add(10f);
        coords.add(10f);
        Restaurant rest = new Restaurant(10f, coords, "Burger", "1042");
        rest.setName("Burger King");
        rest.setAddress("220 Yonge Street");
        dataAccess.addRestaurant(rest.getId(), rest);
        dataAccess.setCurrentRestaurantId("1042");

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
        StarRateInputBoundary interactor = new StarRateInteractor(output, dataAccess);
        interactor.execute(inputData);

    }
}

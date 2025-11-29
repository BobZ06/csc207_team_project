package use_case;

import use_case.view_ratings.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ViewRatingsInteractorTest {

    // Test 1: Success Scenario
    @Test
    void successTest() {
        ViewRatingsInputData inputData = new ViewRatingsInputData("pai-northern-thai-kitchen-toronto-2");

        ViewRatingsDataAccessInterface successDataAccess = new ViewRatingsDataAccessInterface() {
            @Override
            public List<String> getYelpReviews(String yelpID) {
                List<String> mockReviews = new ArrayList<>();
                mockReviews.add("Great food!");
                mockReviews.add("Service was okay.");
                return mockReviews;
            }
        };

        // Create Output Boundary to test
        ViewRatingsOutputBoundary successPresenter = new ViewRatingsOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewRatingsOutputData outputData) {
                // Test if we received the correct ID and reviews
                assertEquals("pai-northern-thai-kitchen-toronto-2", outputData.getRestaurantID());
                assertEquals(2, outputData.getReviews().size());
                assertEquals("Great food!", outputData.getReviews().get(0));
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case fails.");
            }
        };

        // Execute use case
        ViewRatingsInputBoundary interactor = new ViewRatingsInteractor(successDataAccess, successPresenter);
        interactor.execute(inputData);
    }

    // Test 2: Failure Scenario - No Reviews
    @Test
    void noReviewsTest() {
        ViewRatingsInputData inputData = new ViewRatingsInputData("empty-restaurant-id");

        ViewRatingsDataAccessInterface emptyDataAccess = new ViewRatingsDataAccessInterface() {
            @Override
            public List<String> getYelpReviews(String yelpID) {
                return new ArrayList<>();
            }
        };

        ViewRatingsOutputBoundary failPresenter = new ViewRatingsOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewRatingsOutputData outputData) {
                fail("Use case success is unexpected (expected failure).");
            }

            @Override
            public void prepareFailView(String error) {
                // Test if the error aligns with Interactor
                assertEquals("No reviews found.", error);
            }
        };

        ViewRatingsInputBoundary interactor = new ViewRatingsInteractor(emptyDataAccess, failPresenter);
        interactor.execute(inputData);
    }

    // Test 3: Exception Scenario
    @Test
    void exceptionTest() {
        ViewRatingsInputData inputData = new ViewRatingsInputData("error-id");

        ViewRatingsDataAccessInterface errorDataAccess = new ViewRatingsDataAccessInterface() {
            @Override
            public List<String> getYelpReviews(String yelpID) {
                throw new RuntimeException("Network Error");
            }
        };

        ViewRatingsOutputBoundary exceptionPresenter = new ViewRatingsOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewRatingsOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                // Test if Interactor correctly output "API Error: "
                assertEquals("API Error: Network Error", error);
            }
        };

        ViewRatingsInputBoundary interactor = new ViewRatingsInteractor(errorDataAccess, exceptionPresenter);
        interactor.execute(inputData);
    }
}
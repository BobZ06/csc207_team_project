package use_case.view_ratings;

/**
 * The Output Boundary interface for the View Ratings.
 * It defines the methods that the Presenter must implement to update the view.
 */
public interface ViewRatingsOutputBoundary {

    /**
     * Prepares the success view with the list of reviews.
     *
     * @param outputData the output data containing the reviews.
     */
    void prepareSuccessView(ViewRatingsOutputData outputData);

    /**
     * Prepares the failure view with an error message.
     *
     * @param error the error message to display.
     */
    void prepareFailView(String error);
}

package use_case.view_ratings;

/**
 * The Input Boundary interface for the View Ratings.
 * It defines the input method that the Controller calls.
 */
public interface ViewRatingsInputBoundary {

    /**
     * Executes the view ratings use case logic.
     *
     * @param viewRatingsInputData the input data containing the restaurant ID.
     */
    void execute(ViewRatingsInputData viewRatingsInputData);
}

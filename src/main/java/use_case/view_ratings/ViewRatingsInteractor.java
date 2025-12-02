package use_case.view_ratings;

import java.util.List;

/**
 * The Interactor for the View Ratings.
 * It organizes the flow of data between the DAO and the Presenter.
 */
public class ViewRatingsInteractor implements ViewRatingsInputBoundary {
    private final ViewRatingsDataAccessInterface dataAccessObject;
    private final ViewRatingsOutputBoundary presenter;

    public ViewRatingsInteractor(ViewRatingsDataAccessInterface dataAccessObject,
                                 ViewRatingsOutputBoundary presenter) {
        this.dataAccessObject = dataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute(ViewRatingsInputData inputData) {
        final String yelpID = inputData.getYelpID();

        try {
            final List<String> reviews = dataAccessObject.getYelpReviews(yelpID);

            if (reviews.isEmpty()) {
                presenter.prepareFailView("No reviews found.");
            }
            else {
                final ViewRatingsOutputData outputData = new ViewRatingsOutputData(reviews, yelpID);
                presenter.prepareSuccessView(outputData);
            }
        }
        // -@cs[IllegalCatch] Catching exception to display error in UI.
        catch (Exception error) {
            presenter.prepareFailView("API Error: " + error.getMessage());
        }
    }
}

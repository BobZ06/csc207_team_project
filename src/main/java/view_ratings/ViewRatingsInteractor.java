package view_ratings;

import java.util.List;

public class ViewRatingsInteractor implements ViewRatingsInputBoundary {
    final ViewRatingsDataAccessInterface dataAccessObject;
    final ViewRatingsOutputBoundary presenter;

    public ViewRatingsInteractor(ViewRatingsDataAccessInterface dataAccessObject,
                                 ViewRatingsOutputBoundary presenter) {
        this.dataAccessObject = dataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute(ViewRatingsInputData inputData) {
        String yelpID = inputData.getYelpID();

        try {
            List<String> reviews = dataAccessObject.getYelpReviews(yelpID);

            if (reviews.isEmpty()) {
                presenter.prepareFailView("No reviews found.");
            } else {
                ViewRatingsOutputData outputData = new ViewRatingsOutputData(reviews, yelpID);
                presenter.prepareSuccessView(outputData);
            }
        } catch (Exception e) {
            presenter.prepareFailView("API Error: " + e.getMessage());
        }
    }
}
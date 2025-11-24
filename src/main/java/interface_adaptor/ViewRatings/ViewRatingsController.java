package interface_adaptor.ViewRatings;

import view_ratings.ViewRatingsInputBoundary;
import view_ratings.ViewRatingsInputData;

public class ViewRatingsController {
    final ViewRatingsInputBoundary interactor;

    public ViewRatingsController(ViewRatingsInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String yelpID) {
        ViewRatingsInputData inputData = new ViewRatingsInputData(yelpID);
        interactor.execute(inputData);
    }
}
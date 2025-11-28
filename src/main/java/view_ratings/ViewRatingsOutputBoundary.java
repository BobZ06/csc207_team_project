package view_ratings;

public interface ViewRatingsOutputBoundary {
    void prepareSuccessView(ViewRatingsOutputData outputData);
    void prepareFailView(String error);
}

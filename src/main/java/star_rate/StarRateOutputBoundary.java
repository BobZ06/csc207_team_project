package star_rate;

public interface StarRateOutputBoundary {
    void prepareSuccessView(StarRateOutputData outputData);

    void prepareFailView(String errorMessage);
}

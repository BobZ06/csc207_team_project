package star_rate;

public interface StarRateOutputBoundary {
    void PrepareSuccessView(StarRateOutputData outputData);

    void PrepareFailView(String errorMessage);
}

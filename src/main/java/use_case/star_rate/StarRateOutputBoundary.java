package use_case.star_rate;

public interface StarRateOutputBoundary {
    /**
    * Prepares the success view based on the output data.
     * @param outputData the output of the interactor.
     */
    void prepareSuccessView(StarRateOutputData outputData);

    /**
     * If the interactor fails, this method is called.
     * @param errorMessage message to be displayed by the view if there are errors in the interactor.
     */
    void prepareFailView(String errorMessage);
}

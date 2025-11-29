package use_case.log_in;

public interface LoginOutputBoundary {
    void prepareSuccessView(LoginOutputData output);
    void prepareFailView(String errorMessage);
}

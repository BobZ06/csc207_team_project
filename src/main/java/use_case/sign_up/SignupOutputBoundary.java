package use_case.sign_up;

public interface SignupOutputBoundary {
    void prepareSuccessView(SignupOutputData outputData);
    void prepareFailView(String error);
}

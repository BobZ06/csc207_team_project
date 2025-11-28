package interface_adaptor.Signup;

import interface_adaptor.ViewManagerModel;
import sign_up.SignupOutputBoundary;
import sign_up.SignupOutputData;

public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel signupVM;
    private final ViewManagerModel viewManagerModel;

    public SignupPresenter(SignupViewModel signupVM, ViewManagerModel viewManagerModel) {
        this.signupVM = signupVM;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(SignupOutputData outputData) {
        viewManagerModel.setState("log in");
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        SignupState state = signupVM.getState();
        state.setMessage(error);
        signupVM.firePropertyChange();
    }
}

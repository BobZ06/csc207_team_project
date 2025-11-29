package interface_adaptor.signup;

import interface_adaptor.Login.LoginViewModel;
import interface_adaptor.ViewManagerModel;
import signup.SignupOutputBoundary;
import signup.SignupOutputData;

public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel signupVM;
    private final ViewManagerModel viewManagerModel;
    private final LoginViewModel loginVM;

    public SignupPresenter(SignupViewModel signupVM, ViewManagerModel viewManagerModel, LoginViewModel loginVM) {
        this.signupVM = signupVM;
        this.viewManagerModel = viewManagerModel;
        this.loginVM = loginVM;
    }

    @Override
    public void prepareSuccessView(SignupOutputData outputData) {
        viewManagerModel.setState(loginVM.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        SignupState state = signupVM.getState();
        state.setUsernameError(error);
        signupVM.firePropertyChange();
    }

    @Override
    public void switchToLoginView() {
        viewManagerModel.setState(loginVM.getViewName());
        viewManagerModel.firePropertyChange();
    }
}

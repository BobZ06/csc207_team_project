package interface_adaptor.Login;

import interface_adaptor.ViewManagerModel;
import interface_adaptor.BlackState;
import interface_adaptor.BlankViewModel;
import log_in.LoginOutputBoundary;
import log_in.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary{
    private final BlankViewModel blankModel;
    private final LoginViewModel loginModel;
    private final ViewManagerModel viewModel;

   public LoginPresenter(BlankViewModel blank, LoginViewModel login, ViewManagerModel view){
       this.blankModel = blank;
       this.loginModel = login;
       this.viewModel = view;
   }


    @Override
    public void prepareSuccessView(LoginOutputData output) {
        // Switch to the Blank View
        this.viewModel.setState(blankModel.getViewName());
        this.viewModel.firePropertyChange();

    }

    @Override
    public void prepareFailView(String errorMessage) {
        final LoginState loginState = loginModel.getState();
        loginState.setLoginError(errorMessage);
        loginModel.firePropertyChange();
    }
}

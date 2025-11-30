package interface_adaptor.log_in;

import interface_adaptor.ViewManagerModel;
import interface_adaptor.BlankViewModel;
import use_case.log_in.LoginOutputBoundary;
import use_case.log_in.LoginOutputData;

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
        this.viewModel.setState("SearchView");
        this.viewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final LoginState loginState = loginModel.getState();
        loginState.setLoginError(errorMessage);
        loginModel.firePropertyChange();
    }
}

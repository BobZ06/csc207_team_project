package interface_adaptor.log_out;

import interface_adaptor.ViewManagerModel;
import interface_adaptor.log_in.LoginState;
import interface_adaptor.log_in.LoginViewModel;
import interface_adaptor.restaurant_search.RestaurantSearchViewModel;
import use_case.log_out.LogoutOutputBoundary;
import use_case.log_out.LogoutOutputData;

public class LogoutPresenter implements LogoutOutputBoundary {
    private RestaurantSearchViewModel menuViewModel;
    private ViewManagerModel viewManagerModel;
    private LoginViewModel loginViewModel;

    public LogoutPresenter(ViewManagerModel viewManagerModel,
                           RestaurantSearchViewModel loggedInViewModel,
                           LoginViewModel loginViewModel) {
        this.menuViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LogoutOutputData output) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setUsername(output.getUsername());
        loginState.setPassword("");

        loginViewModel.firePropertyChange();

        this.viewManagerModel.setState(loginViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }
}

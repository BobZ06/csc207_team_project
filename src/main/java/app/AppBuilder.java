package app;
import data_access.*;

import interface_adaptor.BlankViewModel;
import interface_adaptor.Login.LoginController;
import interface_adaptor.Login.LoginPresenter;
import interface_adaptor.Login.LoginViewModel;
import interface_adaptor.Menu.MenuViewModel;
import interface_adaptor.Menu.StarRateController;
import interface_adaptor.Menu.StarRatePresenter;
import interface_adaptor.ViewManagerModel;
import log_in.LoginInputBoundary;
import log_in.LoginInteractor;
import log_in.LoginOutputBoundary;
import star_rate.StarRateInputBoundary;
import star_rate.StarRateInteractor;
import star_rate.StarRateOutputBoundary;
import view.BlankView;
import view.LoginView;
import view.MenuView;
import view.ViewManager;

// IMPORTANT!!!!! REMOVE THIS IN THE FINAL THING!!!!!!!
import entity.Restaurant;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // Data Access Object Temp User Data:
    final TempUserDataAccessObject userDataAccessObject = new TempUserDataAccessObject();

    // Data Access Object Temp Star Rate:
    final TempStarRateDataAccessObject starRateDataAccessObject = new TempStarRateDataAccessObject();

    private BlankView blankView;
    private LoginView loginView;
    private MenuView menuView;
    private BlankViewModel blankViewModel;
    private LoginViewModel loginViewModel;
    private MenuViewModel menuViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addBlankView(){
        blankViewModel = new BlankViewModel();
        blankView = new BlankView();
        cardPanel.add(blankView, blankView.getViewName());
        return this;
    }
    public AppBuilder addLoginView(){
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    public AppBuilder addMenuView(){
        menuViewModel = new MenuViewModel();
        menuView = new MenuView(menuViewModel);
        cardPanel.add(menuView, menuView.getViewName());
        return this;
    }

    public AppBuilder addLoginUseCase(){
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(
                blankViewModel, loginViewModel, viewManagerModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);
        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addStarRateUseCase() throws RestaurantSearchService.RestaurantSearchException {
        final StarRateOutputBoundary starRateOutputBoundary = new StarRatePresenter(
                viewManagerModel, menuViewModel);
        final StarRateInputBoundary starRateInteractor = new StarRateInteractor(
                starRateOutputBoundary, starRateDataAccessObject);
        StarRateController starRateController = new StarRateController(starRateInteractor);
        menuView.setStarRateController(starRateController);

        // IMPORTANT REMOVE WHEN DONE!!!!
        ArrayList coords = new ArrayList<Float>();
        coords.add(10f);
        coords.add(10f);
        Restaurant rest = new Restaurant(10f, coords, "Burger", "1012301023102301023");
        starRateDataAccessObject.addRestaurant(rest.getId(), rest);
        starRateDataAccessObject.setCurrentRestaurantId(rest.getId());
//        YelpRestaurantSearchService apiCall = new YelpRestaurantSearchService();
//        ArrayList restaurantList = (ArrayList) apiCall.searchRestaurants(40.7128f, -74.0060f, "Burger", 10);

        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("Fork & Code");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(menuView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }
}

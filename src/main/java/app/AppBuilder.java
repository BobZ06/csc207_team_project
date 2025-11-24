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
import entity.MenuItem;
import interface_adaptor.Menu.MenuState;
import interface_adaptor.Menu.MenuSearchController;
import interface_adaptor.Menu.MenuSearchPresenter;
import menu_search.MenuSearchInputBoundary;
import menu_search.MenuSearchInteractor;
import menu_search.MenuSearchOutputBoundary;


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

    // Data Access Object Temp Menu:
    final TempMenuDataAccessObject menuDataAccessObject = new TempMenuDataAccessObject();

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

        // ---- IMPORTANT REMOVE WHEN DONE (USE CASE 3 & 5 DEMO) ----
        ArrayList<Float> coords = new ArrayList<>();
        coords.add(10f);
        coords.add(10f);
        Restaurant rest = new Restaurant(10f, coords, "Burger", "1042");
        rest.setName("Burger King");
        rest.setAddress("220 Yonge Street");

        starRateDataAccessObject.addRestaurant(rest.getId(), rest);
        starRateDataAccessObject.setCurrentRestaurantId(rest.getId());

        MenuState menuState = menuViewModel.getState();
        menuState.setName(rest.getName());
        menuState.setRestaurant(rest.getId());
        menuState.setAddress(rest.getAddress());
        menuState.setRating(rest.getAverageRating());

        // TEMP MENU
        menuDataAccessObject.addMenuItem(rest.getId(),
                new MenuItem("Cheeseburger", 9.99f, "Beef burger with cheese"));
        menuDataAccessObject.addMenuItem(rest.getId(),
                new MenuItem("Megaburger", 12.99f, "Double the meat, double the taste"));
        menuDataAccessObject.addMenuItem(rest.getId(),
                new MenuItem("Salad", 8.99f, "With fresh greens and preferred sauce"));
        menuDataAccessObject.addMenuItem(rest.getId(),
                new MenuItem("Fries", 3.99f, "Crispy french fries"));
        menuDataAccessObject.addMenuItem(rest.getId(),
                new MenuItem("Diet Coke", 1.59f, "Ice-cold soft drink"));
        menuDataAccessObject.addMenuItem(rest.getId(),
                new MenuItem("Ketchup", 0.39f, "Good ol' ketchup"));

        java.util.ArrayList<MenuItem> allItems =
                new java.util.ArrayList<>(menuDataAccessObject.getMenu(rest.getId()));
        menuState.setMenuList(allItems);
        menuViewModel.firePropertyChange();

        MenuSearchOutputBoundary menuSearchOutputBoundary =
                new MenuSearchPresenter(menuViewModel);
        MenuSearchInputBoundary menuSearchInteractor =
                new MenuSearchInteractor(menuDataAccessObject, menuSearchOutputBoundary);
        MenuSearchController menuSearchController =
                new MenuSearchController(menuSearchInteractor);
        menuView.setMenuSearchController(menuSearchController);


        // YelpRestaurantSearchService apiCall = new YelpRestaurantSearchService();
        // ArrayList restaurantList = (ArrayList) apiCall.searchRestaurants(40.7128f, -74.0060f, "Burger", 10);

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

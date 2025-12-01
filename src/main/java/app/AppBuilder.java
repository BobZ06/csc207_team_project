package app;

import data_access.*;

import interface_adaptor.BlankViewModel;
import interface_adaptor.log_in.LoginController;
import interface_adaptor.log_in.LoginPresenter;
import interface_adaptor.log_in.LoginViewModel;
import interface_adaptor.log_out.LogoutController;
import interface_adaptor.log_out.LogoutPresenter;
import interface_adaptor.menu.MenuViewModel;
import interface_adaptor.menu.StarRateController;
import interface_adaptor.menu.StarRatePresenter;
import interface_adaptor.restaurant_search.RestaurantSearchController;
import interface_adaptor.restaurant_search.RestaurantSearchPresenter;
import interface_adaptor.restaurant_search.RestaurantSearchViewModel;
import interface_adaptor.ViewManagerModel;
import interface_adaptor.sign_up.SignupController;
import interface_adaptor.sign_up.SignupPresenter;
import interface_adaptor.sign_up.SignupViewModel;
import interface_adaptor.view_ratings.ViewRatingsController;
import interface_adaptor.view_ratings.ViewRatingsPresenter;
import interface_adaptor.view_ratings.ViewRatingsViewModel;

import use_case.log_in.LoginInputBoundary;
import use_case.log_in.LoginInteractor;
import use_case.log_in.LoginOutputBoundary;
import use_case.log_out.LogoutInputBoundary;
import use_case.log_out.LogoutInteractor;
import use_case.log_out.LogoutOutputBoundary;
import use_case.restaurant_search.RestaurantSearchInteractor;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import use_case.star_rate.StarRateDataAccessInterface;
import use_case.star_rate.StarRateInputBoundary;
import use_case.star_rate.StarRateInteractor;
import use_case.star_rate.StarRateOutputBoundary;
import use_case.menu_search.MenuSearchInputBoundary;
import use_case.menu_search.MenuSearchInteractor;
import use_case.menu_search.MenuSearchOutputBoundary;
import use_case.view_menu.ViewMenuInputBoundary;
import use_case.view_menu.ViewMenuOutputBoundary;
import use_case.view_menu.ViewMenuDataAccessInterface;
import use_case.view_menu.ViewMenuInteractor;
import use_case.view_ratings.ViewRatingsDataAccessInterface;
import use_case.view_ratings.ViewRatingsInputBoundary;
import use_case.view_ratings.ViewRatingsInteractor;
import use_case.view_ratings.ViewRatingsOutputBoundary;

import view.*;
import entity.MenuItem;
import interface_adaptor.menu.MenuState;
import interface_adaptor.menu.MenuSearchController;
import interface_adaptor.menu.MenuSearchPresenter;
import interface_adaptor.menu.ViewMenuController;
import interface_adaptor.menu.ViewMenuPresenter;

import entity.Restaurant;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    final FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject("users.csv");
    final StarRateDataAccessInterface starRateDataAccessObject = new TempFileStarRateDAO("restaurants.csv");
    final TempMenuDataAccessObject menuDataAccessObject = new TempMenuDataAccessObject();

    private BlankView blankView;
    private LoginView loginView;
    private MenuView menuView;
    private SignupView signupView;
    private BlankViewModel blankViewModel;
    private LoginViewModel loginViewModel;
    private MenuViewModel menuViewModel;
    private SignupViewModel signupViewModel;
    private final MenuService menuService = new SpoonacularMenuService();
    private SearchView searchView;
    private AddressSearchView addressSearchView;
    private RestaurantSearchViewModel restaurantSearchViewModel;

    // View Ratings ViewModel
    private ViewRatingsViewModel viewRatingsViewModel;

    public AppBuilder() throws IOException {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addBlankView() {
        blankViewModel = new BlankViewModel();
        blankView = new BlankView();
        cardPanel.add(blankView, blankView.getViewName());
        return this;
    }

    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginViewModel.setViewManagerModel(viewManagerModel);
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    public AppBuilder addSearchView() {
        restaurantSearchViewModel = new RestaurantSearchViewModel();
        addressSearchView = new AddressSearchView(restaurantSearchViewModel);
        cardPanel.add(addressSearchView, addressSearchView.getViewName());
        return this;
    }

    public AppBuilder addMenuView() {
        menuViewModel = new MenuViewModel();
        menuView = new MenuView(menuViewModel);
        cardPanel.add(menuView, menuView.getViewName());
        return this;
    }

    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(
                blankViewModel, loginViewModel, viewManagerModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);
        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutput = new LogoutPresenter(viewManagerModel, restaurantSearchViewModel,
                loginViewModel);
        final LogoutInputBoundary logoutInteractor = new LogoutInteractor(userDataAccessObject, logoutOutput);
        LogoutController logoutController = new LogoutController(logoutInteractor);
        addressSearchView.setLogoutController(logoutController);
        return this;
    }

    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(
                signupViewModel, viewManagerModel, loginViewModel);
        final SignupInputBoundary signupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary);
        SignupController signupController = new SignupController(signupInteractor);
        signupView.setSignupController(signupController);
        return this;
    }

    public AppBuilder addRestaurantSearchUseCase() {
        LocationService locationService = new GoogleMapsLocationService();
        RestaurantSearchService yelpService = new YelpRestaurantSearchService();

        RestaurantSearchPresenter presenter =
                new RestaurantSearchPresenter(restaurantSearchViewModel, viewManagerModel);

        RestaurantSearchInteractor interactor =
                new RestaurantSearchInteractor(locationService, yelpService, presenter);

        RestaurantSearchController controller =
                new RestaurantSearchController(interactor);

        addressSearchView.setController(controller);

        return this;
    }

    public AppBuilder addStarRateUseCase() throws RestaurantSearchService.RestaurantSearchException {
        final StarRateOutputBoundary starRateOutputBoundary = new StarRatePresenter(
                viewManagerModel, menuViewModel);
        final StarRateInputBoundary starRateInteractor = new StarRateInteractor(
                starRateOutputBoundary, starRateDataAccessObject, userDataAccessObject);
        StarRateController starRateController = new StarRateController(starRateInteractor);
        menuView.setStarRateController(starRateController);

        MenuSearchOutputBoundary menuSearchOutputBoundary =
                new MenuSearchPresenter(menuViewModel);

        APIMenuDataAccessObject apiMenuDAO = new APIMenuDataAccessObject(menuService);

        MenuSearchInputBoundary menuSearchInteractor =
                new MenuSearchInteractor(apiMenuDAO, menuSearchOutputBoundary);

        MenuSearchController menuSearchController =
                new MenuSearchController(menuSearchInteractor);
        menuView.setMenuSearchController(menuSearchController);

        return this;
    }

    public AppBuilder addViewMenuUseCase() {
        // Fix: Added missing arguments (viewManagerModel, userDataAccessObject)
        ViewMenuOutputBoundary presenter =
                new ViewMenuPresenter(menuViewModel, viewManagerModel, userDataAccessObject);

        ViewMenuDataAccessInterface menuDAO =
                new APIMenuDataAccessObject(menuService);

        ViewMenuInputBoundary interactor =
                new ViewMenuInteractor(menuDAO, presenter);

        ViewMenuController controller =
                new ViewMenuController(interactor);

        menuView.setViewMenuController(controller);

        if (addressSearchView != null) {
            addressSearchView.setViewMenuController(controller);
        }

        return this;
    }

    public AppBuilder addViewRatingsUseCase() {
        this.viewRatingsViewModel = new ViewRatingsViewModel();

        ViewRatingsDataAccessInterface reviewsDAO = new YelpReviewDataAccessObject();

        ViewRatingsOutputBoundary presenter = new ViewRatingsPresenter(viewRatingsViewModel);

        ViewRatingsInputBoundary interactor = new ViewRatingsInteractor(reviewsDAO, presenter);

        ViewRatingsController controller = new ViewRatingsController(interactor);

        if (menuView != null) {
            menuView.setViewRatingsController(controller);
            menuView.setViewRatingsViewModel(viewRatingsViewModel);
        }

        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("Fork & Code");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }
}
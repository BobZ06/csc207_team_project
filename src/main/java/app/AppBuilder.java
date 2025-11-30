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
import view.*;
import entity.MenuItem;
import interface_adaptor.menu.MenuState;
import interface_adaptor.menu.MenuSearchController;
import interface_adaptor.menu.MenuSearchPresenter;
import use_case.menu_search.MenuSearchInputBoundary;
import use_case.menu_search.MenuSearchInteractor;
import use_case.menu_search.MenuSearchOutputBoundary;

import use_case.view_menu.ViewMenuInputBoundary;
import use_case.view_menu.ViewMenuOutputBoundary;
import use_case.view_menu.ViewMenuDataAccessInterface;
import use_case.view_menu.ViewMenuInteractor;

import data_access.APIMenuDataAccessObject;
import interface_adaptor.menu.ViewMenuController;
import interface_adaptor.menu.ViewMenuPresenter;

import data_access.MenuService;
import data_access.SpoonacularMenuService;


// IMPORTANT!!!!! REMOVE THIS IN THE FINAL THING!!!!!!!
import entity.Restaurant;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // Data Access Object Temp User Data:
    final TempUserDataAccessObject userDataAccessObject = new TempUserDataAccessObject();

    // Data Access Object Temp Star Rate:
    final StarRateDataAccessInterface starRateDataAccessObject = new TempFileStarRateDAO("restaurants.csv");

    // Data Access Object Temp Menu:
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
    // private final MenuService menuService = new MenuServiceForLocalTesting();
    private SearchView searchView;
    private AddressSearchView addressSearchView;
    private RestaurantSearchViewModel restaurantSearchViewModel;


    public AppBuilder() throws FileNotFoundException {
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

        // ---- IMPORTANT REMOVE WHEN DONE (USE CASE 3 & 5 DEMO) ----
        ArrayList<Float> coords = new ArrayList<>();
        coords.add(10f);
        coords.add(10f);
        Restaurant rest = new Restaurant(10f, coords, "Burger", "1042");
        rest.setName("Burger King");
        rest.setAddress("220 Yonge Street");

        User user = new User("Username", "Password");

        starRateDataAccessObject.save(rest.getId(), rest);
        starRateDataAccessObject.setCurrentRestaurantId(rest.getId());
        userDataAccessObject.save(user);
        userDataAccessObject.setCurrentUsername(user.getName());

        MenuState menuState = menuViewModel.getState();
        menuState.setName(rest.getName());
        menuState.setRestaurant(rest.getId());
        menuState.setAddress(rest.getAddress());
        menuState.setRating(rest.getAverageRating());
        menuState.setUsername(user.getName());
        menuViewModel.firePropertyChange();

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

        // Instead of TempMenuDataAccessObject, use the API DAO
        MenuSearchOutputBoundary menuSearchOutputBoundary =
                new MenuSearchPresenter(menuViewModel);

// Wrap the real MenuService in your API DAO
        APIMenuDataAccessObject apiMenuDAO = new APIMenuDataAccessObject(menuService);

        MenuSearchInputBoundary menuSearchInteractor =
                new MenuSearchInteractor(apiMenuDAO, menuSearchOutputBoundary);

        MenuSearchController menuSearchController =
                new MenuSearchController(menuSearchInteractor);
        menuView.setMenuSearchController(menuSearchController);


        // YelpRestaurantSearchService apiCall = new YelpRestaurantSearchService();
        // ArrayList restaurantList = (ArrayList) apiCall.searchRestaurants(40.7128f, -74.0060f, "Burger", 10);

        return this;
    }

    public AppBuilder addViewMenuUseCase() {
        // 1. Presenter
        ViewMenuOutputBoundary presenter =
                new ViewMenuPresenter(menuViewModel);

        // 2. Data Access Object wrapping the MenuService
        ViewMenuDataAccessInterface menuDAO =
                new APIMenuDataAccessObject(menuService);

        // 3. Interactor
        ViewMenuInputBoundary interactor =
                new ViewMenuInteractor(menuDAO, presenter);

        // 4. Controller
        ViewMenuController controller =
                new ViewMenuController(interactor);

        // 5. Connect controller to UI
        menuView.setViewMenuController(controller);

        // 6. TEMP: trigger the use case once, using the restaurant already set in MenuState
        MenuState state = menuViewModel.getState();

        // Only call if we actually have a restaurant name set
        if (state.getName() != null && !state.getName().isEmpty()) {
            System.out.println("[AppBuilder] Calling viewMenu for: " + state.getName());
            controller.viewMenu(
                    state.getName(),        // restaurantName
                    "00000",                // fake zip for now
                    state.getAddress(),     // address from star-rate set-up
                    state.getRating()       // rating from star-rate set-up
            );
        } else {
            System.out.println("[AppBuilder] MenuState has no restaurant name yet.");
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

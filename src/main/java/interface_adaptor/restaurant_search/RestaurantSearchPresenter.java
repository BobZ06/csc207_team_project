package interface_adaptor.restaurant_search;

import use_case.restaurant_search.RestaurantSearchOutputBoundary;
import entity.Restaurant;
import interface_adaptor.ViewManagerModel;

import java.util.List;

public class RestaurantSearchPresenter implements RestaurantSearchOutputBoundary {

    private final RestaurantSearchViewModel viewModel;
    private final ViewManagerModel viewManager;

    public RestaurantSearchPresenter(RestaurantSearchViewModel vm,
                                     ViewManagerModel viewManager) {
        this.viewModel = vm;
        this.viewManager = viewManager;
    }

    @Override
    public void prepareSuccessView(List<Restaurant> restaurants) {
        RestaurantSearchState state = viewModel.getState();
        state.setResults(restaurants);
        state.setError(null);

        viewModel.firePropertyChange();

        viewManager.setState("SearchView");
        viewManager.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        RestaurantSearchState state = viewModel.getState();
        state.setError(errorMessage);
        state.setResults(null);

        viewModel.firePropertyChange();
    }
}

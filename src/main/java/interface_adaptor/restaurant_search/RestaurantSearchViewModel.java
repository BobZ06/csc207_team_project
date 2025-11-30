package interface_adaptor.restaurant_search;

import interface_adaptor.ViewModel;

public class RestaurantSearchViewModel extends ViewModel<RestaurantSearchState> {
    public RestaurantSearchViewModel() {
        super("SearchView");
        setState(new RestaurantSearchState());
    }
}

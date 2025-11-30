package interface_adaptor.Menu;

import use_case.view_menu.ViewMenuOutputBoundary;
import use_case.view_menu.ViewMenuOutputData;

public class ViewMenuPresenter implements ViewMenuOutputBoundary {

    private final MenuViewModel menuViewModel;

    public ViewMenuPresenter(MenuViewModel menuViewModel) {
        this.menuViewModel = menuViewModel;
    }

    @Override
    public void prepareSuccessView(ViewMenuOutputData outputData) {
        MenuState state = menuViewModel.getState();

        state.setName(outputData.getRestaurantName());
        state.setAddress(outputData.getRestaurantAddress());
        state.setRating((float) outputData.getRestaurantRating());

        // here:
        state.setMenuData(outputData.getMenuData());
        state.setMenuError(null);

        menuViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        MenuState state = menuViewModel.getState();

        state.setMenuData(null);
        state.setMenuError(errorMessage);

        menuViewModel.firePropertyChange();
    }
}
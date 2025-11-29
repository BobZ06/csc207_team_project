package interface_adaptor.Menu;

import view_menu.ViewMenuOutputBoundary;
import view_menu.ViewMenuOutputData;


public class ViewMenuPresenter implements ViewMenuOutputBoundary {

    private final MenuViewModel menuViewModel;

    public ViewMenuPresenter(MenuViewModel menuViewModel) {
        this.menuViewModel = menuViewModel;
    }

    @Override
    public void prepareSuccessView(ViewMenuOutputData outputData) {
        MenuState state = menuViewModel.getState();

        // basic restaurant info
        state.setName(outputData.getRestaurantName());
        state.setAddress(outputData.getRestaurantAddress());
        state.setRating((float) outputData.getRestaurantRating());

        // store JSON menu (for the view to render later)
        state.setMenuData(outputData.getMenuData());

        // clear errors
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
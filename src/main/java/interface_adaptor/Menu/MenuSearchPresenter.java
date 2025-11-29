package interface_adaptor.Menu;

import menu_search.MenuSearchOutputBoundary;
import menu_search.MenuSearchOutputData;
import entity.MenuItem;
import java.util.ArrayList;

public class MenuSearchPresenter implements MenuSearchOutputBoundary {
    private final MenuViewModel menuViewModel;

    public MenuSearchPresenter(MenuViewModel menuViewModel) {
        this.menuViewModel = menuViewModel;
    }

    @Override
    public void prepareSuccessView(MenuSearchOutputData outputData) {
        MenuState state = menuViewModel.getState();
        state.setMenuList(new ArrayList<MenuItem>(outputData.getResults()));
        menuViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        MenuState state = menuViewModel.getState();
        state.setMenuList(new ArrayList<>());
        System.out.println(errorMessage);
        menuViewModel.firePropertyChange();
    }
}

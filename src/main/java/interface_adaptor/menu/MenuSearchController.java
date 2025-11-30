package interface_adaptor.menu;

import use_case.menu_search.MenuSearchInputBoundary;
import use_case.menu_search.MenuSearchInputData;

public class MenuSearchController {
    private final MenuSearchInputBoundary inputBoundary;

    public MenuSearchController(MenuSearchInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void execute(String restaurantID, String query) {
        MenuSearchInputData inputData = new MenuSearchInputData(restaurantID, query);
        inputBoundary.execute(inputData);
    }
}

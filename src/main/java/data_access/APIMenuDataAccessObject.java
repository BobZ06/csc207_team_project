package data_access;

import org.json.JSONObject;
import use_case.view_menu.ViewMenuDataAccessInterface;


public class APIMenuDataAccessObject implements ViewMenuDataAccessInterface {

    private final MenuService menuService;

    public APIMenuDataAccessObject(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public JSONObject getRestaurantMenu(String restaurantName, String zipCode) throws Exception {
        // This calls whatever concrete MenuService implementation you pass in
        // (e.g., SpoonacularMenuService, a local testing stub, etc.)
        return menuService.getRestaurantMenu(restaurantName, zipCode);
    }
}
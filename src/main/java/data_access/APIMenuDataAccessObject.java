package data_access;

import org.json.JSONObject;
import view_menu.ViewMenuDataAccessInterface;

public class APIMenuDataAccessObject implements ViewMenuDataAccessInterface {

    private final MenuService menuService;

    public APIMenuDataAccessObject(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public JSONObject getRestaurantMenu(String restaurantName, String zipCode) throws Exception {
        return menuService.getRestaurantMenu(restaurantName, zipCode);
    }
}
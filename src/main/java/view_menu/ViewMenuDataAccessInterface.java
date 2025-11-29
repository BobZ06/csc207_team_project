package view_menu;

import org.json.JSONObject;

public interface ViewMenuDataAccessInterface {
    JSONObject getRestaurantMenu(String restaurantName, String zipCode) throws Exception;
}
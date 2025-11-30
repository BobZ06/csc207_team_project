package use_case.view_menu;

import org.json.JSONObject;


public interface ViewMenuDataAccessInterface {

    /**
     * Fetch the menu for a given restaurant.
     *
     * @param restaurantName name of the restaurant
     * @param zipCode        zip/postal code for context
     * @return menu data as a JSONObject
     * @throws Exception if the menu cannot be retrieved
     */
    JSONObject getRestaurantMenu(String restaurantName, String zipCode) throws Exception;
}
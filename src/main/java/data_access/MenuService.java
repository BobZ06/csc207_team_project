package data_access;

import org.json.JSONObject;
import java.util.List;

/**
 * Interface for menu-related services.
 */
public interface MenuService {

    /**
     * Get the complete menu for a restaurant.
     * @param restaurantName name of the restaurant
     * @param zipCode zip code (used for context, though Spoonacular global search might ignore it)
     * @return menu data as JSONObject containing menu items
     * @throws MenuNotFoundException if menu is not found or API fails
     */
    JSONObject getRestaurantMenu(String restaurantName, String zipCode) throws MenuNotFoundException;

    /**
     * Search for specific items within a menu.
     * @param menuData the menu data to search in
     * @param searchTerm the item to search for (e.g., "burger", "pizza")
     * @return list of matching menu items as JSONObjects
     * @throws MenuNotFoundException if search logic fails
     */
    List<JSONObject> searchMenuItems(JSONObject menuData, String searchTerm) throws MenuNotFoundException;

    /**
     * Get menu organized by categories.
     * @param menuData raw menu data
     * @return organized menu by categories (Main Menu, etc.)
     */
    JSONObject parseMenuByCategories(JSONObject menuData);

    /**
     * Custom Exception thrown when menu operations fail.
     */
    class MenuNotFoundException extends Exception {
        public MenuNotFoundException(String message) {
            super("Menu not found: " + message);
        }
    }
}
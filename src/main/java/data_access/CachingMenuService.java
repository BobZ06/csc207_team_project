package data_access;

import org.json.JSONObject;
import java.util.*;

/**
 * Caching Wrapper for MenuService.
 */
public class CachingMenuService implements MenuService {

    // Tracks how many times we actually called the API
    private int callsMade = 0;

    private final MenuService service;

    // The memory cache: stores "restaurant_zip" -> Menu JSON
    private final Map<String, JSONObject> menuCache;

    /**
     * Constructor accepts ANY MenuService implementation.
     * @param service The real service to wrap (SpoonacularMenuService)
     */
    public CachingMenuService(MenuService service) {
        this.service = service;
        this.menuCache = new HashMap<>();
    }

    @Override
    public JSONObject getRestaurantMenu(String restaurantName, String zipCode)
            throws MenuNotFoundException {

        // 1. Create a unique cache key
        String cacheKey = (restaurantName + "_" + zipCode).toLowerCase().trim();

        // 2. Check if we have seen this request before
        if (menuCache.containsKey(cacheKey)) {
            System.out.println("Returning saved menu for: " + restaurantName);
            return menuCache.get(cacheKey);
        }

        // 3. Cache Miss: We must call the real API
        System.out.println("Calling real API for: " + restaurantName);
        callsMade++;
        JSONObject menu = service.getRestaurantMenu(restaurantName, zipCode);

        // 4. Save the result for next time
        menuCache.put(cacheKey, menu);

        return menu;
    }

    @Override
    public List<JSONObject> searchMenuItems(JSONObject menuData, String searchTerm)
            throws MenuNotFoundException {
        return service.searchMenuItems(menuData, searchTerm);
    }

    @Override
    public JSONObject parseMenuByCategories(JSONObject menuData) {
        return service.parseMenuByCategories(menuData);
    }

    /**
     * Get the number of times the API was actually called.
     */
    public int getCallsMade() {
        return callsMade;
    }

    /**
     * Clear all cached menus.
     */
    public void clearCache() {
        menuCache.clear();
    }
}
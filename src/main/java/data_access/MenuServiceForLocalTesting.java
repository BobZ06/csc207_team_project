package data_access;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Mock implementation of MenuService for local testing.
 */
public class MenuServiceForLocalTesting implements MenuService {

    @Override
    public JSONObject getRestaurantMenu(String restaurantName, String zipCode) {
        JSONObject mockResponse = new JSONObject();
        JSONArray menuItems = new JSONArray();

        // Create mock items
        menuItems.put(createMockItem(101, "Classic Burger", 12.99));
        menuItems.put(createMockItem(102, "Cheese Fries", 5.49));
        menuItems.put(createMockItem(103, "Chocolate Shake", 4.99));
        menuItems.put(createMockItem(104, "Caesar Salad", 9.50));

        mockResponse.put("menuItems", menuItems);
        mockResponse.put("totalMenuItems", 4);

        return mockResponse;
    }

    private JSONObject createMockItem(int id, String title, double price) {
        JSONObject item = new JSONObject();
        item.put("id", id);
        item.put("title", title);
        item.put("price", price);
        item.put("currency", "USD");
        item.put("readablePrice", "$" + price);
        item.put("restaurantChain", "Mock Restaurant");
        return item;
    }

    @Override
    public List<JSONObject> searchMenuItems(JSONObject menuData, String searchTerm) {
        List<JSONObject> results = new ArrayList<>();
        if (!menuData.has("menuItems")) return results;

        JSONArray items = menuData.getJSONArray("menuItems");
        String lowerTerm = searchTerm.toLowerCase();

        for(int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            if(item.getString("title").toLowerCase().contains(lowerTerm)) {
                results.add(item);
            }
        }
        return results;
    }

    @Override
    public JSONObject parseMenuByCategories(JSONObject menuData) {
        JSONObject categorized = new JSONObject();
        if (menuData.has("menuItems")) {
            categorized.put("Test Category", menuData.getJSONArray("menuItems"));
        }
        return categorized;
    }
}
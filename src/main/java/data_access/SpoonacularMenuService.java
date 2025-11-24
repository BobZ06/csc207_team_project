package data_access;

import config.ConfigManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * MenuService implementation using Spoonacular API.
 */
public class SpoonacularMenuService implements MenuService {
    private final OkHttpClient client;
    private final String apiHost;
    private final String apiKey;

    public SpoonacularMenuService() {
        this.client = new OkHttpClient();
        ConfigManager.loadConfig();
        this.apiHost = ConfigManager.getSpoonacularHost();
        this.apiKey = ConfigManager.getSpoonacularApiKey();

        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("WARNING: Spoonacular API key not configured");
        }
    }

    @Override
    public JSONObject getRestaurantMenu(String restaurantName, String zipCode) throws MenuNotFoundException {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new MenuNotFoundException("API key not configured");
        }

        try {
            // Spoonacular Search Endpoint
            String url = String.format(
                    "https://%s/food/menuItems/search?query=%s&number=20&apiKey=%s",
                    apiHost, restaurantName, apiKey
            );

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new MenuNotFoundException("API returned status: " + response.code());
            }

            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseBody);

            // Process the response to add prices
            if (jsonResponse.has("menuItems")) {
                JSONArray items = jsonResponse.getJSONArray("menuItems");

                // If list is empty, treat as not found
                if (items.length() == 0) {
                    throw new MenuNotFoundException(restaurantName);
                }

                for (int i = 0; i < items.length(); i++) {
                    JSONObject item = items.getJSONObject(i);
                    enrichItemWithPrice(item);
                }
                return jsonResponse;
            } else {
                throw new MenuNotFoundException(restaurantName);
            }

        } catch (IOException e) {
            throw new MenuNotFoundException("API call failed: " + e.getMessage());
        }
    }

    @Override
    public List<JSONObject> searchMenuItems(JSONObject menuData, String searchTerm) throws MenuNotFoundException {
        List<JSONObject> matchingItems = new ArrayList<>();

        try {
            if (!menuData.has("menuItems")) {
                return matchingItems;
            }

            JSONArray items = menuData.getJSONArray("menuItems");
            String lowerSearchTerm = searchTerm.toLowerCase();

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                String title = item.optString("title", "").toLowerCase();

                if (title.contains(lowerSearchTerm)) {
                    matchingItems.add(item);
                }
            }
        } catch (Exception e) {
            throw new MenuNotFoundException("Error searching menu: " + e.getMessage());
        }

        return matchingItems;
    }

    @Override
    public JSONObject parseMenuByCategories(JSONObject menuData) {
        JSONObject organizedMenu = new JSONObject();

        // Spoonacular returns a flat list, so we group everything under "Main Menu"
        // to maintain the structure expected by the View
        if (menuData.has("menuItems")) {
            organizedMenu.put("Main Menu", menuData.getJSONArray("menuItems"));
        }

        return organizedMenu;
    }

    private void enrichItemWithPrice(JSONObject item) {
        // If API provides price, use it; otherwise generate one
        if (item.has("price") && !item.isNull("price")) {
            return;
        }

        int id = item.optInt("id", 0);
        Random rand = new Random(id);

        // Generate price between $5.00 and $25.00
        double fakePrice = 5.0 + (20.0 * rand.nextDouble());

        // Round to 2 decimal places
        fakePrice = Math.round(fakePrice * 100.0) / 100.0;

        item.put("price", fakePrice);
        item.put("currency", "USD");
        item.put("readablePrice", "$" + String.format("%.2f", fakePrice));
    }
}
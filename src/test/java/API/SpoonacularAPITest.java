package API;

import data_access.MenuService;
import data_access.SpoonacularMenuService;
import data_access.MenuService.MenuNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class SpoonacularAPITest {

    public static void main(String[] args) {
        System.out.println("\n=== Spoonacular API Connectivity Test ===\n");

        // 1. Initialize the service
        MenuService service = new SpoonacularMenuService();

        // 2. Define test parameters
        String restaurantName = "Burger King";
        String dummyZip = "10001";

        testGetMenu(service, restaurantName, dummyZip);
    }

    private static void testGetMenu(MenuService service, String name, String zip) {
        System.out.println("Requesting Menu for: " + name + "...");

        try {
            long start = System.currentTimeMillis();

            // Call the API
            JSONObject menuData = service.getRestaurantMenu(name, zip);

            long end = System.currentTimeMillis();
            System.out.println("API Call Successful (Time: " + (end - start) + "ms)");

            if (menuData.has("menuItems")) {
                JSONArray items = menuData.getJSONArray("menuItems");
                System.out.println("Found " + items.length() + " menu items.\n");

                System.out.println("SAMPLE MENU ITEMS & PRICES:");
                System.out.println("------------------------------------------------");

                // Print top 5 items to verify Price generation
                for (int i = 0; i < Math.min(items.length(), 5); i++) {
                    JSONObject item = items.getJSONObject(i);
                    String title = item.optString("title", "Unknown Item");

                    // This checks if our enrichItemWithPrice logic worked
                    String price = item.optString("readablePrice", "N/A");

                    System.out.printf("%-35s | %s\n", title, price);
                }
                System.out.println("------------------------------------------------");

                // Test Search functionality within the menu
                testLocalSearch(service, menuData, "Bacon");

            } else {
                System.err.println("Error: Response contains no 'menuItems'.");
            }

        } catch (MenuNotFoundException e) {
            System.err.println("API Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testLocalSearch(MenuService service, JSONObject menuData, String query) {
        System.out.println("\nTesting Local Search for '" + query + "'...");
        try {
            List<JSONObject> results = service.searchMenuItems(menuData, query);
            System.out.println("Found " + results.size() + " matches.");
            if (!results.isEmpty()) {
                System.out.println("First match: " + results.get(0).getString("title"));
            }
        } catch (MenuNotFoundException e) {
            System.err.println("Search failed: " + e.getMessage());
        }
    }
}
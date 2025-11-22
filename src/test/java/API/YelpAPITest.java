package API;

import data_access.YelpRestaurantSearchService;
import data_access.RestaurantSearchService;
import entity.Restaurant;

import java.util.List;

public class YelpAPITest {

    public static void main(String[] args) {
        System.out.println("\n=== Yelp Official API Test ===\n");

        // Test 1: Search Restaurants in Toronto
        testSearch(43.6629f, -79.3957f, "ramen");

        // Test 2: Search Restaurants in New York
        testSearch(40.7580f, -73.9855f, "pizza");
    }

    private static void testSearch(float lat, float lng, String term) {
        System.out.println("📍 Searching for '" + term + "' at " + lat + ", " + lng + "...");

        try {
            RestaurantSearchService service = new YelpRestaurantSearchService();
            long start = System.currentTimeMillis();

            List<Restaurant> results = service.searchRestaurants(lat, lng, term, 3);

            System.out.println("   Found " + results.size() + " restaurants:\n");

            for (Restaurant r : results) {
                System.out.println("   🍽️ " + r.getName());
                System.out.println("      Rating: " + r.getAverageRating() + " ⭐");
                System.out.println("      Type:   " + r.getFoodType());
                System.out.println("      Addr:   " + r.getAddress());
                System.out.println("      -----------------------------");
            }
            System.out.println("\n");

        } catch (Exception e) {
            System.err.println("Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
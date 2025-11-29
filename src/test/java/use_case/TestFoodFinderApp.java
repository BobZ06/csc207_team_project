package entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import data_access.LocationService;
import data_access.RestaurantSearchService;

class FoodFinderAppTest {

    @Test
    void testCreateRestaurantList_withUserLocation() throws Exception {

        // 1. Create user
        User user = new User("testUser", "password123");

        // 2. Set user coordinates using address: King College Cir, Toronto
        GetUserLocation locationGetter =
                new GetUserLocation.GetUserLocationByAddress("King College Cir, Toronto");

        try {
            user.setCoordsFromLocation(locationGetter);
        } catch (LocationService.LocationNotFoundException e) {
            fail("Failed to set user location: " + e.getMessage());
        }

        System.out.println("=== User Coordinates Set ===");
        System.out.println("Lat: " + user.getCoords()[0]);
        System.out.println("Lng: " + user.getCoords()[1]);

        // 3. Create app and assign current user
        FoodFinderApp app = new FoodFinderApp();
        app.setCurrentUser(user);

        // 4. Create restaurant list via Yelp service
        try {
            app.createRestaurantList();
        } catch (RestaurantSearchService.RestaurantSearchException e) {
            fail("Restaurant search failed: " + e.getMessage());
        }

        // 5. Get restaurants
        List<Restaurant> restaurants = app.getFullRestaurantlist();

        // Basic assert
        assertNotNull(restaurants, "Restaurant list should not be null");

        // Print to console
        System.out.println("\n=== Restaurants fetched ===");
        System.out.println("Total restaurants: " + restaurants.size());

        for (Restaurant r : restaurants) {
            System.out.println(r);
        }
    }
}

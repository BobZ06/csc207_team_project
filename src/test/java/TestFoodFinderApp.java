package entity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import data_access.RestaurantSearchService;

class FoodFinderAppTest {

    @Test
    void testCreateRestaurantList_printsRestaurants() throws RestaurantSearchService.RestaurantSearchException {
        FoodFinderApp app = new FoodFinderApp();

        // call the method you want to test
        app.createRestaurantList();

        List<Restaurant> restaurants = app.getFullRestaurantlist();

        // basic sanity check so the test actually asserts something
        assertNotNull(restaurants, "Restaurant list should not be null");

        // print to console so you can visually inspect results
        System.out.println("=== Restaurants fetched ===");
        System.out.println("Total restaurants: " + restaurants.size());

        for (Restaurant r : restaurants) {
            // this will call Restaurant.toString()
            System.out.println(r);
        }
    }
}

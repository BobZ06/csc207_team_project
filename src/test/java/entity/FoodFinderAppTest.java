package entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FoodFinderAppTest {

    @Test
    void testGetDistanceAndSortByClosest() {
        System.out.println("\n=== FoodFinderAppTest: testGetDistanceAndSortByClosest ===");

        // User at origin
        User user = new User("testUser", "pw");
        user.setCoords(0f, 0f);

        FoodFinderApp app = new FoodFinderApp();
        app.setCurrentUser(user);

        // Restaurants
        Restaurant restA = new Restaurant(
                "A", "A-Rest", "addrA", "zipA", 2.0f,
                Arrays.asList(1.0f, 0.0f), "TypeA"
        );
        Restaurant restB = new Restaurant(
                "B", "B-Rest", "addrB", "zipB", 3.0f,
                Arrays.asList(0.0f, 2.0f), "TypeB"
        );

        List<Restaurant> list = new ArrayList<>(Arrays.asList(restA, restB));
        app.setRestaurantList(list);

        double distA = app.getDistance(restA);
        double distB = app.getDistance(restB);

        System.out.println("Distance to A: " + distA);
        System.out.println("Distance to B: " + distB);

        assertTrue(distA < distB);

        List<Restaurant> sorted = app.getSortByClosest();
        System.out.println("Sorted by closest: ");
        for (Restaurant r : sorted) {
            System.out.println(" -> " + r.getId());
        }

        assertEquals("A", sorted.get(0).getId());
    }

    @Test
    void testSortByCheapest() {
        System.out.println("\n=== FoodFinderAppTest: testSortByCheapest ===");

        User user = new User("testUser", "pw");
        user.setCoords(0f, 0f);

        FoodFinderApp app = new FoodFinderApp();
        app.setCurrentUser(user);

        Restaurant cheap = new Restaurant("cheap", "Cheap", "addr", "zip", 1.0f, Arrays.asList(0f, 0f), "Type");
        Restaurant mid = new Restaurant("mid", "Mid", "addr", "zip", 2.0f, Arrays.asList(0f, 0f), "Type");
        Restaurant exp = new Restaurant("exp", "Expensive", "addr", "zip", 3.0f, Arrays.asList(0f, 0f), "Type");

        app.setRestaurantList(Arrays.asList(exp, cheap, mid));

        List<Restaurant> sorted = app.getSortByCheapest();

        System.out.println("Sorted by cheapest:");
        for (Restaurant r : sorted) {
            System.out.println(" -> " + r.getId() + " ($" + r.getPriceRange() + ")");
        }

        assertEquals("cheap", sorted.get(0).getId());
    }
}

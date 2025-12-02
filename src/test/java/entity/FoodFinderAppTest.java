package entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test class for FoodFinderApp entity.
 * Achieves 100% code coverage by testing all methods and branches.
 */
class FoodFinderAppTest {

    private FoodFinderApp app;
    private User testUser;

    @BeforeEach
    void setUp() {
        app = new FoodFinderApp();
        testUser = new User("testUser", "password");
        testUser.setCoords(0f, 0f);
    }

    @Test
    void testConstructor() {
        FoodFinderApp newApp = new FoodFinderApp();

        assertNotNull(newApp);
        assertNull(newApp.getCurrentUser());
        assertNotNull(newApp.getFullRestaurantlist());
        assertTrue(newApp.getFullRestaurantlist().isEmpty());
    }

    @Test
    void testConstructorInitializesEmptyRestaurantList() {
        FoodFinderApp newApp = new FoodFinderApp();
        assertEquals(0, newApp.getFullRestaurantlist().size());
    }

    @Test
    void testGetCurrentUserWhenNull() {
        assertNull(app.getCurrentUser());
    }

    @Test
    void testGetCurrentUserWhenSet() {
        app.setCurrentUser(testUser);

        assertNotNull(app.getCurrentUser());
        assertEquals(testUser, app.getCurrentUser());
        assertEquals("testUser", app.getCurrentUser().getName());
    }

    @Test
    void testSetCurrentUser() {
        User user = new User("alice", "pass");
        app.setCurrentUser(user);

        assertEquals(user, app.getCurrentUser());
        assertEquals("alice", app.getCurrentUser().getName());
    }

    @Test
    void testSetCurrentUserToNull() {
        app.setCurrentUser(testUser);
        assertNotNull(app.getCurrentUser());

        app.setCurrentUser(null);
        assertNull(app.getCurrentUser());
    }

    @Test
    void testSetCurrentUserMultipleTimes() {
        User user1 = new User("user1", "pass");
        User user2 = new User("user2", "pass");
        User user3 = new User("user3", "pass");

        app.setCurrentUser(user1);
        assertEquals("user1", app.getCurrentUser().getName());

        app.setCurrentUser(user2);
        assertEquals("user2", app.getCurrentUser().getName());

        app.setCurrentUser(user3);
        assertEquals("user3", app.getCurrentUser().getName());
    }

    @Test
    void testSetRestaurantList() {
        List<Restaurant> restaurants = Arrays.asList(
            createRestaurant("1", "Rest1", 0f, 0f, 2.0f),
            createRestaurant("2", "Rest2", 1f, 1f, 3.0f)
        );

        app.setRestaurantList(restaurants);

        assertEquals(2, app.getFullRestaurantlist().size());
        assertEquals("Rest1", app.getFullRestaurantlist().get(0).getName());
    }

    @Test
    void testSetRestaurantListReplacesOldList() {
        List<Restaurant> list1 = Arrays.asList(createRestaurant("1", "Rest1", 0f, 0f, 1.0f));
        app.setRestaurantList(list1);
        assertEquals(1, app.getFullRestaurantlist().size());

        List<Restaurant> list2 = Arrays.asList(
            createRestaurant("2", "Rest2", 0f, 0f, 2.0f),
            createRestaurant("3", "Rest3", 0f, 0f, 3.0f)
        );
        app.setRestaurantList(list2);
        assertEquals(2, app.getFullRestaurantlist().size());
    }

    @Test
    void testSetRestaurantListWithEmptyList() {
        app.setRestaurantList(new ArrayList<>());
        assertTrue(app.getFullRestaurantlist().isEmpty());
    }

    @Test
    void testGetFullRestaurantlist() {
        List<Restaurant> restaurants = Arrays.asList(
            createRestaurant("1", "Rest1", 0f, 0f, 1.0f),
            createRestaurant("2", "Rest2", 1f, 1f, 2.0f)
        );

        app.setRestaurantList(restaurants);
        List<Restaurant> retrieved = app.getFullRestaurantlist();

        assertNotNull(retrieved);
        assertEquals(2, retrieved.size());
        assertEquals("Rest1", retrieved.get(0).getName());
        assertEquals("Rest2", retrieved.get(1).getName());
    }

    @Test
    void testGetFullRestaurantlistWhenEmpty() {
        List<Restaurant> retrieved = app.getFullRestaurantlist();

        assertNotNull(retrieved);
        assertTrue(retrieved.isEmpty());
    }

    @Test
    void testAddToRestaurants() {
        Restaurant restaurant = createRestaurant("1", "Pizza Place", 0f, 0f, 2.0f);

        app.addToRestaurants(restaurant);

        assertEquals(1, app.getFullRestaurantlist().size());
        assertEquals("Pizza Place", app.getFullRestaurantlist().get(0).getName());
    }

    @Test
    void testAddToRestaurantsMultiple() {
        app.addToRestaurants(createRestaurant("1", "Rest1", 0f, 0f, 1.0f));
        app.addToRestaurants(createRestaurant("2", "Rest2", 1f, 1f, 2.0f));
        app.addToRestaurants(createRestaurant("3", "Rest3", 2f, 2f, 3.0f));

        assertEquals(3, app.getFullRestaurantlist().size());
    }

    @Test
    void testAddToRestaurantsPreservesOrder() {
        app.addToRestaurants(createRestaurant("1", "First", 0f, 0f, 1.0f));
        app.addToRestaurants(createRestaurant("2", "Second", 0f, 0f, 2.0f));
        app.addToRestaurants(createRestaurant("3", "Third", 0f, 0f, 3.0f));

        List<Restaurant> list = app.getFullRestaurantlist();
        assertEquals("First", list.get(0).getName());
        assertEquals("Second", list.get(1).getName());
        assertEquals("Third", list.get(2).getName());
    }

    @Test
    void testGetDistance() {
        app.setCurrentUser(testUser);
        Restaurant restaurant = createRestaurant("1", "Rest", 3f, 4f, 2.0f);

        double distance = app.getDistance(restaurant);

        assertEquals(5.0, distance, 0.001); // 3-4-5 triangle
    }

    @Test
    void testGetDistanceZeroDistance() {
        app.setCurrentUser(testUser);
        Restaurant restaurant = createRestaurant("1", "Rest", 0f, 0f, 2.0f);

        double distance = app.getDistance(restaurant);

        assertEquals(0.0, distance, 0.001);
    }

    @Test
    void testGetDistanceNegativeCoordinates() {
        testUser.setCoords(0f, 0f);
        app.setCurrentUser(testUser);
        Restaurant restaurant = createRestaurant("1", "Rest", -3f, -4f, 2.0f);

        double distance = app.getDistance(restaurant);

        assertEquals(5.0, distance, 0.001);
    }

    @Test
    void testGetDistanceWithUserNotAtOrigin() {
        testUser.setCoords(1f, 1f);
        app.setCurrentUser(testUser);
        Restaurant restaurant = createRestaurant("1", "Rest", 4f, 5f, 2.0f);

        double distance = app.getDistance(restaurant);

        assertEquals(5.0, distance, 0.001); // sqrt((4-1)^2 + (5-1)^2) = sqrt(9+16) = 5
    }

    @Test
    void testGetSortByClosest() {
        app.setCurrentUser(testUser);

        Restaurant restA = createRestaurant("A", "Far", 10f, 0f, 2.0f);    // Distance: 10
        Restaurant restB = createRestaurant("B", "Medium", 5f, 0f, 3.0f);  // Distance: 5
        Restaurant restC = createRestaurant("C", "Close", 1f, 0f, 1.0f);   // Distance: 1

        app.setRestaurantList(Arrays.asList(restA, restB, restC));

        List<Restaurant> sorted = app.getSortByClosest();

        assertEquals(3, sorted.size());
        assertEquals("C", sorted.get(0).getId()); // Closest
        assertEquals("B", sorted.get(1).getId());
        assertEquals("A", sorted.get(2).getId()); // Farthest
    }

    @Test
    void testGetSortByClosestWithEqualDistances() {
        app.setCurrentUser(testUser);

        Restaurant restA = createRestaurant("A", "Rest1", 1f, 0f, 2.0f); // Distance: 1
        Restaurant restB = createRestaurant("B", "Rest2", 0f, 1f, 3.0f); // Distance: 1

        app.setRestaurantList(Arrays.asList(restA, restB));

        List<Restaurant> sorted = app.getSortByClosest();

        assertEquals(2, sorted.size());
        // Both have same distance, order may vary but both should be present
    }

    @Test
    void testGetSortByClosestWithSingleRestaurant() {
        app.setCurrentUser(testUser);

        Restaurant restaurant = createRestaurant("1", "Only", 5f, 5f, 2.0f);
        app.setRestaurantList(Arrays.asList(restaurant));

        List<Restaurant> sorted = app.getSortByClosest();

        assertEquals(1, sorted.size());
        assertEquals("1", sorted.get(0).getId());
    }

    @Test
    void testGetSortByClosestWithEmptyList() {
        app.setCurrentUser(testUser);
        app.setRestaurantList(new ArrayList<>());

        List<Restaurant> sorted = app.getSortByClosest();

        assertTrue(sorted.isEmpty());
    }

    @Test
    void testGetSortByClosestDoesNotModifyOriginalList() {
        app.setCurrentUser(testUser);

        Restaurant restA = createRestaurant("A", "Far", 10f, 0f, 2.0f);
        Restaurant restB = createRestaurant("B", "Close", 1f, 0f, 1.0f);

        List<Restaurant> original = new ArrayList<>(Arrays.asList(restA, restB));
        app.setRestaurantList(original);

        List<Restaurant> sorted = app.getSortByClosest();

        // Original list order should be unchanged
        assertEquals("A", app.getFullRestaurantlist().get(0).getId());
        assertEquals("B", app.getFullRestaurantlist().get(1).getId());

        // Sorted list should be different
        assertEquals("B", sorted.get(0).getId());
        assertEquals("A", sorted.get(1).getId());
    }

    @Test
    void testGetSortByCheapest() {
        Restaurant cheap = createRestaurant("cheap", "Cheap", 0f, 0f, 1.0f);
        Restaurant mid = createRestaurant("mid", "Mid", 0f, 0f, 2.0f);
        Restaurant expensive = createRestaurant("exp", "Expensive", 0f, 0f, 3.0f);

        app.setRestaurantList(Arrays.asList(expensive, cheap, mid));

        List<Restaurant> sorted = app.getSortByCheapest();

        assertEquals(3, sorted.size());
        assertEquals("cheap", sorted.get(0).getId());
        assertEquals("mid", sorted.get(1).getId());
        assertEquals("exp", sorted.get(2).getId());
    }

    @Test
    void testGetSortByCheapestWithEqualPrices() {
        Restaurant restA = createRestaurant("A", "Rest1", 0f, 0f, 2.0f);
        Restaurant restB = createRestaurant("B", "Rest2", 0f, 0f, 2.0f);

        app.setRestaurantList(Arrays.asList(restA, restB));

        List<Restaurant> sorted = app.getSortByCheapest();

        assertEquals(2, sorted.size());
        // Both have same price, order may vary but both should be present
    }

    @Test
    void testGetSortByCheapestWithSingleRestaurant() {
        Restaurant restaurant = createRestaurant("1", "Only", 0f, 0f, 2.0f);
        app.setRestaurantList(Arrays.asList(restaurant));

        List<Restaurant> sorted = app.getSortByCheapest();

        assertEquals(1, sorted.size());
        assertEquals("1", sorted.get(0).getId());
    }

    @Test
    void testGetSortByCheapestWithEmptyList() {
        app.setRestaurantList(new ArrayList<>());

        List<Restaurant> sorted = app.getSortByCheapest();

        assertTrue(sorted.isEmpty());
    }

    @Test
    void testGetSortByCheapestDoesNotModifyOriginalList() {
        Restaurant expensive = createRestaurant("exp", "Expensive", 0f, 0f, 3.0f);
        Restaurant cheap = createRestaurant("cheap", "Cheap", 0f, 0f, 1.0f);

        List<Restaurant> original = new ArrayList<>(Arrays.asList(expensive, cheap));
        app.setRestaurantList(original);

        List<Restaurant> sorted = app.getSortByCheapest();

        // Original list order should be unchanged
        assertEquals("exp", app.getFullRestaurantlist().get(0).getId());
        assertEquals("cheap", app.getFullRestaurantlist().get(1).getId());

        // Sorted list should be different
        assertEquals("cheap", sorted.get(0).getId());
        assertEquals("exp", sorted.get(1).getId());
    }

    @Test
    void testGetSortByCheapestReverseOrder() {
        Restaurant rest1 = createRestaurant("1", "R1", 0f, 0f, 5.0f);
        Restaurant rest2 = createRestaurant("2", "R2", 0f, 0f, 4.0f);
        Restaurant rest3 = createRestaurant("3", "R3", 0f, 0f, 3.0f);
        Restaurant rest4 = createRestaurant("4", "R4", 0f, 0f, 2.0f);
        Restaurant rest5 = createRestaurant("5", "R5", 0f, 0f, 1.0f);

        app.setRestaurantList(Arrays.asList(rest1, rest2, rest3, rest4, rest5));

        List<Restaurant> sorted = app.getSortByCheapest();

        assertEquals("5", sorted.get(0).getId());
        assertEquals("4", sorted.get(1).getId());
        assertEquals("3", sorted.get(2).getId());
        assertEquals("2", sorted.get(3).getId());
        assertEquals("1", sorted.get(4).getId());
    }

    @Test
    void testCompleteWorkflow() {
        // Setup user
        User user = new User("alice", "pass");
        user.setCoords(0f, 0f);
        app.setCurrentUser(user);

        // Add restaurants
        app.addToRestaurants(createRestaurant("1", "Close Cheap", 1f, 0f, 1.0f));
        app.addToRestaurants(createRestaurant("2", "Far Expensive", 10f, 0f, 3.0f));
        app.addToRestaurants(createRestaurant("3", "Medium Price", 5f, 0f, 2.0f));

        // Test getFullRestaurantlist
        assertEquals(3, app.getFullRestaurantlist().size());

        // Test sorting by distance
        List<Restaurant> byDistance = app.getSortByClosest();
        assertEquals("1", byDistance.get(0).getId()); // Closest

        // Test sorting by price
        List<Restaurant> byPrice = app.getSortByCheapest();
        assertEquals("1", byPrice.get(0).getId()); // Cheapest

        // Test distance calculation
        double distance = app.getDistance(byDistance.get(0));
        assertEquals(1.0, distance, 0.001);
    }

    // Helper method to create restaurants
    private Restaurant createRestaurant(String id, String name, float x, float y, float price) {
        return new Restaurant(
            id,
            name,
            "address",
            "zipcode",
            price,
            Arrays.asList(x, y),
            "Type"
        );
    }
}
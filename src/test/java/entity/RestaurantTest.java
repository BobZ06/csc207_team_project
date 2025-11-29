package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    private Restaurant restaurant;
    private List<Float> coords;

    @BeforeEach
    void setUp() {
        coords = new ArrayList<>();
        coords.add(43.66f);
        coords.add(-79.39f);

        restaurant = new Restaurant(
                "rest-001",
                "Test Restaurant",
                "123 Test St",
                "M5S 2E6",
                2.0f,
                coords,
                "Chinese"
        );
    }

    @Test
    void testFullConstructorAndGetters() {
        assertEquals("rest-001", restaurant.getId());
        assertEquals("Test Restaurant", restaurant.getName());
        assertEquals("123 Test St", restaurant.getAddress());
        assertEquals("M5S 2E6", restaurant.getZipCode());
        assertEquals(2.0f, restaurant.getPriceRange());
        assertEquals(coords, restaurant.getCoords());
        assertEquals("Chinese", restaurant.getFoodType());
        assertEquals(0, restaurant.getRatingsList().size());
        assertEquals(0.0f, restaurant.getAverageRating());
    }

    @Test
    void testMinimalConstructorWithId() {
        // Test Restaurant(float priceRange, List<Float> coordinates, String foodType, String id)
        Restaurant minimalRest = new Restaurant(3.0f, coords, "French", "rest-002");

        assertEquals("rest-002", minimalRest.getId());
        assertEquals("Unknown", minimalRest.getName());
        assertEquals("French", minimalRest.getFoodType());
        assertEquals(3.0f, minimalRest.getPriceRange());
    }

    @Test
    void testMinimalConstructorNoId() {
        // Test Restaurant(float priceRange, List<Float> coordinates, String foodType)
        Restaurant minimalRest = new Restaurant(1.0f, coords, "Burgers");

        assertEquals("", minimalRest.getId());
        assertEquals("Unknown", minimalRest.getName());
        assertEquals("Burgers", minimalRest.getFoodType());
    }

    @Test
    void testDefaultConstructor() {
        // Test Restaurant()
        Restaurant defaultRest = new Restaurant();

        assertEquals("", defaultRest.getId());
        assertEquals("Unknown", defaultRest.getName());
        assertEquals(1.0f, defaultRest.getPriceRange());
        assertEquals("Restaurant", defaultRest.getFoodType());
        assertNotNull(defaultRest.getCoords());
    }

    @Test
    void testSetters() {
        // Test all Setters
        restaurant.setId("new-id");
        assertEquals("new-id", restaurant.getId());

        restaurant.setName("New Name");
        assertEquals("New Name", restaurant.getName());

        restaurant.setAddress("456 New St");
        assertEquals("456 New St", restaurant.getAddress());

        restaurant.setZipCode("90210");
        assertEquals("90210", restaurant.getZipCode());

        restaurant.setPriceRange(4.0f);
        assertEquals(4.0f, restaurant.getPriceRange());

        List<Float> newCoords = new ArrayList<>();
        newCoords.add(0.0f);
        newCoords.add(0.0f);
        restaurant.setCoords(newCoords);
        assertEquals(newCoords, restaurant.getCoords());

        restaurant.setFoodType("Sushi");
        assertEquals("Sushi", restaurant.getFoodType());
    }

    @Test
    void testRatingLogic() {
        assertEquals(0.0f, restaurant.getAverageRating(), 0.01);

        restaurant.addToRating(5);
        assertEquals(5.0f, restaurant.getAverageRating(), 0.01);
        assertEquals(1, restaurant.getRatingsList().size());

        restaurant.addToRating(3);
        // (5 + 3) / 2 = 4.0
        assertEquals(4.0f, restaurant.getAverageRating(), 0.01);
        assertEquals(2, restaurant.getRatingsList().size());

        // Test getting the copy of list
        List<Integer> listCopy = restaurant.getRatingsList();
        listCopy.add(100); // Modifying the copy
        // Test if the original one keeps same
        assertEquals(2, restaurant.getRatingsList().size());
    }

    @Test
    void testInvalidRatings() {
        // Test Boundary: 1 <= rating <= 5
        restaurant.addToRating(0); // Invalid
        restaurant.addToRating(6); // Invalid
        restaurant.addToRating(-1); // Invalid

        assertEquals(0, restaurant.getRatingsList().size());
        assertEquals(0.0f, restaurant.getAverageRating());
    }

    @Test
    void testToString() {
        // Test if toString works
        String output = restaurant.toString();
        assertTrue(output.contains("rest-001"));
        assertTrue(output.contains("Test Restaurant"));
        assertTrue(output.contains("Chinese"));
    }
}

package use_case;

import data_access.RestaurantSearchService;
import entity.FoodFinderApp;
import entity.Restaurant;
import entity.User;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreateRestaurantListTest {

    private static class InMemoryRestaurantSearchService implements RestaurantSearchService {

        private final List<Restaurant> toReturn;

        float lastLat;
        float lastLng;
        String lastTerm;
        int lastLimit;

        InMemoryRestaurantSearchService(List<Restaurant> toReturn) {
            this.toReturn = toReturn;
        }

        @Override
        public List<Restaurant> searchRestaurants(float lat, float lng, String term, int limit) {
            lastLat = lat;
            lastLng = lng;
            lastTerm = term;
            lastLimit = limit;

            return toReturn;
        }

        @Override
        public Restaurant getRestaurantDetails(String restaurantId) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<String> getRestaurantReviews(String restaurantId) {
            throw new UnsupportedOperationException();
        }
    }

    @Test
    void testCreateRestaurantList() throws Exception {
        System.out.println("\n=== CreateRestaurantListTest: testCreateRestaurantList ===");

        // User coords
        User user = new User("testUser", "pw");
        user.setCoords(43.6629f, -79.3957f);

        FoodFinderApp app = new FoodFinderApp();
        app.setCurrentUser(user);

        // Fake restaurants
        Restaurant r1 = new Restaurant("1", "Ramen", "addr1", "zip1",
                2f, Arrays.asList(1f, 2f), "Ramen");
        Restaurant r2 = new Restaurant("2", "Pizza", "addr2", "zip2",
                3f, Arrays.asList(3f, 4f), "Pizza");

        InMemoryRestaurantSearchService fakeSearch =
                new InMemoryRestaurantSearchService(Arrays.asList(r1, r2));

        CreateRestaurantList interactor = new CreateRestaurantList(app, fakeSearch);

        interactor.execute();

        // Print search parameters
        System.out.println("Search called with:");
        System.out.println("Lat = " + fakeSearch.lastLat);
        System.out.println("Lng = " + fakeSearch.lastLng);
        System.out.println("Term = " + fakeSearch.lastTerm);
        System.out.println("Limit = " + fakeSearch.lastLimit);

        // Print restaurants
        System.out.println("\nRestaurants stored in FoodFinderApp:");
        for (Restaurant r : app.getFullRestaurantlist()) {
            System.out.println(" - " + r.getName() + " (" + r.getFoodType() + ")");
        }

        assertEquals(2, app.getFullRestaurantlist().size());
        assertEquals("Ramen", app.getFullRestaurantlist().get(0).getName());
    }
}

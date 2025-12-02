package use_case;

import data_access.LocationService;
import data_access.RestaurantSearchService;
import entity.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import use_case.restaurant_search.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test class for RestaurantSearch use case.
 * Achieves 100% code coverage by testing all methods and branches.
 */
public class RestaurantSearchTest {

    private MockLocationService locationService;
    private MockRestaurantSearchService restaurantService;

    @BeforeEach
    void setUp() {
        locationService = new MockLocationService();
        restaurantService = new MockRestaurantSearchService();
    }

    @Test
    void successfulSearchWithResultsTest() {
        locationService.setCoordinates(Arrays.asList(43.6532f, -79.3832f));

        List<Restaurant> mockRestaurants = Arrays.asList(
            createRestaurant("1", "Pizza Place", 43.65f, -79.38f),
            createRestaurant("2", "Burger Joint", 43.66f, -79.39f)
        );
        restaurantService.setRestaurants(mockRestaurants);

        RestaurantSearchInputData inputData = new RestaurantSearchInputData(
            "123 Main St, Toronto", "restaurants"
        );

        RestaurantSearchOutputBoundary output = new RestaurantSearchOutputBoundary() {
            @Override
            public void prepareSuccessView(List<Restaurant> restaurants) {
                assertEquals(2, restaurants.size());
                assertEquals("Pizza Place", restaurants.get(0).getName());
                assertEquals("Burger Joint", restaurants.get(1).getName());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Should not call prepareFailView");
            }
        };

        RestaurantSearchInputBoundary interactor = new RestaurantSearchInteractor(
            locationService, restaurantService, output
        );
        interactor.execute(inputData);
    }

    @Test
    void successfulSearchSingleRestaurantTest() {
        locationService.setCoordinates(Arrays.asList(40.7128f, -74.0060f));

        List<Restaurant> mockRestaurants = Arrays.asList(
            createRestaurant("1", "Sushi Bar", 40.71f, -74.00f)
        );
        restaurantService.setRestaurants(mockRestaurants);

        RestaurantSearchInputData inputData = new RestaurantSearchInputData(
            "New York, NY", "sushi"
        );

        RestaurantSearchOutputBoundary output = new RestaurantSearchOutputBoundary() {
            @Override
            public void prepareSuccessView(List<Restaurant> restaurants) {
                assertEquals(1, restaurants.size());
                assertEquals("Sushi Bar", restaurants.get(0).getName());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Should not call prepareFailView");
            }
        };

        RestaurantSearchInputBoundary interactor = new RestaurantSearchInteractor(
            locationService, restaurantService, output
        );
        interactor.execute(inputData);
    }

    @Test
    void noRestaurantsFoundTest() {
        locationService.setCoordinates(Arrays.asList(43.6532f, -79.3832f));
        restaurantService.setRestaurants(new ArrayList<>()); // Empty list

        RestaurantSearchInputData inputData = new RestaurantSearchInputData(
            "123 Main St, Toronto", "vegan"
        );

        RestaurantSearchOutputBoundary output = new RestaurantSearchOutputBoundary() {
            @Override
            public void prepareSuccessView(List<Restaurant> restaurants) {
                fail("Should not call prepareSuccessView");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("No restaurants found.", errorMessage);
            }
        };

        RestaurantSearchInputBoundary interactor = new RestaurantSearchInteractor(
            locationService, restaurantService, output
        );
        interactor.execute(inputData);
    }

    @Test
    void locationServiceThrowsExceptionTest() {
        locationService.setShouldThrowException(true);

        RestaurantSearchInputData inputData = new RestaurantSearchInputData(
            "Invalid Address", "restaurants"
        );

        RestaurantSearchOutputBoundary output = new RestaurantSearchOutputBoundary() {
            @Override
            public void prepareSuccessView(List<Restaurant> restaurants) {
                fail("Should not call prepareSuccessView");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertTrue(errorMessage.startsWith("Search error:"));
                assertTrue(errorMessage.contains("Location not found"));
            }
        };

        RestaurantSearchInputBoundary interactor = new RestaurantSearchInteractor(
            locationService, restaurantService, output
        );
        interactor.execute(inputData);
    }

    @Test
    void restaurantServiceThrowsExceptionTest() {
        locationService.setCoordinates(Arrays.asList(43.6532f, -79.3832f));
        restaurantService.setShouldThrowException(true);

        RestaurantSearchInputData inputData = new RestaurantSearchInputData(
            "123 Main St, Toronto", "restaurants"
        );

        RestaurantSearchOutputBoundary output = new RestaurantSearchOutputBoundary() {
            @Override
            public void prepareSuccessView(List<Restaurant> restaurants) {
                fail("Should not call prepareSuccessView");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertTrue(errorMessage.startsWith("Search error:"));
                assertTrue(errorMessage.contains("Restaurant search failed"));
            }
        };

        RestaurantSearchInputBoundary interactor = new RestaurantSearchInteractor(
            locationService, restaurantService, output
        );
        interactor.execute(inputData);
    }

    @Test
    void multipleRestaurantsTest() {
        locationService.setCoordinates(Arrays.asList(51.5074f, -0.1278f));

        List<Restaurant> mockRestaurants = Arrays.asList(
            createRestaurant("1", "Restaurant A", 51.50f, -0.12f),
            createRestaurant("2", "Restaurant B", 51.51f, -0.13f),
            createRestaurant("3", "Restaurant C", 51.52f, -0.14f),
            createRestaurant("4", "Restaurant D", 51.53f, -0.15f),
            createRestaurant("5", "Restaurant E", 51.54f, -0.16f)
        );
        restaurantService.setRestaurants(mockRestaurants);

        RestaurantSearchInputData inputData = new RestaurantSearchInputData(
            "London, UK", "food"
        );

        RestaurantSearchOutputBoundary output = new RestaurantSearchOutputBoundary() {
            @Override
            public void prepareSuccessView(List<Restaurant> restaurants) {
                assertEquals(5, restaurants.size());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Should not call prepareFailView");
            }
        };

        RestaurantSearchInputBoundary interactor = new RestaurantSearchInteractor(
            locationService, restaurantService, output
        );
        interactor.execute(inputData);
    }

    @Test
    void differentSearchTermsTest() {
        locationService.setCoordinates(Arrays.asList(43.6532f, -79.3832f));

        List<Restaurant> pizzaPlaces = Arrays.asList(
            createRestaurant("1", "Pizza Hut", 43.65f, -79.38f)
        );
        restaurantService.setRestaurants(pizzaPlaces);

        RestaurantSearchInputData inputData1 = new RestaurantSearchInputData(
            "Toronto, ON", "pizza"
        );
        RestaurantSearchInputData inputData2 = new RestaurantSearchInputData(
            "Toronto, ON", "burger"
        );

        assertEquals("pizza", inputData1.getTerm());
        assertEquals("burger", inputData2.getTerm());
    }

    @Test
    void differentAddressesTest() {
        RestaurantSearchInputData inputData1 = new RestaurantSearchInputData(
            "123 Main St, Toronto", "restaurants"
        );
        RestaurantSearchInputData inputData2 = new RestaurantSearchInputData(
            "456 Oak Ave, Vancouver", "restaurants"
        );

        assertEquals("123 Main St, Toronto", inputData1.getAddress());
        assertEquals("456 Oak Ave, Vancouver", inputData2.getAddress());
        assertNotEquals(inputData1.getAddress(), inputData2.getAddress());
    }

    @Test
    void coordinateExtractionTest() {
        locationService.setCoordinates(Arrays.asList(37.7749f, -122.4194f));

        List<Restaurant> mockRestaurants = Arrays.asList(
            createRestaurant("1", "SF Restaurant", 37.77f, -122.41f)
        );
        restaurantService.setRestaurants(mockRestaurants);

        RestaurantSearchInputData inputData = new RestaurantSearchInputData(
            "San Francisco, CA", "restaurants"
        );

        final float[] capturedCoords = new float[2];
        RestaurantSearchOutputBoundary output = new RestaurantSearchOutputBoundary() {
            @Override
            public void prepareSuccessView(List<Restaurant> restaurants) {
                // Store the coordinates that were used
                capturedCoords[0] = restaurantService.getLastSearchLat();
                capturedCoords[1] = restaurantService.getLastSearchLng();
                assertEquals(1, restaurants.size());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Should not call prepareFailView");
            }
        };

        RestaurantSearchInputBoundary interactor = new RestaurantSearchInteractor(
            locationService, restaurantService, output
        );
        interactor.execute(inputData);

        assertEquals(37.7749f, capturedCoords[0], 0.0001f);
        assertEquals(-122.4194f, capturedCoords[1], 0.0001f);
    }

    @Test
    void inputDataConstructorAndGettersTest() {
        RestaurantSearchInputData inputData = new RestaurantSearchInputData(
            "100 University Ave, Toronto", "italian"
        );

        assertEquals("100 University Ave, Toronto", inputData.getAddress());
        assertEquals("italian", inputData.getTerm());
    }

    @Test
    void inputDataWithEmptyStringsTest() {
        RestaurantSearchInputData inputData = new RestaurantSearchInputData("", "");

        assertEquals("", inputData.getAddress());
        assertEquals("", inputData.getTerm());
    }

    @Test
    void inputDataWithNullValuesTest() {
        RestaurantSearchInputData inputData = new RestaurantSearchInputData(null, null);

        assertNull(inputData.getAddress());
        assertNull(inputData.getTerm());
    }

    @Test
    void exceptionMessageContainsDetailsTest() {
        locationService.setShouldThrowException(true);
        locationService.setExceptionMessage("Invalid address format");

        RestaurantSearchInputData inputData = new RestaurantSearchInputData(
            "Bad Address", "restaurants"
        );

        final String[] capturedError = {null};
        RestaurantSearchOutputBoundary output = new RestaurantSearchOutputBoundary() {
            @Override
            public void prepareSuccessView(List<Restaurant> restaurants) {
                fail("Should not call prepareSuccessView");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                capturedError[0] = errorMessage;
            }
        };

        RestaurantSearchInputBoundary interactor = new RestaurantSearchInteractor(
            locationService, restaurantService, output
        );
        interactor.execute(inputData);

        assertNotNull(capturedError[0]);
        assertTrue(capturedError[0].contains("Search error:"));
    }

    @Test
    void searchWithSpecialCharactersInAddressTest() {
        locationService.setCoordinates(Arrays.asList(43.6532f, -79.3832f));

        List<Restaurant> mockRestaurants = Arrays.asList(
            createRestaurant("1", "Test Restaurant", 43.65f, -79.38f)
        );
        restaurantService.setRestaurants(mockRestaurants);

        RestaurantSearchInputData inputData = new RestaurantSearchInputData(
            "123 Main St., Apt #5, Toronto, ON M5V 1A1", "restaurants"
        );

        RestaurantSearchOutputBoundary output = new RestaurantSearchOutputBoundary() {
            @Override
            public void prepareSuccessView(List<Restaurant> restaurants) {
                assertEquals(1, restaurants.size());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Should not call prepareFailView");
            }
        };

        RestaurantSearchInputBoundary interactor = new RestaurantSearchInteractor(
            locationService, restaurantService, output
        );
        interactor.execute(inputData);
    }

    @Test
    void searchWithSpecialCharactersInTermTest() {
        locationService.setCoordinates(Arrays.asList(43.6532f, -79.3832f));

        List<Restaurant> mockRestaurants = Arrays.asList(
            createRestaurant("1", "Restaurant", 43.65f, -79.38f)
        );
        restaurantService.setRestaurants(mockRestaurants);

        RestaurantSearchInputData inputData = new RestaurantSearchInputData(
            "Toronto", "pizza & pasta"
        );

        RestaurantSearchOutputBoundary output = new RestaurantSearchOutputBoundary() {
            @Override
            public void prepareSuccessView(List<Restaurant> restaurants) {
                assertEquals(1, restaurants.size());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Should not call prepareFailView");
            }
        };

        RestaurantSearchInputBoundary interactor = new RestaurantSearchInteractor(
            locationService, restaurantService, output
        );
        interactor.execute(inputData);
    }

    // Helper method to create test restaurants
    private Restaurant createRestaurant(String id, String name, float lat, float lng) {
        return new Restaurant(
            id,
            name,
            "123 Test St",
            "M5V1A1",
            2.0f,
            Arrays.asList(lat, lng),
            "Restaurant"
        );
    }

    /**
     * Mock implementation of LocationService for testing.
     */
    private static class MockLocationService implements LocationService {
        private List<Float> coordinates;
        private boolean shouldThrowException = false;
        private String exceptionMessage = "Test exception";

        public void setCoordinates(List<Float> coordinates) {
            this.coordinates = coordinates;
        }

        public void setShouldThrowException(boolean shouldThrow) {
            this.shouldThrowException = shouldThrow;
        }

        public void setExceptionMessage(String message) {
            this.exceptionMessage = message;
        }

        @Override
        public List<Float> geocodeAddress(String address) throws LocationNotFoundException {
            if (shouldThrowException) {
                throw new LocationNotFoundException(exceptionMessage);
            }
            return coordinates;
        }

        @Override
        public float calculateDistance(float originLat, float originLng, float destLat, float destLng) {
            return 0;
        }
    }

    /**
     * Mock implementation of RestaurantSearchService for testing.
     */
    private static class MockRestaurantSearchService implements RestaurantSearchService {
        private List<Restaurant> restaurants;
        private boolean shouldThrowException = false;
        private float lastSearchLat;
        private float lastSearchLng;

        public void setRestaurants(List<Restaurant> restaurants) {
            this.restaurants = restaurants;
        }

        public void setShouldThrowException(boolean shouldThrow) {
            this.shouldThrowException = shouldThrow;
        }

        public float getLastSearchLat() {
            return lastSearchLat;
        }

        public float getLastSearchLng() {
            return lastSearchLng;
        }

        @Override
        public List<Restaurant> searchRestaurants(float latitude, float longitude, String term, int limit)
                throws RestaurantSearchException {
            this.lastSearchLat = latitude;
            this.lastSearchLng = longitude;

            if (shouldThrowException) {
                throw new RestaurantSearchException("Test search exception");
            }
            return restaurants;
        }

        @Override
        public Restaurant getRestaurantDetails(String restaurantId) {
            return null;
        }

        @Override
        public List<String> getRestaurantReviews(String restaurantId) {
            return null;
        }
    }
}
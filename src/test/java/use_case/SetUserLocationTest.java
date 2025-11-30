package use_case;

import data_access.LocationService;
import entity.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SetUserLocationTest {

    private static class InMemoryLocationService implements LocationService {
        @Override
        public List<Float> geocodeAddress(String address) {
            List<Float> coords = new ArrayList<>();
            coords.add(43.6629f);
            coords.add(-79.3957f);
            return coords;
        }

        @Override
        public float calculateDistance(float oLat, float oLng, float dLat, float dLng) {
            return 0;
        }
    }

    @Test
    void testSetUserLocationFromAddress() throws Exception {
        System.out.println("\n=== SetUserLocationTest: testSetUserLocationFromAddress ===");

        User user = new User("testUser", "pw");
        LocationService fake = new InMemoryLocationService();
        SetUserLocation useCase = new SetUserLocation(user, fake);

        useCase.setUserLocationFromAddress("Fake Address");

        float[] coords = user.getCoords();
        System.out.println("Coords set from address:");
        System.out.println("Lat = " + coords[0]);
        System.out.println("Lng = " + coords[1]);

        assertEquals(43.6629f, coords[0], 0.01);
    }

    @Test
    void testSetUserLocationFromCoords() {
        System.out.println("\n=== SetUserLocationTest: testSetUserLocationFromCoords ===");

        User user = new User("testUser", "pw");
        LocationService fake = new InMemoryLocationService();
        SetUserLocation useCase = new SetUserLocation(user, fake);

        useCase.setUserLocationFromCoords(10f, 20f);

        float[] coords = user.getCoords();

        System.out.println("Coords set directly:");
        System.out.println("Lat = " + coords[0]);
        System.out.println("Lng = " + coords[1]);

        assertEquals(10f, coords[0]);
    }
}

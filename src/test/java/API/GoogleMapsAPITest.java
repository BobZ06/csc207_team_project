package API;

import data_access.GoogleMapsLocationService;
import data_access.LocationService;

import java.util.List;

/**
 * Test class to verify Google Maps Geocoding API connectivity.
 */
public class GoogleMapsAPITest {

    public static void main(String[] args) {

        try {
            LocationService service = new GoogleMapsLocationService();

            String addressToTest = "27 King's College Cir, Toronto, ON";
            System.out.println("TEST: Geocoding Address -> '" + addressToTest + "'");

            long start = System.currentTimeMillis();
            List<Float> coordinates = service.geocodeAddress(addressToTest);
            long end = System.currentTimeMillis();

            System.out.println("Latitude:  " + coordinates.get(0));
            System.out.println("Longitude: " + coordinates.get(1));

            if (Math.abs(coordinates.get(0) - 43.66) < 0.05 && Math.abs(coordinates.get(1) - (-79.39)) < 0.05) {
                System.out.println("Coordinates look correct for Toronto");
            } else {
                System.out.println("Coordinates seem far off. Check if the API Key is valid.");
            }

        } catch (LocationService.LocationNotFoundException e) {
            System.err.println("Error Message: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("UNEXPECTED ERROR:");
            e.printStackTrace();
        }
    }
}

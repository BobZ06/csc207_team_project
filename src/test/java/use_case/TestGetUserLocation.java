package use_case;

import data_access.LocationService;
import entity.GetUserLocation;

import java.util.List;

public class TestGetUserLocation {

    public static void main(String[] args) {

        // -----------------------------
        // TEST 2: Get location by Address
        // -----------------------------
        System.out.println("\n=== Testing Address-based location ===");

        String testAddress = "King College Cir, Toronto";

        GetUserLocation addressLocation =
                new GetUserLocation.GetUserLocationByAddress(testAddress);

        try {
            List<Float> addressCoords = addressLocation.getLocation();
            System.out.println("Address Latitude:  " + addressCoords.get(0));
            System.out.println("Address Longitude: " + addressCoords.get(1));
        } catch (LocationService.LocationNotFoundException e) {
            System.err.println("Failed to get address-based location: " + e.getMessage());
        }
    }
}
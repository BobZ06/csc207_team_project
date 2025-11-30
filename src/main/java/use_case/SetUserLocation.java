package use_case;

import data_access.LocationService;
import entity.User;

import java.util.List;

/**
 * Use case interactor responsible for setting a User's location.
 * This can be done:
 *   - By converting an address into coordinates using a LocationService.
 *   - By directly setting latitude/longitude (e.g., from map selection).
 */
public class SetUserLocation {

    private final User user;
    private final LocationService locationService;

    /**
     * Constructor for the SetUserLocation use case.
     *
     * @param user            The User entity whose coordinates should be set.
     * @param locationService The geocoding interface for converting address → coordinates.
     */
    public SetUserLocation(User user, LocationService locationService) {
        this.user = user;
        this.locationService = locationService;
    }

    /**
     * Sets the user's location by geocoding a text address.
     *
     * @param address A human-readable address (e.g., "King College Cir, Toronto").
     */
    public void setUserLocationFromAddress(String address)
            throws LocationService.LocationNotFoundException {

        List<Float> coords = locationService.geocodeAddress(address);
        float lat = coords.get(0);
        float lng = coords.get(1);

        user.setCoords(lat, lng);
    }

    /**
     * Sets the user's location directly from latitude/longitude values.
     *
     * @param lat Latitude
     * @param lng Longitude
     */
    public void setUserLocationFromCoords(float lat, float lng) {
        user.setCoords(lat, lng);
    }
}

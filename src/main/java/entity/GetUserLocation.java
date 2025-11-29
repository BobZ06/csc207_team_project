package entity;


import data_access.GoogleMapsLocationService;
import data_access.LocationService;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Base class for getting a user's location.
 * Both implementations return a List<Float> containing [lat, lng].
 */
public abstract class GetUserLocation {

    public abstract List<Float> getLocation() throws LocationService.LocationNotFoundException;

    /**
     * Implementation #1:
     * Get user location by a provided address string.
     */
    public static class GetUserLocationByAddress extends GetUserLocation {
        private final String address;
        private final GoogleMapsLocationService googleService;

        public GetUserLocationByAddress(String address) {
            this.address = address;
            this.googleService = new GoogleMapsLocationService();
        }

        @Override
        public List<Float> getLocation() throws LocationService.LocationNotFoundException {
            return googleService.geocodeAddress(address);
        }
    }

    /**
     * Implementation #2:
     * Get user location using current GPS coordinates already
     * received from browser/mobile (no API call needed).
     */
    public static class GetUserLocationByCurrent extends GetUserLocation {
        private final float lat;
        private final float lng;

        public GetUserLocationByCurrent(float lat, float lng) {
            this.lat = lat;
            this.lng = lng;
        }

        @Override
        public List<Float> getLocation() {
            List<Float> coords = new ArrayList<>();
            coords.add(lat);
            coords.add(lng);
            return coords;
        }
    }
}

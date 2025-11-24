package data_access;

import config.ConfigManager;
import entity.Restaurant;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of RestaurantSearchService using the Yelp Fusion API.
 * Reference: https://docs.developer.yelp.com/docs/getting-started
 */
public class YelpRestaurantSearchService implements RestaurantSearchService {
    private final OkHttpClient client;
    private final String apiKey;
    private static final String API_HOST = "api.yelp.com";

    public YelpRestaurantSearchService() {
        // Set timeout to 30 seconds
        this.client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        ConfigManager.loadConfig();
        this.apiKey = ConfigManager.getYelpApiKey();

        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("Yelp API Key is missing in config.");
        }
    }

    @Override
    public List<Restaurant> searchRestaurants(float latitude, float longitude, String term, int limit)
            throws RestaurantSearchException {

        // Build the URL
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
                .scheme("https")
                .host(API_HOST)
                .addPathSegment("v3")
                .addPathSegments("businesses/search")
                .addQueryParameter("term", term)
                .addQueryParameter("latitude", String.valueOf(latitude))
                .addQueryParameter("longitude", String.valueOf(longitude))
                .addQueryParameter("limit", String.valueOf(limit))
                .addQueryParameter("sort_by", "best_match");

        // Build the Request
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("accept", "application/json")
                .build();

        // Execute the Request
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RestaurantSearchException("Yelp Search Error: " + response.code());
            }

            String responseBody = response.body().string();
            return parseSearchResponse(responseBody);

        } catch (IOException e) {
            throw new RestaurantSearchException("Network error: " + e.getMessage());
        }
    }

    @Override
    public Restaurant getRestaurantDetails(String restaurantId) throws RestaurantSearchException {
        // Build the URL: https://api.yelp.com/v3/businesses/{id}
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
                .scheme("https")
                .host(API_HOST)
                .addPathSegment("v3")
                .addPathSegments("businesses")
                .addPathSegment(restaurantId);

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RestaurantSearchException("Yelp Details Error: " + response.code());
            }

            String responseBody = response.body().string();
            JSONObject jsonObject = new JSONObject(responseBody);

            // Use the helper method to parse the single business object
            return parseRestaurantFromBusiness(jsonObject);

        } catch (IOException e) {
            throw new RestaurantSearchException("Network error: " + e.getMessage());
        }
    }

    @Override
    public List<String> getRestaurantReviews(String restaurantId) throws RestaurantSearchException {
        // Build the URL: https://api.yelp.com/v3/businesses/{id}/reviews
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
                .scheme("https")
                .host(API_HOST)
                .addPathSegment("v3")
                .addPathSegments("businesses")
                .addPathSegment(restaurantId)
                .addPathSegment("reviews");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RestaurantSearchException("Yelp Reviews Error: " + response.code());
            }

            String responseBody = response.body().string();
            return parseReviewsResponse(responseBody);

        } catch (IOException e) {
            throw new RestaurantSearchException("Network error: " + e.getMessage());
        }
    }

    private List<Restaurant> parseSearchResponse(String responseBody) {
        JSONObject jsonObject = new JSONObject(responseBody);
        List<Restaurant> restaurants = new ArrayList<>();

        if (jsonObject.has("businesses")) {
            JSONArray businesses = jsonObject.getJSONArray("businesses");
            for (int i = 0; i < businesses.length(); i++) {
                restaurants.add(parseRestaurantFromBusiness(businesses.getJSONObject(i)));
            }
        }
        return restaurants;
    }

    private List<String> parseReviewsResponse(String responseBody) {
        JSONObject jsonObject = new JSONObject(responseBody);
        List<String> reviews = new ArrayList<>();

        if (jsonObject.has("reviews")) {
            JSONArray reviewsArray = jsonObject.getJSONArray("reviews");
            for (int i = 0; i < reviewsArray.length(); i++) {
                JSONObject reviewObj = reviewsArray.getJSONObject(i);

                String text = reviewObj.optString("text");
                int rating = reviewObj.optInt("rating");
                String userName = reviewObj.getJSONObject("user").optString("name");

                // Format: "5/5 by User: Content..."
                reviews.add(rating + "/5 by " + userName + ": \"" + text + "\"");
            }
        }
        return reviews;
    }

    private Restaurant parseRestaurantFromBusiness(JSONObject business) {
        String id = business.optString("id");
        String name = business.optString("name");
        float rating = (float) business.optDouble("rating", 0.0);
        String price = business.optString("price", "$");

        String address = "";
        String zip = "";
        if (business.has("location")) {
            JSONObject loc = business.getJSONObject("location");
            zip = loc.optString("zip_code");
            JSONArray displayAddr = loc.optJSONArray("display_address");

            if (displayAddr != null && displayAddr.length() > 0) {
                // Combine address lines
                StringBuilder sb = new StringBuilder();
                for(int j = 0; j < displayAddr.length(); j++) {
                    if(j > 0) sb.append(", ");
                    sb.append(displayAddr.getString(j));
                }
                address = sb.toString();
            } else {
                address = loc.optString("address1");
            }
        }

        List<Float> coords = new ArrayList<>();
        if (business.has("coordinates")) {
            JSONObject c = business.getJSONObject("coordinates");
            coords.add((float)c.optDouble("latitude", 0.0));
            coords.add((float)c.optDouble("longitude", 0.0));
        } else {
            coords.add(0.0f);
            coords.add(0.0f);
        }

        String type = "Food";
        if (business.has("categories")) {
            JSONArray cats = business.getJSONArray("categories");
            if (cats.length() > 0) {
                type = cats.getJSONObject(0).optString("title");
            }
        }

        // Create the Restaurant object
        Restaurant r = new Restaurant(id, name, address, zip, (float)price.length(), coords, type);
        r.addToRating((int)rating);
        return r;
    }
}
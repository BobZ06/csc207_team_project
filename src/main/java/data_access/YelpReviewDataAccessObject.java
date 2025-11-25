package data_access;

import config.ConfigManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import view_ratings.ViewRatingsDataAccessInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YelpReviewDataAccessObject implements ViewRatingsDataAccessInterface {
    private final OkHttpClient client;
    private final String apiKey;

    public YelpReviewDataAccessObject() {
        this.client = new OkHttpClient();
        ConfigManager.loadConfig();
        this.apiKey = ConfigManager.getYelpApiKey();
    }

    @Override
    public List<String> getYelpReviews(String yelpID) {
        List<String> reviews = new ArrayList<>();

        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("Yelp API Key is missing!");
            return reviews;
        }

        String url = "https://api.yelp.com/v3/businesses/" + yelpID + "/reviews";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JSONObject json = new JSONObject(responseBody);

                if (json.has("reviews")) {
                    JSONArray reviewsArray = json.getJSONArray("reviews");

                    // Get up to 3 reviews
                    int limit = Math.min(reviewsArray.length(), 3);
                    for (int i = 0; i < limit; i++) {
                        JSONObject reviewJson = reviewsArray.getJSONObject(i);

                        String text = reviewJson.optString("text", "No content");

                        text = text.replace("\n", " ").replace("\r", " ").trim();

                        int rating = reviewJson.optInt("rating", 0);

                        String authorName = "Anonymous";
                        if (reviewJson.has("user")) {
                            authorName = reviewJson.getJSONObject("user").optString("name", "Anonymous");
                        }

                        String formattedReview = String.format("%s (%d⭐): %s", authorName, rating, text);
                        reviews.add(formattedReview);
                    }
                }
            } else {
                System.out.println("Yelp API call failed: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reviews;
    }
}
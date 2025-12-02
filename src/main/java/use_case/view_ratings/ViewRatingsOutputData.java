package use_case.view_ratings;

import java.util.List;

/**
 * The Output Data class for the View Ratings.
 * It gets the data to be presented to the user (reviews and restaurant ID).
 */
public class ViewRatingsOutputData {
    private final List<String> reviews;
    private final String restaurantID;

    /**
     * Constructs a new ViewRatingsOutputData object.
     *
     * @param reviews      the list of reviews fetched from the API.
     * @param restaurantID the ID of the restaurant.
     */
    public ViewRatingsOutputData(List<String> reviews, String restaurantID) {
        this.reviews = reviews;
        this.restaurantID = restaurantID;
    }

    /**
     * Gets the list of reviews.
     *
     * @return a list of review strings.
     */
    public List<String> getReviews() {
        return reviews;
    }

    /**
     * Gets the restaurant ID.
     *
     * @return the restaurant ID.
     */
    public String getRestaurantID() {
        return restaurantID;
    }
}

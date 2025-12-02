package use_case.view_ratings;

import java.util.List;

/**
 * Interface for the View Ratings Data Access Object.
 */
public interface ViewRatingsDataAccessInterface {

    /**
     * Fetches a list of reviews for a specific restaurant from the Yelp API.
     *
     * @param yelpID the unique Yelp ID of the restaurant.
     * @return a list of strings, where each string represents a formatted review.
     */
    List<String> getYelpReviews(String yelpID);
}

package use_case.view_ratings;

import java.util.List;

public class ViewRatingsOutputData {
    private final List<String> reviews;
    private final String restaurantID;

    public ViewRatingsOutputData(List<String> reviews, String restaurantID) {
        this.reviews = reviews;
        this.restaurantID = restaurantID;
    }

    public List<String> getReviews() { return reviews; }
    public String getRestaurantID() { return restaurantID; }
}

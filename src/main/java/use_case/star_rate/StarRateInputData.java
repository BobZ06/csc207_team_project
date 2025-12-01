package use_case.star_rate;

public class StarRateInputData {
    private final int starRating;
    private final String restaurantId;

    public StarRateInputData(int rate, String restaurant) {
        this.starRating = rate;
        this.restaurantId = restaurant;
    }

    public int getStarRating() {
        return this.starRating;
    }

    public String getRestaurantId() {
        return this.restaurantId;
    }

}

package view_menu;

/**
 * Input data for the View Menu use case.
 * Contains information about the restaurant the user selected.
 */
public class ViewMenuInputData {

    // Name and zip are what MenuService needs.
    private final String restaurantName;
    private final String zipCode;

    // Extra fields for displaying in the view.
    private final String restaurantAddress;
    private final double restaurantRating;

    public ViewMenuInputData(String restaurantName,
                             String zipCode,
                             String restaurantAddress,
                             double restaurantRating) {
        this.restaurantName = restaurantName;
        this.zipCode = zipCode;
        this.restaurantAddress = restaurantAddress;
        this.restaurantRating = restaurantRating;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public double getRestaurantRating() {
        return restaurantRating;
    }
}
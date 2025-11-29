package use_case.view_menu;


public class ViewMenuInputData {


    private final String restaurantName;
    private final String zipCode;


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
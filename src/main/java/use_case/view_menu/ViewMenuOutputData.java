package use_case.view_menu;

import org.json.JSONObject;

public class ViewMenuOutputData {

    private final String restaurantName;
    private final String restaurantId;
    private final String restaurantAddress;
    private final double restaurantRating;
    private final JSONObject menuData;

    public ViewMenuOutputData(String restaurantName,
                              String restaurantId,
                              String restaurantAddress,
                              double restaurantRating,
                              JSONObject menuData) {
        this.restaurantName = restaurantName;
        this.restaurantId = restaurantId;
        this.restaurantAddress = restaurantAddress;
        this.restaurantRating = restaurantRating;
        this.menuData = menuData;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRestaurantId() { return restaurantId; }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public double getRestaurantRating() {
        return restaurantRating;
    }

    public JSONObject getMenuData() {
        return menuData;
    }
}
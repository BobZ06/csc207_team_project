package menu_search;

public class MenuSearchInputData {
    private final String restaurantID;
    private final String query;

    public MenuSearchInputData(String restaurantID, String query) {
        this.restaurantID = restaurantID;
        this.query = query;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public String getQuery() {
        return query;
    }
}

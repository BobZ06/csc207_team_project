package use_case.restaurant_search;

public class RestaurantSearchInputData {
    private final String address;
    private final String term;

    public RestaurantSearchInputData(String address, String term) {
        this.address = address;
        this.term = term;
    }

    public String getAddress() { return address; }
    public String getTerm() { return term; }
}

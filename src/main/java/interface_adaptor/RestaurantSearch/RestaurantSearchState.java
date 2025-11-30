package interface_adaptor.RestaurantSearch;

import entity.Restaurant;
import java.util.List;

public class RestaurantSearchState {
    private List<Restaurant> results;
    private String error;

    public List<Restaurant> getResults() { return results; }
    public String getError() { return error; }

    public void setResults(List<Restaurant> results) { this.results = results; }
    public void setError(String error) { this.error = error; }
}

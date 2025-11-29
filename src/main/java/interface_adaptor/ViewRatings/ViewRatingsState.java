package interface_adaptor.ViewRatings;

import java.util.ArrayList;
import java.util.List;

public class ViewRatingsState {
    private List<String> reviews = new ArrayList<>();
    private String error = null;

    public ViewRatingsState(ViewRatingsState copy) {
        this.reviews = copy.reviews;
        this.error = copy.error;
    }

    public ViewRatingsState() {}

    public List<String> getReviews() { return reviews; }
    public void setReviews(List<String> reviews) { this.reviews = reviews; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}

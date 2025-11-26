package view_ratings;
import java.util.List;

public interface ViewRatingsDataAccessInterface {
    List<String> getYelpReviews(String yelpID);
}
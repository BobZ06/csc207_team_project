package star_rate;
import entity.Restaurant;

public class StarRateInputData {
    final private int starRating;
    final private String restaurantId;

    public StarRateInputData(int rate, String restaurant){
        this.starRating = rate;
        this.restaurantId = restaurant;

    }
    public int getStarRating(){
        return this.starRating;
    }
    public String getRestaurantId(){
        return this.restaurantId;
    }

}

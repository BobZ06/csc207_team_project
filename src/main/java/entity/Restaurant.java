package entity;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private List<Integer> ratingsList;
    private float priceRange;
    private List<Float> coordinates;
    private String foodType;

    public Restaurant(float priceRange, List<Float> coordinates, String foodType) {
        this.priceRange = priceRange;
        this.coordinates = coordinates;
        this.foodType = foodType;
        this.ratingsList = new ArrayList<>();
    }

    public Restaurant() {
        this.ratingsList = new ArrayList<>();
        this.coordinates = new ArrayList<>();
    }

    public void addToRating(int rating) {
        if (rating >= 1 && rating <= 5) {
            this.ratingsList.add(rating);
        }
    }

    public float getAverageRating() {
        if (ratingsList.isEmpty()) {
            return 0.0f;
        }
        int ratings = 0;
        for (int i = 0; i < ratingsList.size(); i++) {
            ratings += ratingsList.get(i);
        }
        return (float) ratings / ratingsList.size();
    }

    public float getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(float price) {
        this.priceRange = price;
    }

    public List<Float> getCoords() {
        return coordinates;
    }

    public String getFoodType() {
        return foodType;
    }
}

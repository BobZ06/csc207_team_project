package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Restaurant entity representing a dining establishment.
 * Contains all necessary information for the Food Finder application.
 */
public class Restaurant {
    private String id;           // from Yelp business_id
    private String name;          // Restaurant name
    private String address;       // Street address
    private String zipCode;       // Zip/postal code
    private float priceRange;     // Price level (1.0 = $, 2.0 = $$, etc.)
    private List<Float> coordinates; // [latitude, longitude]
    private String foodType;      // Cuisine type/category
    private List<Integer> ratingsList; // User ratings (1-5)

    /**
     * Full constructor with all required fields.
     */
    public Restaurant(String id, String name, String address, String zipCode,
                      float priceRange, List<Float> coordinates, String foodType) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.zipCode = zipCode;
        this.priceRange = priceRange;
        this.coordinates = coordinates;
        this.foodType = foodType;
        this.ratingsList = new ArrayList<>();
    }

    /**
     * Minimal constructor for backwards compatibility.
     */
    public Restaurant(float priceRange, List<Float> coordinates, String foodType, String id) {
        this(id, "Unknown", "", "", priceRange, coordinates, foodType);
    }
    public Restaurant(float priceRange, List<Float> coordinates, String foodType) {
        this("", "Unknown", "", "", priceRange, coordinates, foodType);
    }

    /**
     * Default constructor.
     */
    public Restaurant() {
        this("", "Unknown", "", "", 1.0f, new ArrayList<>(), "Restaurant");
    }

    // ID getter/setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Name getter/setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Address getter/setter
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // ZipCode getter/setter
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    // Rating methods
    public void addToRating(int rating) {
        if (rating >= 1 && rating <= 5) {
            this.ratingsList.add(rating);
        }
    }

    /**
     * Calculate the average rating from all user ratings.
     * @return average rating (0.0 if no ratings)
     */
    public float getAverageRating() {  // FIXED: removed unnecessary parameter
        if (ratingsList.isEmpty()) {
            return 0.0f;
        }
        int sum = 0;
        for (int rating : ratingsList) {
            sum += rating;
        }
        return (float) sum / ratingsList.size();
    }

    /**
     * Get a copy of the ratings list.
     * @return copy of ratings list
     */
    public List<Integer> getRatingsList() {
        return new ArrayList<>(ratingsList); // Return copy for safety
    }

    public float getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(float price) {
        this.priceRange = price;
    }

    // Coordinates methods
    public List<Float> getCoords() {
        return coordinates;
    }

    public void setCoords(List<Float> coordinates) {
        this.coordinates = coordinates;
    }

    // Food type methods
    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    @Override
    public String toString() {
        return String.format("Restaurant{id='%s', name='%s', address='%s', zipCode='%s', " +
                        "priceRange=%.1f, foodType='%s', avgRating=%.1f}",
                id, name, address, zipCode, priceRange, foodType, getAverageRating());
    }
}
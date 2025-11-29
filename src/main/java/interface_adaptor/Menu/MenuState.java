package interface_adaptor.Menu;

import entity.MenuItem;
import org.json.JSONObject;

import java.util.ArrayList;


public class MenuState {

    private String name = "";
    private String restaurantId;
    private float rating = 0;
    private String address = "";
    private ArrayList<MenuItem> menuList;
    private String reviewError;
    private String username = "";

    // API menu data (JSON from MenuService)
    private JSONObject menuData;

    // Error message for menu API
    private String menuError;

    // ---- GETTERS ----

    public String getName() {
        return this.name;
    }

    public String getRestaurantId() {
        return this.restaurantId;
    }

    public float getRating() {
        return this.rating;
    }

    public String getAddress() {
        return this.address;
    }

    public ArrayList<MenuItem> getMenuList() {
        return this.menuList;
    }

    public String getReviewError() {
        return this.reviewError;
    }

    public String getUsername() {
        return this.username;
    }

    public JSONObject getMenuData() {
        return menuData;
    }

    public String getMenuError() {
        return menuError;
    }

    // ---- SETTERS ----

    public void setName(String name) {
        this.name = name;
    }

    public void setRestaurant(String rest) {
        this.restaurantId = rest;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMenuList(ArrayList<MenuItem> menuList) {
        this.menuList = menuList;
    }

    public void setReviewError(String error) {
        this.reviewError = error;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public void setMenuData(JSONObject menuData) {
        this.menuData = menuData;
    }

    public void setMenuError(String menuError) {
        this.menuError = menuError;
    }
}
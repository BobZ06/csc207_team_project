package interface_adaptor.Menu;

import entity.MenuItem;

import java.util.ArrayList;

public class MenuState {
    private String name = "";
    private String restaurantId;
    private float rating = 0;
    private String address = "";
    private ArrayList<MenuItem> menuList;

    public String getName(){
        return this.name;
    }
    public String getRestaurantId(){
        return this.restaurantId;
    }
    public float getRating(){
        return this.rating;
    }
    public String getAddress(){
        return this.address;
    }
    public ArrayList<MenuItem> getMenuList(){
        return this.menuList;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setRestaurant(String rest){
        this.restaurantId = rest;
    }
    public void setRating(float rating) {
        this.rating = rating;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setMenuList(ArrayList<MenuItem> menuList){
        this.menuList = menuList;
    }
}

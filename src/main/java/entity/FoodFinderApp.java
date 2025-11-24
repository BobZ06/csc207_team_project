package entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class FoodFinderApp {
    private List<Restaurant> restaurantList;
    private User currentUser;

    public FoodFinderApp() {
        restaurantList = new ArrayList<Restaurant>();
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public List<Restaurant> getFullRestaurantlist() {
        return restaurantList;
    }

    public void addToRestaurants(Restaurant restaurant) {
        restaurantList.add(restaurant);
    }

    public double getDistance(Restaurant restaurant) {
        float userX = currentUser.getCoords()[0];
        float userY = currentUser.getCoords()[1];
        float restaurantX = restaurant.getCoords().get(0);
        float restaurantY = restaurant.getCoords().get(1);

        float xSquared = (float) Math.pow(restaurantX - userX, 2);
        float ySquared = (float) Math.pow(restaurantY - userY, 2);
        return Math.sqrt(xSquared + ySquared);
    }

    public List<Restaurant> getSortByClosest() {
        List<Restaurant> sortedList = new ArrayList<Restaurant>(restaurantList);
        sortedList.sort(new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant A, Restaurant B) {
                double distA = getDistance(A);
                double distB = getDistance(B);
                return Double.compare(distA, distB);
            }
        });
        return sortedList;
    }

    public List<Restaurant> getSortByCheapest(){
        List<Restaurant> sortedList = new ArrayList<Restaurant>(restaurantList);
        sortedList.sort(new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant A, Restaurant B) {
                double priceA = A.getPriceRange();
                double priceB = B.getPriceRange();
                return Double.compare(priceA, priceB);
            }
        });
        return sortedList;
    }
}


package entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String name;
    private final String password;
    private final float[] coords = new float[2];
    private final List<String> reviewedRests;

    public User(String name, String password){
        this.name = name;
        this.password = password;
        this.coords[0] = 0f;
        this.coords[1] = 0f;
        this.reviewedRests = new ArrayList<String>();
    }

    public void setCoords(float lat, float lng) {
        this.coords[0] = lat;
        this.coords[1] = lng;
    }

    public String getName(){
        return this.name;
    }
    public String getPassword(){
        return this.password;
    }
    public float[] getCoords(){
        return this.coords;
    }

    public void setCoords(float coord1, float coord2){
        this.coords[0] = coord1;
        this.coords[1] = coord2;
    }

    public void addToReviewed(String id){
        this.reviewedRests.add(id);
    }
    public boolean inReviewed(String id){
        return reviewedRests.contains(id);
    }
}

package entity;
import data_access.LocationService;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String name;
    private final String password;
    private final float[] coords = new float[2];

    public User(String name, String password){
        this.name = name;
        this.password = password;
        this.coords[0] = 0f;
        this.coords[1] = 0f;
    }

    public void setCoordsFromLocation(GetUserLocation locationGetter)
            throws LocationService.LocationNotFoundException {

        List<Float> list = locationGetter.getLocation();
        this.coords[0] = list.get(0);   // latitude
        this.coords[1] = list.get(1);   // longitude
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
}

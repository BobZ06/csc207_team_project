package data_access;

import entity.User;
import use_case.log_in.LoginDataAccessInterface;
import use_case.log_out.LogoutDataAccessInterface;
import use_case.signup.SignupDataAccessInterface;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileUserDataAccessObject implements LoginDataAccessInterface, SignupDataAccessInterface,
        LogoutDataAccessInterface {
    private static final String HEADER = "username,password,latitude,longitude,reviewedRestaurants";
    private final Map<String, Integer> headers = new LinkedHashMap<>();
    private final Map<String, User> users = new HashMap<>();
    private String currentUser;
    private final File csvFile;

    public FileUserDataAccessObject(String csvPath) throws IOException {
        csvFile = new File(csvPath);
        headers.put("username", 0);
        headers.put("password", 1);
        headers.put("latitude", 2);
        headers.put("longitude", 3);
        headers.put("reviewedRestaurants", 4);

        if (csvFile.length() == 0) {
            save();
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                final String header = reader.readLine();
                if (!header.equals(HEADER)) {
                    throw new RuntimeException(String.format("header should be:%n%s%nbut was:%n%s", HEADER, header));
                }

                String row;
                while ((row = reader.readLine()) != null) {
                    final String[] col = row.split(",", -1);
                    final String username = col[headers.get("username")];
                    final String password = col[headers.get("password")];
                    final float latitude = Float.parseFloat(col[headers.get("latitude")]);
                    final float longitude = Float.parseFloat(col[headers.get("longitude")]);
                    final String reviewedRestaurants = col[headers.get("reviewedRestaurants")];

                    User user = new User(username, password);
                    user.setCoords(latitude, longitude);

                    if (!reviewedRestaurants.isEmpty()) {
                        String[] restaurants = reviewedRestaurants.split(" ");
                        for (String restaurantId : restaurants) {
                            if (!restaurantId.isEmpty()) {
                                user.addToReviewed(restaurantId);
                            }
                        }
                    }

                    users.put(username, user);
                }
            }
        }
    }

    @Override
    public boolean existsByName(String username) {
        return users.containsKey(username);
    }

    @Override
    public void save(User user) {
        users.put(user.getName(), user);
        save();
    }

    @Override
    public User get(String username) {
        return users.get(username);
    }

    @Override
    public void setCurrentUsername(String name) {
        currentUser = name;
    }

    @Override
    public String getCurrentUsername() {
        return currentUser;
    }

    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write(String.join(",", headers.keySet()));
            writer.newLine();

            for (User user : users.values()) {
                final String line = String.format("%s,%s,%s,%s,%s",
                        user.getName(),
                        user.getPassword(),
                        user.getCoords()[0],
                        user.getCoords()[1],
                        getReviewedRestaurantsString(user));
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String getReviewedRestaurantsString(User user) {
        StringBuilder str = new StringBuilder();
        for (String restaurantId : user.getReviewedRests()) {
            str.append(restaurantId).append(" ");
        }
        return str.toString().trim();
    }
}
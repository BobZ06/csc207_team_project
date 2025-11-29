package data_access;
import entity.Restaurant;
import use_case.star_rate.StarRateDataAccessInterface;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class APIStarRateDataAccessObject implements StarRateDataAccessInterface{
    private static final String HEADER = "restaurantId,reviewsData";
    private String currentRestaurantId;
    private final Map<String, Integer> headers = new LinkedHashMap<>();
    private RestaurantSearchService apiCall = new YelpRestaurantSearchService();
    private final Map<String, List<Integer>> restaurants = new HashMap<>();
    private final File csvFile;

    public APIStarRateDataAccessObject(String csvPath) throws FileNotFoundException {
        csvFile = new File(csvPath);
        headers.put("restaurantId", 0);
        headers.put("reviewsData", 1);

        if (csvFile.length() == 0) {
            save();
        }
        else{
            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                final String header = reader.readLine();
                if (!header.equals(HEADER)) {
                    throw new RuntimeException(String.format("header should be%n: %s%n but was:%n%s", HEADER, header));
                }

                String row;
                while ((row = reader.readLine()) != null) {
                    final String[] col = row.split(",");
                    final String id = String.valueOf(col[headers.get("restaurantId")]);
                    final List<Integer> data = new java.util.ArrayList<>(List.of());
                    if (col.length > 1){
                        final String dataTemp = String.valueOf(col[headers.get("reviewsData")]);
                        final String[] dataArray = dataTemp.split("\\s");

                        for (String str : dataArray){
                            data.add(Integer.parseInt(str));
                        }
                    }
                    restaurants.put(id, data);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Restaurant getRestaurantById(String id) throws RestaurantSearchService.RestaurantSearchException {
        Restaurant rest = apiCall.getRestaurantDetails(id);
        if (restaurants.get(id) != null){
            List<Integer> reviews = restaurants.get(id);
            rest.setRatingsList(reviews);
        }
        return rest;
    }

    @Override
    public String getCurrentRestaurantId() {
        return this.currentRestaurantId;
    }

    @Override
    public void setCurrentRestaurantId(String id) {
        this.currentRestaurantId = id;
    }

    // Saves to local device.
    @Override
    public void save(String id, Restaurant rest) {
        restaurants.put(id, rest.getRatingsList());
        save();
    }
    public void save(){
        final BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(csvFile));
            writer.write(String.join(",", headers.keySet()));
            writer.newLine();

            for (String keys : restaurants.keySet()) {
                final String line = String.format("%s,%s",
                        keys, getRatingsString(restaurants.get(keys)));
                writer.write(line);
                writer.newLine();
            }
            writer.close();
            System.out.println("Saved");

        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    public String getRatingsString(List<Integer> ratingsList){
        StringBuilder str = new StringBuilder();
        for (int i : ratingsList){
            str.append(i+" ");
        }
        return str.toString();
    }
}

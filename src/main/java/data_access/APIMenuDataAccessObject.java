package data_access;

import entity.MenuItem;
import org.json.JSONArray;
import org.json.JSONObject;

import use_case.menu_search.MenuSearchDataAccessInterface;
import use_case.view_menu.ViewMenuDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class APIMenuDataAccessObject implements
        ViewMenuDataAccessInterface,
        MenuSearchDataAccessInterface {

    private final MenuService menuService;

    public APIMenuDataAccessObject(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public JSONObject getRestaurantMenu(String restaurantName, String zipCode) throws Exception {
        try {
            return menuService.getRestaurantMenu(restaurantName, zipCode);
        } catch (Exception e) {
            return getLocalMenuByKeyword(restaurantName);
        }
    }

    private JSONObject getLocalMenuByKeyword(String name) throws Exception {
        String lowerName = name.toLowerCase();
        JSONArray items = new JSONArray();

        if (lowerName.contains("burger")
                || lowerName.contains("mcdonald")
                || lowerName.contains("king")
                || lowerName.contains("shack")) {

            items.put(createItem("Classic Cheeseburger", 9.99));
            items.put(createItem("Double Bacon Smash Burger", 14.50));
            items.put(createItem("Crispy Chicken Sandwich", 11.00));
            items.put(createItem("Large Fries", 4.99));
            items.put(createItem("Onion Rings", 5.50));
            items.put(createItem("Chocolate Milkshake", 6.00));
            items.put(createItem("Diet Coke", 2.50));

        } else if (lowerName.contains("pizza")
                || lowerName.contains("domino")
                || lowerName.contains("hut")) {

            items.put(createItem("Large Pepperoni Pizza", 18.99));
            items.put(createItem("Margherita Pizza", 15.50));
            items.put(createItem("Meat Lovers Pizza", 21.00));
            items.put(createItem("Hawaiian Pizza", 17.50));
            items.put(createItem("Garlic Breadsticks", 6.99));
            items.put(createItem("Caesar Salad", 8.99));
            items.put(createItem("Coke Zero", 2.50));

        } else if (lowerName.contains("noodle")
                || lowerName.contains("ramen")
                || lowerName.contains("pho")) {

            items.put(createItem("Tonkotsu Ramen", 16.99));
            items.put(createItem("Spicy Miso Ramen", 17.50));
            items.put(createItem("Beef Pho (Large)", 15.00));
            items.put(createItem("Chicken Pad Thai", 14.99));
            items.put(createItem("Gyoza (6pcs)", 7.99));
            items.put(createItem("Shrimp Tempura", 10.50));
            items.put(createItem("Green Tea", 3.00));

        } else if (lowerName.contains("sushi")
                || lowerName.contains("japan")) {

            items.put(createItem("Salmon Sashimi (3pcs)", 8.99));
            items.put(createItem("Spicy Tuna Roll", 9.50));
            items.put(createItem("California Roll", 7.00));
            items.put(createItem("Dragon Roll", 13.99));
            items.put(createItem("Miso Soup", 3.50));
            items.put(createItem("Edamame", 5.00));
            items.put(createItem("Matcha Ice Cream", 4.50));

        } else if (lowerName.contains("chinese")
                || lowerName.contains("wok")
                || lowerName.contains("panda")) {

            items.put(createItem("Kung Pao Chicken", 13.99));
            items.put(createItem("Sweet and Sour Pork", 14.50));
            items.put(createItem("Beef and Broccoli", 15.00));
            items.put(createItem("Fried Rice", 10.99));
            items.put(createItem("Spring Rolls (2pcs)", 4.50));
            items.put(createItem("Wonton Soup", 5.99));
            items.put(createItem("Fortune Cookie", 0.50));

        } else if (lowerName.contains("indian")
                || lowerName.contains("curry")
                || lowerName.contains("tandoori")) {

            items.put(createItem("Butter Chicken", 16.99));
            items.put(createItem("Chicken Tikka Masala", 17.50));
            items.put(createItem("Lamb Vindaloo", 18.99));
            items.put(createItem("Garlic Naan", 3.99));
            items.put(createItem("Vegetable Samosa", 5.50));
            items.put(createItem("Mango Lassi", 4.99));
            items.put(createItem("Basmati Rice", 4.00));

        } else if (lowerName.contains("mexican")
                || lowerName.contains("taco")
                || lowerName.contains("burrito")) {

            items.put(createItem("Beef Tacos (3pcs)", 12.99));
            items.put(createItem("Chicken Burrito Bowl", 14.50));
            items.put(createItem("Carne Asada Quesadilla", 13.99));
            items.put(createItem("Chips and Guacamole", 6.50));
            items.put(createItem("Churros", 5.00));
            items.put(createItem("Horchata", 4.00));

        } else if (lowerName.contains("coffee")
                || lowerName.contains("cafe")
                || lowerName.contains("starbucks")
                || lowerName.contains("tim")) {

            items.put(createItem("Medium Roast Coffee", 2.50));
            items.put(createItem("Caramel Macchiato", 5.50));
            items.put(createItem("Iced Latte", 4.99));
            items.put(createItem("Blueberry Muffin", 3.25));
            items.put(createItem("Croissant", 3.50));
            items.put(createItem("Bagel with Cream Cheese", 3.99));

        } else if (lowerName.contains("salad")
                || lowerName.contains("green")
                || lowerName.contains("healthy")) {

            items.put(createItem("Kale Caesar Salad", 13.50));
            items.put(createItem("Greek Salad", 12.99));
            items.put(createItem("Cobb Salad", 14.50));
            items.put(createItem("Quinoa Bowl", 11.99));
            items.put(createItem("Fresh Fruit Cup", 5.50));
            items.put(createItem("Smoothie", 7.00));

        } else if (lowerName.contains("sandwich")
                || lowerName.contains("sub")) {

            items.put(createItem("Footlong Italian Sub", 11.99));
            items.put(createItem("Turkey Club Sandwich", 10.50));
            items.put(createItem("Meatball Marinara", 9.99));
            items.put(createItem("Tuna Melt", 8.50));
            items.put(createItem("Potato Chips", 2.00));
            items.put(createItem("Cookie", 1.25));

        } else {
            items.put(createItem("House Special", 15.99));
            items.put(createItem("Daily Soup", 6.99));
            items.put(createItem("Classic Burger", 12.50));
            items.put(createItem("Fries", 4.50));
            items.put(createItem("Soft Drink", 2.50));
        }

        JSONObject result = new JSONObject();
        result.put("menuItems", items);
        return result;
    }

    private JSONObject createItem(String title, double price) {
        JSONObject item = new JSONObject();
        item.put("title", title);
        item.put("price", price);
        item.put("readablePrice", "$" + String.format("%.2f", price));
        return item;
    }

    @Override
    public List<MenuItem> getMenu(String restaurantName) {
        try {
            JSONObject menuJson = getRestaurantMenu(restaurantName, "00000");
            JSONArray arr = menuJson.getJSONArray("menuItems");

            List<MenuItem> list = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                list.add(new MenuItem(
                        obj.getString("title"),
                        (float) obj.getDouble("price"),
                        obj.optString("readablePrice", "")
                ));
            }
            return list;

        } catch (Exception e) {
            List<MenuItem> fallback = new ArrayList<>();
            fallback.add(new MenuItem("Error loading menu", 0.0f, "Please try again"));
            return fallback;
        }
    }
}
package data_access;

import entity.MenuItem;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.menu_search.MenuSearchDataAccessInterface;
import java.util.ArrayList;
import java.util.List;

public class APIMenuDataAccessObject implements MenuSearchDataAccessInterface {

    private final MenuService menuService;

    public APIMenuDataAccessObject(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public List<MenuItem> getMenu(String restaurantName) {
        try {
            JSONObject menuJson = menuService.getRestaurantMenu(restaurantName, "00000");
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
            return new ArrayList<>();
        }
    }
}

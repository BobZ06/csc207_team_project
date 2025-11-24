package data_access;

import entity.MenuItem;
import menu_search.MenuSearchDataAccessInterface;
import java.util.*;

public class TempMenuDataAccessObject implements MenuSearchDataAccessInterface {
    private final Map<String, List<MenuItem>> menus = new HashMap<>();

    @Override
    public List<MenuItem> getMenu(String restaurantID) {
        List<MenuItem> items = menus.get(restaurantID);
        if (items == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(items);
    }

    public void addMenuItem(String restaurantID, MenuItem item) {
        menus.computeIfAbsent(restaurantID, id -> new ArrayList<>()).add(item);
    }

}
package menu_search;

import entity.MenuItem;
import java.util.List;

public interface MenuSearchDataAccessInterface {
    List<MenuItem> getMenu(String restaurantId);
}

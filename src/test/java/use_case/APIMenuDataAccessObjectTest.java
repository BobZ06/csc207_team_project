package use_case;

import data_access.APIMenuDataAccessObject;
import data_access.MenuService;
import entity.MenuItem;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class APIMenuDataAccessObjectTest {

    @Test
    void getRestaurantMenu_usesMenuServiceWhenOk() throws Exception {
        MenuService menuService = mock(MenuService.class);
        JSONObject json = new JSONObject()
                .put("menuItems", new JSONArray()
                        .put(new JSONObject()
                                .put("title", "Item")
                                .put("price", 5.0)
                                .put("readablePrice", "$5.00")));

        when(menuService.getRestaurantMenu("Test", "12345")).thenReturn(json);

        APIMenuDataAccessObject dao = new APIMenuDataAccessObject(menuService);

        JSONObject result = dao.getRestaurantMenu("Test", "12345");

        assertEquals(json.toString(), result.toString());
        verify(menuService).getRestaurantMenu("Test", "12345");
    }

    @Test
    void getRestaurantMenu_fallsBackToLocalWhenServiceFails() throws Exception {
        MenuService menuService = mock(MenuService.class);
        when(menuService.getRestaurantMenu(anyString(), anyString()))
                .thenThrow(new RuntimeException("API down"));

        APIMenuDataAccessObject dao = new APIMenuDataAccessObject(menuService);

        JSONObject result = dao.getRestaurantMenu("Burger Palace", "00000");

        assertTrue(result.has("menuItems"));
        JSONArray arr = result.getJSONArray("menuItems");
        assertTrue(arr.length() > 0);
    }

    @Test
    void getMenu_convertsJsonToMenuItems() throws Exception {
        MenuService menuService = mock(MenuService.class);
        JSONArray items = new JSONArray();
        items.put(new JSONObject()
                .put("title", "Item A")
                .put("price", 10.0)
                .put("readablePrice", "$10.00"));
        items.put(new JSONObject()
                .put("title", "Item B")
                .put("price", 7.5)
                .put("readablePrice", "$7.50"));

        JSONObject json = new JSONObject().put("menuItems", items);
        when(menuService.getRestaurantMenu("Any", "00000")).thenReturn(json);

        APIMenuDataAccessObject dao = new APIMenuDataAccessObject(menuService);

        List<MenuItem> list = dao.getMenu("Any");

        assertEquals(2, list.size());
        assertEquals("Item A", list.get(0).getName());
        assertEquals(10.0f, list.get(0).getPrice());
        assertEquals("$10.00", list.get(0).getDescription());
    }

    @Test
    void getMenu_handlesItemsWithMissingFields() throws Exception {
        MenuService menuService = mock(MenuService.class);

        JSONObject json = new JSONObject()
                .put("menuItems", new JSONArray()
                        .put(new JSONObject()          // no price, no readablePrice
                                .put("title", "Mystery Dish")));

        when(menuService.getRestaurantMenu("Weird Place", "00000"))
                .thenReturn(json);

        APIMenuDataAccessObject dao = new APIMenuDataAccessObject(menuService);

        List<MenuItem> items = dao.getMenu("Weird Place");

        assertEquals(1, items.size());
        MenuItem item = items.get(0);
        assertEquals("Mystery Dish", item.getName());
        assertEquals(0.0f, item.getPrice());       // defaulted
        assertEquals("", item.getDescription());   // defaulted
    }
}
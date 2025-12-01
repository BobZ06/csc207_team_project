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

        assertNotNull(result);
        assertEquals(json.toString(), result.toString());
        verify(menuService).getRestaurantMenu("Test", "12345");
    }

    @Test
    void getRestaurantMenu_fallsBackToLocalOnException() throws Exception {
        MenuService menuService = mock(MenuService.class);

        when(menuService.getRestaurantMenu("Burger Town", "00000"))
                .thenThrow(new RuntimeException("API down"));

        APIMenuDataAccessObject dao = new APIMenuDataAccessObject(menuService);

        JSONObject result = dao.getRestaurantMenu("Burger Town", "00000");

        assertNotNull(result);
        JSONArray items = result.getJSONArray("menuItems");
        assertTrue(items.length() > 0);
    }

    @Test
    void getMenu_convertsJsonToMenuItems() throws Exception {
        MenuService menuService = mock(MenuService.class);

        JSONObject json = new JSONObject()
                .put("menuItems", new JSONArray()
                        .put(new JSONObject()
                                .put("title", "Pasta")
                                .put("price", 12.5)
                                .put("readablePrice", "$12.50"))
                        .put(new JSONObject()
                                .put("title", "Salad")
                                .put("price", 8.0)
                                .put("readablePrice", "$8.00")));

        when(menuService.getRestaurantMenu("Italian Place", "00000"))
                .thenReturn(json);

        APIMenuDataAccessObject dao = new APIMenuDataAccessObject(menuService);

        List<MenuItem> items = dao.getMenu("Italian Place");

        assertEquals(2, items.size());

        MenuItem first = items.get(0);
        assertEquals("Pasta", first.getName());
        assertEquals(12.5f, first.getPrice());
        assertEquals("$12.50", first.getDescription());

        MenuItem second = items.get(1);
        assertEquals("Salad", second.getName());
        assertEquals(8.0f, second.getPrice());
        assertEquals("$8.00", second.getDescription());
    }

    @Test
    void getMenu_handlesItemsWithMissingFields() throws Exception {
        MenuService menuService = mock(MenuService.class);

        JSONObject json = new JSONObject()
                .put("menuItems", new JSONArray()
                        .put(new JSONObject()
                                .put("title", "Mystery Dish"))); // no price, no readablePrice

        when(menuService.getRestaurantMenu("Weird Place", "00000"))
                .thenReturn(json);

        APIMenuDataAccessObject dao = new APIMenuDataAccessObject(menuService);

        List<MenuItem> items = dao.getMenu("Weird Place");

        assertEquals(1, items.size());
        MenuItem item = items.get(0);

        // because the exception is thrown while reading price,
        // the DAO falls back to the default "Error loading menu" item
        assertEquals("Error loading menu", item.getName());
        assertEquals(0.0f, item.getPrice());
        assertEquals("Please try again", item.getDescription());
    }
}
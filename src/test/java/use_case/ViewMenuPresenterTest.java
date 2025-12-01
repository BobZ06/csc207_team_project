package use_case;

import entity.MenuItem;
import interface_adaptor.menu.MenuState;
import interface_adaptor.menu.MenuViewModel;
import interface_adaptor.menu.ViewMenuPresenter;
import interface_adaptor.ViewManagerModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import use_case.view_menu.ViewMenuOutputBoundary;
import use_case.view_menu.ViewMenuOutputData;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ViewMenuPresenterTest {

    @Test
    void prepareSuccessView_updatesStateAndViewManager() {
        MenuViewModel menuViewModel = new MenuViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        ViewMenuOutputBoundary presenter = new ViewMenuPresenter(menuViewModel, viewManagerModel);

        JSONArray items = new JSONArray();
        items.put(new JSONObject()
                .put("title", "Item A")
                .put("price", 10.5)
                .put("readablePrice", "$10.50"));
        items.put(new JSONObject()
                .put("title", "Item B")
                .put("price", 7.0)
                .put("readablePrice", "$7.00"));

        JSONObject menuJson = new JSONObject().put("menuItems", items);

        ViewMenuOutputData output = new ViewMenuOutputData(
                "My Restaurant",
                "123 Street",
                4.7,
                menuJson
        );

        presenter.prepareSuccessView(output);

        MenuState state = menuViewModel.getState();
        assertEquals("My Restaurant", state.getName());
        assertEquals("123 Street", state.getAddress());
        assertEquals(4.7f, state.getRating());
        ArrayList<MenuItem> menuList = state.getMenuList();
        assertNotNull(menuList);
        assertEquals(2, menuList.size());
        assertEquals("Item A", menuList.get(0).getName());
        assertEquals(10.5f, menuList.get(0).getPrice());
        assertEquals("$10.50", menuList.get(0).getDescription());
        assertNull(state.getReviewError());
        assertEquals("menu", viewManagerModel.getState());
    }

    @Test
    void prepareFailView_setsErrorAndClearsMenu() {
        MenuViewModel menuViewModel = new MenuViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        ViewMenuOutputBoundary presenter = new ViewMenuPresenter(menuViewModel, viewManagerModel);

        MenuState state = menuViewModel.getState();
        ArrayList<MenuItem> existing = new ArrayList<>();
        existing.add(new MenuItem("Old", 1.0f, "old"));
        state.setMenuList(existing);

        presenter.prepareFailView("Error happened");

        MenuState newState = menuViewModel.getState();
        assertNotNull(newState.getMenuList());
        assertEquals(0, newState.getMenuList().size());
        assertEquals("Error happened", newState.getReviewError());
    }

    @Test
    void prepareSuccessView_parsesMultipleItems() {
        MenuViewModel menuViewModel = new MenuViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        ViewMenuPresenter presenter =
                new ViewMenuPresenter(menuViewModel, viewManagerModel);

        JSONObject menuJson = new JSONObject()
                .put("menuItems", new JSONArray()
                        .put(new JSONObject()
                                .put("title", "Item 1")
                                .put("price", 1.0)
                                .put("readablePrice", "$1.00"))
                        .put(new JSONObject()
                                .put("title", "Item 2")
                                .put("price", 2.0)
                                .put("readablePrice", "$2.00")));

        ViewMenuOutputData data = new ViewMenuOutputData(
                "Multi Place",
                "789 Road",
                4.0,
                menuJson
        );

        presenter.prepareSuccessView(data);

        MenuState state = menuViewModel.getState();
        assertEquals(2, state.getMenuList().size());
        assertEquals("Item 1", state.getMenuList().get(0).getName());
        assertEquals("Item 2", state.getMenuList().get(1).getName());
    }

}
package interface_adaptor.Menu;

import entity.MenuItem;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.view_menu.ViewMenuOutputBoundary;
import use_case.view_menu.ViewMenuOutputData;

import java.util.ArrayList;

/**
 * Presenter for the View Menu use case.
 * Converts output data into MenuState for the MenuViewModel.
 */
public class ViewMenuPresenter implements ViewMenuOutputBoundary {

    private final MenuViewModel menuViewModel;

    public ViewMenuPresenter(MenuViewModel menuViewModel) {
        this.menuViewModel = menuViewModel;
    }

    @Override
    public void prepareSuccessView(ViewMenuOutputData outputData) {
        MenuState state = menuViewModel.getState();

        // Basic restaurant info
        state.setName(outputData.getRestaurantName());
        state.setAddress(outputData.getRestaurantAddress());
        state.setRating((float) outputData.getRestaurantRating());

        // Parse menu JSON into MenuItem list
        JSONObject menuJson = outputData.getMenuData();
        ArrayList<MenuItem> items = new ArrayList<>();

        if (menuJson != null) {
            // Adjust keys here if MenuServiceForLocalTesting uses different ones
            JSONArray arr = menuJson.optJSONArray("menuItems");
            if (arr != null) {
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    String title = obj.optString("title", "Unnamed item");
                    float price = (float) obj.optDouble("price", 0.0);
                    String description = obj.optString("readablePrice", "");

                    items.add(new MenuItem(title, price, description));
                }
            }
        }

        // Store parsed menu in state so MenuView can show it
        state.setMenuList(items);

        // Clear any previous error
        state.setReviewError(null);

        menuViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        MenuState state = menuViewModel.getState();

        state.setMenuList(new ArrayList<>());
        state.setReviewError(errorMessage);

        menuViewModel.firePropertyChange();
    }
}
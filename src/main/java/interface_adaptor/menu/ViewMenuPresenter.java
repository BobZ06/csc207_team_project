package interface_adaptor.menu;

import entity.MenuItem;
import interface_adaptor.ViewManagerModel;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.view_menu.ViewMenuOutputBoundary;
import use_case.view_menu.ViewMenuOutputData;

import java.util.ArrayList;

public class ViewMenuPresenter implements ViewMenuOutputBoundary {

    private final MenuViewModel menuViewModel;
    private final ViewManagerModel viewManagerModel;

    public ViewMenuPresenter(MenuViewModel menuViewModel, ViewManagerModel viewManagerModel) {
        this.menuViewModel = menuViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(ViewMenuOutputData outputData) {
        MenuState state = menuViewModel.getState();

        state.setName(outputData.getRestaurantName());
        state.setAddress(outputData.getRestaurantAddress());
        state.setRating((float) outputData.getRestaurantRating());

        JSONObject menuJson = outputData.getMenuData();
        ArrayList<MenuItem> items = new ArrayList<>();

        if (menuJson != null) {
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

        state.setMenuList(items);
        state.setReviewError(null);
        menuViewModel.firePropertyChange();

        this.viewManagerModel.setState("menu");
        this.viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        MenuState state = menuViewModel.getState();
        state.setMenuList(new ArrayList<>());
        state.setReviewError(errorMessage);
        menuViewModel.firePropertyChange();
    }
}
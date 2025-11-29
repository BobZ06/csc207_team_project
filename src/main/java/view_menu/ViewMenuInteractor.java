package view_menu;

import org.json.JSONObject;

public class ViewMenuInteractor implements ViewMenuInputBoundary {

    private final ViewMenuDataAccessInterface menuDataAccess;
    private final ViewMenuOutputBoundary presenter;

    public ViewMenuInteractor(ViewMenuDataAccessInterface menuDataAccess,
                              ViewMenuOutputBoundary presenter) {
        this.menuDataAccess = menuDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(ViewMenuInputData inputData) {
        try {
            // Call through the interface, not directly on MenuService
            JSONObject menuJson = menuDataAccess.getRestaurantMenu(
                    inputData.getRestaurantName(),
                    inputData.getZipCode()
            );

            ViewMenuOutputData outputData = new ViewMenuOutputData(
                    inputData.getRestaurantName(),
                    inputData.getRestaurantAddress(),
                    inputData.getRestaurantRating(),
                    menuJson
            );

            presenter.prepareSuccessView(outputData);
        } catch (Exception e) {
            presenter.prepareFailView(e.getMessage());
        }
    }
}
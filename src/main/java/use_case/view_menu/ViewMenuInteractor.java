package use_case.view_menu;

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
        System.out.println("[ViewMenuInteractor] execute called for: " + inputData.getRestaurantName());
        try {
            JSONObject menuJson = menuDataAccess.getRestaurantMenu(
                    inputData.getRestaurantName(),
                    inputData.getZipCode()
            );
            ViewMenuOutputData outputData = new ViewMenuOutputData(
                    inputData.getRestaurantName(),
                    inputData.getRestaurantId(),
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
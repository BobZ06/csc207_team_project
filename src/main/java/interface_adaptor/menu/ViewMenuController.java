package interface_adaptor.menu;

import use_case.view_menu.ViewMenuInputBoundary;
import use_case.view_menu.ViewMenuInputData;

public class ViewMenuController {

    private final ViewMenuInputBoundary interactor;

    public ViewMenuController(ViewMenuInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Trigger the view menu use case.
     *
     * @param restaurantName     name of the restaurant
     * @param zipCode            postal/zip code
     * @param restaurantAddress  address to show in the view
     * @param rating             rating to show in the view
     */
    public void viewMenu(String restaurantName,
                         String zipCode,
                         String restaurantAddress,
                         double rating) {

        ViewMenuInputData inputData = new ViewMenuInputData(
                restaurantName,
                zipCode,
                restaurantAddress,
                rating
        );

        interactor.execute(inputData);
    }
}
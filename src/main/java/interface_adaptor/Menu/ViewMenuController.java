package interface_adaptor.Menu;

import view_menu.ViewMenuInputBoundary;
import view_menu.ViewMenuInputData;

public class ViewMenuController {

    private final ViewMenuInputBoundary interactor;

    public ViewMenuController(ViewMenuInputBoundary interactor) {
        this.interactor = interactor;
    }

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
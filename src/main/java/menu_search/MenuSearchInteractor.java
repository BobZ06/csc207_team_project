package menu_search;

import entity.MenuItem;
import java.util.ArrayList;
import java.util.List;

public class MenuSearchInteractor implements MenuSearchInputBoundary {
    private final MenuSearchDataAccessInterface dataAccess;
    private final MenuSearchOutputBoundary outputBoundary;

    public MenuSearchInteractor(MenuSearchDataAccessInterface dataAccess, MenuSearchOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(MenuSearchInputData inputData) {
        String restaurantID = inputData.getRestaurantID();
        String rQuery = inputData.getQuery();

        String trimmed = (rQuery == null) ? "" : rQuery.trim();
        String query = trimmed.toLowerCase();

        List<MenuItem> menu = dataAccess.getMenu(restaurantID);

        if (query.isEmpty()) {
            MenuSearchOutputData outputData =
                    new MenuSearchOutputData("", menu);
            outputBoundary.prepareSuccessView(outputData);
            return;
        }

        List<MenuItem> matches = new ArrayList<>();

        for (MenuItem item : menu) {
            String name = item.getName() == null ? "" : item.getName().toLowerCase();
            String description = item.getDescription() == null ? "" : item.getDescription().toLowerCase();
            if (name.contains(query) || description.contains(query)) {
                matches.add(item);
            }
        }

        if (matches.isEmpty()) {
            outputBoundary.prepareFailView("Couldn't find the item '" + trimmed + "'");
            return;
        }

        MenuSearchOutputData outputData = new MenuSearchOutputData(trimmed, matches);
        outputBoundary.prepareSuccessView(outputData);
    }
}
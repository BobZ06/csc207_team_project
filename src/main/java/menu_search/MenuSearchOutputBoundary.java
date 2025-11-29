package menu_search;

public interface MenuSearchOutputBoundary {
    void prepareSuccessView(MenuSearchOutputData outputData);
    void prepareFailView(String errorMessage);
}

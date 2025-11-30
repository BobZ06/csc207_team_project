package use_case.view_menu;


public interface ViewMenuOutputBoundary {

    void prepareSuccessView(ViewMenuOutputData outputData);

    void prepareFailView(String errorMessage);
}
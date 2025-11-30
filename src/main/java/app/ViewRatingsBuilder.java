package app;

import interface_adaptor.view_ratings.ViewRatingsController;
import interface_adaptor.view_ratings.ViewRatingsPresenter;
import interface_adaptor.view_ratings.ViewRatingsViewModel;
import use_case.view_ratings.ViewRatingsDataAccessInterface;
import use_case.view_ratings.ViewRatingsInputBoundary;
import use_case.view_ratings.ViewRatingsInteractor;
import use_case.view_ratings.ViewRatingsOutputBoundary;

public class ViewRatingsBuilder {
    private  ViewRatingsBuilder(){}
    public static ViewRatingsController create(
            ViewRatingsDataAccessInterface dataAccessObject,
            ViewRatingsViewModel viewModel) {

        ViewRatingsOutputBoundary presenter = new ViewRatingsPresenter(viewModel);

        ViewRatingsInputBoundary interactor = new ViewRatingsInteractor(
                dataAccessObject, presenter);

        return new ViewRatingsController(interactor);
    }
}

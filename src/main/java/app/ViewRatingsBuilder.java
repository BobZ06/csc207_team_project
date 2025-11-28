package app;

import interface_adaptor.ViewRatings.ViewRatingsController;
import interface_adaptor.ViewRatings.ViewRatingsPresenter;
import interface_adaptor.ViewRatings.ViewRatingsViewModel;
import view_ratings.ViewRatingsDataAccessInterface;
import view_ratings.ViewRatingsInputBoundary;
import view_ratings.ViewRatingsInteractor;
import view_ratings.ViewRatingsOutputBoundary;

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

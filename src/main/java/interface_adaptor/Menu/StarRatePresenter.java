package interface_adaptor.Menu;

import interface_adaptor.ViewManagerModel;
import star_rate.StarRateOutputBoundary;
import star_rate.StarRateOutputData;

public class StarRatePresenter implements StarRateOutputBoundary {
    private final ViewManagerModel viewModel;
    private final MenuViewModel menuModel;

    public StarRatePresenter(ViewManagerModel viewModel, MenuViewModel menuModel){
        this.viewModel = viewModel;
        this.menuModel = menuModel;
    }

    @Override
    public void prepareSuccessView(StarRateOutputData output) {
        final MenuState menuState = menuModel.getState();
        menuState.setRating(output.getAverage());
        menuState.setReviewError("");
        this.menuModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final MenuState menuState = menuModel.getState();
        System.out.println(errorMessage);
        menuState.setReviewError(errorMessage);
        this.menuModel.firePropertyChange();
    }
}

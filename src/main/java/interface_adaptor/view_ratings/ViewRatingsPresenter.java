package interface_adaptor.view_ratings;

import use_case.view_ratings.ViewRatingsOutputBoundary;
import use_case.view_ratings.ViewRatingsOutputData;

public class ViewRatingsPresenter implements ViewRatingsOutputBoundary {
    private final ViewRatingsViewModel viewModel;

    public ViewRatingsPresenter(ViewRatingsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ViewRatingsOutputData response) {
        ViewRatingsState state = viewModel.getState();
        state.setReviews(response.getReviews());
        state.setError(null);

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        ViewRatingsState state = viewModel.getState();
        state.setError(error);
        viewModel.firePropertyChanged();
    }
}

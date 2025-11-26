package app;

import data_access.YelpReviewDataAccessObject;
import interface_adaptor.ViewRatings.ViewRatingsController;
import interface_adaptor.ViewRatings.ViewRatingsState;
import interface_adaptor.ViewRatings.ViewRatingsViewModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class ViewRatingsTest {

    public static void main(String[] args) {
        System.out.println("===Testing View Ratings Use Case ===\n");

        YelpReviewDataAccessObject object = new YelpReviewDataAccessObject();

        ViewRatingsViewModel viewModel = new ViewRatingsViewModel();

        viewModel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("state".equals(evt.getPropertyName())) {
                    ViewRatingsState state = (ViewRatingsState) evt.getNewValue();

                    if (state.getError() != null) {
                        System.out.println("Error:" + state.getError());
                    } else {
                        System.out.println("ViewModel is updated");
                        System.out.println("Comments:");
                        System.out.println("--------------------------------------");
                        List<String> reviews = state.getReviews();
                        for (String review : reviews) {
                            System.out.println(review);
                        }
                        System.out.println("--------------------------------------");
                    }
                }
            }
        });

        ViewRatingsController controller = ViewRatingsBuilder.create(object, viewModel);

        String testRestaurantID = "pai-northern-thai-kitchen-toronto-2";

        System.out.println("Request" + testRestaurantID + "'s reviews");
        controller.execute(testRestaurantID);
    }
}

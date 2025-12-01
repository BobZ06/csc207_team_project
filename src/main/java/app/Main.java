package app;
import data_access.RestaurantSearchService;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String args[]) throws RestaurantSearchService.RestaurantSearchException, IOException {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addBlankView()
                .addLoginView()
                .addSignupView()
                .addSearchView()
                .addMenuView()
                .addLoginUseCase()
                .addLogoutUseCase()
                .addSignupUseCase()
                .addRestaurantSearchUseCase()
                .addStarRateUseCase()
                .addViewMenuUseCase()
                .addViewRatingsUseCase()
                .build();
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
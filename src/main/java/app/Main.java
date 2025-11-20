package app;
import data_access.RestaurantSearchService;

import javax.swing.*;

public class Main {
    public static void main(String args[]) throws RestaurantSearchService.RestaurantSearchException {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addBlankView()
                .addLoginView()
                .addMenuView()
                .addLoginUseCase()
                .addStarRateUseCase()
                .build();
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }

}

package app;
import data_access.RestaurantSearchService;

import javax.swing.*;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String args[]) throws RestaurantSearchService.RestaurantSearchException, FileNotFoundException {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addBlankView()
                .addLoginView()
                .addSignupView()
                .addMenuView()
                .addLoginUseCase()
                .addSignupUseCase()
                .addStarRateUseCase()
                .build();
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}

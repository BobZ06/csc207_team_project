package use_case;

import org.junit.jupiter.api.Test;
import interface_adaptor.menu.ViewMenuController;
import use_case.view_menu.ViewMenuInputBoundary;
import use_case.view_menu.ViewMenuInputData;

import static org.junit.jupiter.api.Assertions.*;

class ViewMenuControllerTest {

    private static class StubInteractor implements ViewMenuInputBoundary {
        ViewMenuInputData lastInput;

        @Override
        public void execute(ViewMenuInputData inputData) {
            lastInput = inputData;
        }
    }

    @Test
    void viewMenu_buildsInputDataAndDelegates() {
        StubInteractor interactor = new StubInteractor();
        ViewMenuController controller = new ViewMenuController(interactor);

        controller.viewMenu("Name", "M5S2E4", "123 Street", 3.5);

        assertNotNull(interactor.lastInput);
        assertEquals("Name", interactor.lastInput.getRestaurantName());
        assertEquals("M5S2E4", interactor.lastInput.getZipCode());
        assertEquals("123 Street", interactor.lastInput.getRestaurantAddress());
        assertEquals(3.5, interactor.lastInput.getRestaurantRating());
    }
}
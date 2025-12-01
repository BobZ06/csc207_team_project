package use_case;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import use_case.view_menu.ViewMenuDataAccessInterface;
import use_case.view_menu.ViewMenuInputBoundary;
import use_case.view_menu.ViewMenuInputData;
import use_case.view_menu.ViewMenuOutputBoundary;
import use_case.view_menu.ViewMenuOutputData;
import use_case.view_menu.ViewMenuInteractor;

import static org.junit.jupiter.api.Assertions.*;

class ViewMenuInteractorTest {

    private static class StubMenuDAO implements ViewMenuDataAccessInterface {
        String lastName;
        String lastZip;
        JSONObject toReturn;
        RuntimeException toThrow;

        @Override
        public JSONObject getRestaurantMenu(String restaurantName, String zipCode) throws Exception {
            lastName = restaurantName;
            lastZip = zipCode;
            if (toThrow != null) {
                throw toThrow;
            }
            return toReturn;
        }
    }

    private static class StubPresenter implements ViewMenuOutputBoundary {
        ViewMenuOutputData lastSuccess;
        String lastError;

        @Override
        public void prepareSuccessView(ViewMenuOutputData outputData) {
            lastSuccess = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            lastError = errorMessage;
        }
    }

    @Test
    void execute_success_callsDaoAndPresenter() {
        StubMenuDAO dao = new StubMenuDAO();
        dao.toReturn = new JSONObject().put("menuItems", new JSONArray());
        StubPresenter presenter = new StubPresenter();
        ViewMenuInteractor interactor = new ViewMenuInteractor(dao, presenter);

        ViewMenuInputData input = new ViewMenuInputData(
                "Test Restaurant",
                "REST-ID",
                "12345",
                "123 Street",
                4.2
        );

        interactor.execute(input);

        assertEquals("Test Restaurant", dao.lastName);
        assertEquals("12345", dao.lastZip);

        assertNotNull(presenter.lastSuccess);
        assertNull(presenter.lastError);

        assertEquals("Test Restaurant", presenter.lastSuccess.getRestaurantName());
        assertEquals("REST-ID", presenter.lastSuccess.getRestaurantId());
        assertEquals("123 Street", presenter.lastSuccess.getRestaurantAddress());
        assertEquals(4.2, presenter.lastSuccess.getRestaurantRating());
        assertNotNull(presenter.lastSuccess.getMenuData());
    }

    @Test
    void execute_failure_callsFailView() {
        StubMenuDAO dao = new StubMenuDAO();
        dao.toThrow = new RuntimeException("boom");
        StubPresenter presenter = new StubPresenter();
        ViewMenuInteractor interactor = new ViewMenuInteractor(dao, presenter);

        ViewMenuInputData input = new ViewMenuInputData(
                "Bad Restaurant",
                "REST-BAD",
                "00000",
                "Nowhere",
                1.0
        );

        interactor.execute(input);

        assertNull(presenter.lastSuccess);
        assertNotNull(presenter.lastError);
        assertTrue(presenter.lastError.contains("boom"));
    }

    @Test
    void execute_passesExactlySameRestaurantFieldsToOutput() {
        StubMenuDAO dao = new StubMenuDAO();
        dao.toReturn = new JSONObject().put("menuItems", new JSONArray());
        StubPresenter presenter = new StubPresenter();
        ViewMenuInputBoundary interactor = new ViewMenuInteractor(dao, presenter);

        ViewMenuInputData input = new ViewMenuInputData(
                "Name With Spaces & #",
                "REST-XYZ",
                "A1B2C3",
                "Unit 5, 123 King St.",
                3.75
        );

        interactor.execute(input);

        assertNotNull(presenter.lastSuccess);
        assertEquals("Name With Spaces & #", presenter.lastSuccess.getRestaurantName());
        assertEquals("REST-XYZ", presenter.lastSuccess.getRestaurantId());
        assertEquals("Unit 5, 123 King St.", presenter.lastSuccess.getRestaurantAddress());
        assertEquals(3.75, presenter.lastSuccess.getRestaurantRating());
    }
}
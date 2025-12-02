package use_case;

import entity.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import use_case.menu_search.MenuSearchDataAccessInterface;
import use_case.menu_search.MenuSearchInputData;
import use_case.menu_search.MenuSearchInteractor;
import use_case.menu_search.MenuSearchOutputBoundary;
import use_case.menu_search.MenuSearchOutputData;

class MenuSearchInteractorTest {

    private MenuSearchDataAccessInterface dataAccess;
    private MenuSearchOutputBoundary outputBoundary;
    private MenuSearchInteractor interactor;

    @BeforeEach
    void setup() {
        dataAccess = mock(MenuSearchDataAccessInterface.class);
        outputBoundary = mock(MenuSearchOutputBoundary.class);
        interactor = new MenuSearchInteractor(dataAccess, outputBoundary);
    }

    @Test
    void testNullQueryReturnsFullMenu() {
        String restaurantID = "123";
        List<MenuItem> menu = Arrays.asList(
                new MenuItem("Burger", 10.0f, "A tasty burger"),
                new MenuItem("Fries", 5.0f, "Crispy fries")
        );
        when(dataAccess.getMenu(restaurantID)).thenReturn(menu);

        MenuSearchInputData inputData = new MenuSearchInputData(restaurantID, null);
        interactor.execute(inputData);

        ArgumentCaptor<MenuSearchOutputData> captor =
                ArgumentCaptor.forClass(MenuSearchOutputData.class);

        verify(outputBoundary).prepareSuccessView(captor.capture());
        MenuSearchOutputData output = captor.getValue();

        assertEquals("", output.getQuery());
        assertEquals(menu, output.getResults());
    }

    @Test
    void testEmptyQueryReturnsFullMenu() {
        String restaurantID = "123";
        List<MenuItem> menu = List.of(
                new MenuItem("Pizza", 9.99f, "Cheese pizza")
        );
        when(dataAccess.getMenu(restaurantID)).thenReturn(menu);

        MenuSearchInputData inputData = new MenuSearchInputData(restaurantID, "   ");
        interactor.execute(inputData);

        ArgumentCaptor<MenuSearchOutputData> captor =
                ArgumentCaptor.forClass(MenuSearchOutputData.class);

        verify(outputBoundary).prepareSuccessView(captor.capture());
        MenuSearchOutputData output = captor.getValue();

        assertEquals("", output.getQuery());
        assertEquals(menu, output.getResults());
    }

    @Test
    void testMatchingQueryReturnsResults() {
        String restaurantID = "123";

        MenuItem item1 = new MenuItem("Chicken Burger", 11.0f, "Delicious");
        MenuItem item2 = new MenuItem("Salad", 7.0f, "Fresh greens");

        when(dataAccess.getMenu(restaurantID)).thenReturn(Arrays.asList(item1, item2));

        MenuSearchInputData inputData = new MenuSearchInputData(restaurantID, "burGer");
        interactor.execute(inputData);

        ArgumentCaptor<MenuSearchOutputData> captor =
                ArgumentCaptor.forClass(MenuSearchOutputData.class);

        verify(outputBoundary).prepareSuccessView(captor.capture());
        MenuSearchOutputData output = captor.getValue();

        assertEquals("burGer", output.getQuery());
        assertEquals(1, output.getResults().size());
        assertEquals(item1, output.getResults().get(0));
    }

    @Test
    void testNoMatchesTriggersFailView() {
        String restaurantID = "123";

        MenuItem item1 = new MenuItem("Pizza", 9.99f, "Cheese pizza");
        when(dataAccess.getMenu(restaurantID)).thenReturn(Collections.singletonList(item1));

        MenuSearchInputData inputData = new MenuSearchInputData(restaurantID, "sushi");
        interactor.execute(inputData);

        verify(outputBoundary).prepareFailView("Couldn't find the item 'sushi'");
    }

    @Test
    void testNullNameAndDescriptionHandledGracefully() {
        String restaurantID = "123";

        MenuItem item = new MenuItem(null, 10.0f, null);
        when(dataAccess.getMenu(restaurantID)).thenReturn(Collections.singletonList(item));

        MenuSearchInputData inputData = new MenuSearchInputData(restaurantID, "x");
        interactor.execute(inputData);

        verify(outputBoundary).prepareFailView("Couldn't find the item 'x'");
    }

    @Test
    void testDescriptionOnlyMatchReturnsResults() {
        String restaurantID = "123";

        MenuItem item1 = new MenuItem("Pizza", 9.99f, "Topped with fresh BURGER sauce");
        MenuItem item2 = new MenuItem("Salad", 7.0f, "Fresh greens");

        when(dataAccess.getMenu(restaurantID)).thenReturn(Arrays.asList(item1, item2));

        MenuSearchInputData inputData = new MenuSearchInputData(restaurantID, "burger");
        interactor.execute(inputData);

        ArgumentCaptor<MenuSearchOutputData> captor =
                ArgumentCaptor.forClass(MenuSearchOutputData.class);

        verify(outputBoundary).prepareSuccessView(captor.capture());
        MenuSearchOutputData output = captor.getValue();

        assertEquals("burger", output.getQuery());
        assertEquals(1, output.getResults().size());
        assertEquals(item1, output.getResults().get(0));
    }

}

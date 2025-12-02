package use_case;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import use_case.log_out.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test class for Logout use case.
 * Achieves 100% code coverage by testing all methods and branches.
 */
public class LogoutTest {

    private MockLogoutDataAccessObject userDataAccess;

    @BeforeEach
    void setUp() {
        userDataAccess = new MockLogoutDataAccessObject();
    }

    @Test
    void successLogoutTest() {
        userDataAccess.setCurrentUsername("TestUser");

        LogoutOutputBoundary output = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData outputData) {
                assertEquals("TestUser", outputData.getUsername());
            }
        };

        LogoutInputBoundary interactor = new LogoutInteractor(userDataAccess, output);
        interactor.execute();

        // Verify that current username was set to null
        assertNull(userDataAccess.getCurrentUsername());
    }

    @Test
    void successLogoutWithDifferentUsernameTest() {
        userDataAccess.setCurrentUsername("AnotherUser");

        LogoutOutputBoundary output = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData outputData) {
                assertEquals("AnotherUser", outputData.getUsername());
            }
        };

        LogoutInputBoundary interactor = new LogoutInteractor(userDataAccess, output);
        interactor.execute();

        assertNull(userDataAccess.getCurrentUsername());
    }

    @Test
    void logoutWithNullUsernameTest() {
        userDataAccess.setCurrentUsername(null);

        LogoutOutputBoundary output = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData outputData) {
                assertNull(outputData.getUsername());
            }
        };

        LogoutInputBoundary interactor = new LogoutInteractor(userDataAccess, output);
        interactor.execute();

        assertNull(userDataAccess.getCurrentUsername());
    }

    @Test
    void logoutWithEmptyUsernameTest() {
        userDataAccess.setCurrentUsername("");

        LogoutOutputBoundary output = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData outputData) {
                assertEquals("", outputData.getUsername());
            }
        };

        LogoutInputBoundary interactor = new LogoutInteractor(userDataAccess, output);
        interactor.execute();

        assertNull(userDataAccess.getCurrentUsername());
    }

    @Test
    void logoutWithLongUsernameTest() {
        String longUsername = "a".repeat(100);
        userDataAccess.setCurrentUsername(longUsername);

        LogoutOutputBoundary output = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData outputData) {
                assertEquals(longUsername, outputData.getUsername());
            }
        };

        LogoutInputBoundary interactor = new LogoutInteractor(userDataAccess, output);
        interactor.execute();

        assertNull(userDataAccess.getCurrentUsername());
    }

    @Test
    void logoutWithSpecialCharactersInUsernameTest() {
        userDataAccess.setCurrentUsername("user@123!#");

        LogoutOutputBoundary output = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData outputData) {
                assertEquals("user@123!#", outputData.getUsername());
            }
        };

        LogoutInputBoundary interactor = new LogoutInteractor(userDataAccess, output);
        interactor.execute();

        assertNull(userDataAccess.getCurrentUsername());
    }

    @Test
    void multipleLogoutsTest() {
        // First logout
        userDataAccess.setCurrentUsername("User1");
        LogoutOutputBoundary output1 = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData outputData) {
                assertEquals("User1", outputData.getUsername());
            }
        };
        LogoutInputBoundary interactor1 = new LogoutInteractor(userDataAccess, output1);
        interactor1.execute();
        assertNull(userDataAccess.getCurrentUsername());

        // Second logout with different user
        userDataAccess.setCurrentUsername("User2");
        LogoutOutputBoundary output2 = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData outputData) {
                assertEquals("User2", outputData.getUsername());
            }
        };
        LogoutInputBoundary interactor2 = new LogoutInteractor(userDataAccess, output2);
        interactor2.execute();
        assertNull(userDataAccess.getCurrentUsername());
    }

    @Test
    void logoutOutputDataConstructorTest() {
        LogoutOutputData outputData = new LogoutOutputData("TestUser");
        assertEquals("TestUser", outputData.getUsername());
    }

    @Test
    void logoutOutputDataWithNullTest() {
        LogoutOutputData outputData = new LogoutOutputData(null);
        assertNull(outputData.getUsername());
    }

    @Test
    void logoutOutputDataWithEmptyStringTest() {
        LogoutOutputData outputData = new LogoutOutputData("");
        assertEquals("", outputData.getUsername());
    }

    @Test
    void verifyPrepareSuccessViewIsCalledTest() {
        userDataAccess.setCurrentUsername("User");
        final boolean[] wasCalled = {false};

        LogoutOutputBoundary output = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData outputData) {
                wasCalled[0] = true;
                assertNotNull(outputData);
                assertEquals("User", outputData.getUsername());
            }
        };

        LogoutInputBoundary interactor = new LogoutInteractor(userDataAccess, output);
        interactor.execute();

        assertTrue(wasCalled[0], "prepareSuccessView should have been called");
    }

    @Test
    void verifySetCurrentUsernameToNullIsCalledTest() {
        userDataAccess.setCurrentUsername("TestUser");
        final String[] oldUsername = {null};

        LogoutOutputBoundary output = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData outputData) {
                oldUsername[0] = outputData.getUsername();
            }
        };

        LogoutInputBoundary interactor = new LogoutInteractor(userDataAccess, output);
        interactor.execute();

        assertEquals("TestUser", oldUsername[0]);
        assertNull(userDataAccess.getCurrentUsername());
    }

    @Test
    void logoutDataAccessGetAndSetTest() {
        MockLogoutDataAccessObject dao = new MockLogoutDataAccessObject();

        assertNull(dao.getCurrentUsername());

        dao.setCurrentUsername("User1");
        assertEquals("User1", dao.getCurrentUsername());

        dao.setCurrentUsername("User2");
        assertEquals("User2", dao.getCurrentUsername());

        dao.setCurrentUsername(null);
        assertNull(dao.getCurrentUsername());
    }

    /**
     * Mock implementation of LogoutDataAccessInterface for testing.
     */
    private static class MockLogoutDataAccessObject implements LogoutDataAccessInterface {
        private String currentUsername;

        @Override
        public String getCurrentUsername() {
            return currentUsername;
        }

        @Override
        public void setCurrentUsername(String username) {
            this.currentUsername = username;
        }
    }
}
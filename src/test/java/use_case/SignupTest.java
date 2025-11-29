package use_case;
import data_access.TempUserDataAccessObject;
import entity.User;
import org.junit.jupiter.api.Test;
import use_case.signup.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SignupTest {
    // Create a new user and add it to the DAO
    User user = new User("Username", "Password");
    SignupDataAccessInterface userData = new TempUserDataAccessObject();

    @Test
    void successTest(){
        SignupInputData input = new SignupInputData("Username", "Password", "Password");
        SignupOutputBoundary output = new SignupOutputBoundary(){

            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                assertEquals("Username", outputData.getUsername());
            }

            @Override
            public void prepareFailView(String errorMessage) {fail("Unexpected output");}

            @Override
            public void switchToLoginView() {fail("Unexpected output");}
        };
        SignupInputBoundary interactor = new SignupInteractor(userData, output);
        interactor.execute(input);
    }

    @Test
    void userExistsFail(){
        userData.save(user);
        SignupInputData input = new SignupInputData("Username", "Password", "Password");
        SignupOutputBoundary output = new SignupOutputBoundary(){

            @Override
            public void prepareSuccessView(SignupOutputData outputData) {fail("Unexpected output");}

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("User already exists.", errorMessage);
            }

            @Override
            public void switchToLoginView() {fail("Unexpected output");}
        };
        SignupInputBoundary interactor = new SignupInteractor(userData, output);
        interactor.execute(input);
    }

    @Test
    void emptyUsernameFail(){
        SignupInputData input = new SignupInputData("", "Password", "Password");
        SignupOutputBoundary output = new SignupOutputBoundary(){

            @Override
            public void prepareSuccessView(SignupOutputData outputData) {fail("Unexpected output");}

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Username cannot be empty", errorMessage);
            }

            @Override
            public void switchToLoginView() {fail("Unexpected output");}
        };
        SignupInputBoundary interactor = new SignupInteractor(userData, output);
        interactor.execute(input);
    }

    @Test
    void emptyPasswordFail(){
        SignupInputData input = new SignupInputData("Username", "", "");
        SignupOutputBoundary output = new SignupOutputBoundary(){

            @Override
            public void prepareSuccessView(SignupOutputData outputData) {fail("Unexpected output");}

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("New password cannot be empty", errorMessage);
            }

            @Override
            public void switchToLoginView() {fail("Unexpected output");}
        };
        SignupInputBoundary interactor = new SignupInteractor(userData, output);
        interactor.execute(input);
    }

    @Test
    void passwordMismatchFail(){
        SignupInputData input = new SignupInputData("Username", "Password", "password");
        SignupOutputBoundary output = new SignupOutputBoundary(){

            @Override
            public void prepareSuccessView(SignupOutputData outputData) {fail("Unexpected output");}

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Passwords don't match.", errorMessage);
            }

            @Override
            public void switchToLoginView() {fail("Unexpected output");}
        };
        SignupInputBoundary interactor = new SignupInteractor(userData, output);
        interactor.execute(input);
    }

    @Test
    void switchToLogin(){
        SignupInputData input = new SignupInputData("Username", "Password", "Password");
        SignupOutputBoundary output = new SignupOutputBoundary(){

            @Override
            public void prepareSuccessView(SignupOutputData outputData) {fail("Unexpected output");}

            @Override
            public void prepareFailView(String errorMessage) {fail("Unexpected output");}

            @Override
            public void switchToLoginView() {}
        };
        SignupInputBoundary interactor = new SignupInteractor(userData, output);
        interactor.switchToLoginView();
    }
}

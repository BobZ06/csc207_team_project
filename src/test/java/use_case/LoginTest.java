package use_case;
import data_access.TempUserDataAccessObject;
import entity.User;
import org.junit.jupiter.api.Test;
import use_case.log_in.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class LoginTest {
    // Create a new user and add it to the DAO
    User user = new User("Username", "Password");
    LoginDataAccessInterface userData = new TempUserDataAccessObject();

    @Test
    void successTest(){
        userData.save(user);
        LoginInputData input = new LoginInputData("Username", "Password");
        LoginOutputBoundary output = new LoginOutputBoundary(){
            @Override
            public void prepareSuccessView(LoginOutputData output) {
                assertEquals("Username", output.getUsername());
            }

            @Override
            public void prepareFailView(String errorMessage) {fail("Unexpected output");}
        };
        LoginInputBoundary interactor = new LoginInteractor(userData, output);
        interactor.execute(input);
    }

    @Test
    void failUsernameTest(){
        userData.save(user);
        LoginInputData input = new LoginInputData("username", "Password");
        LoginOutputBoundary output = new LoginOutputBoundary(){
            @Override
            public void prepareSuccessView(LoginOutputData output) {fail("Unexpected output");}

            @Override
            public void prepareFailView(String errorMessage) {
                // Username is not capitalized, symbolising username not found.
                assertEquals("The username or Password is incorrect.", errorMessage);
            }
        };
        LoginInputBoundary interactor = new LoginInteractor(userData, output);
        interactor.execute(input);
    }
    @Test
    void failPasswordTest(){
        userData.save(user);
        LoginInputData input = new LoginInputData("Username", "password");
        LoginOutputBoundary output = new LoginOutputBoundary(){
            @Override
            public void prepareSuccessView(LoginOutputData output) {fail("Unexpected output");}

            @Override
            public void prepareFailView(String errorMessage) {
                // Password is not capitalized, symbolising password is incorrect.
                assertEquals("The Username or password is incorrect.", errorMessage);
            }
        };
        LoginInputBoundary interactor = new LoginInteractor(userData, output);
        interactor.execute(input);
    }

}

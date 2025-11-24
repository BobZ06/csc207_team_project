package log_in;
import entity.User;

public class LoginInteractor implements LoginInputBoundary{
    // The main Login Use Case.
    private final LoginDataAccessInterface dataAccess;
    private final LoginOutputBoundary outputBoundary;

    public LoginInteractor(LoginDataAccessInterface data, LoginOutputBoundary output){
        this.dataAccess = data;
        this.outputBoundary = output;
    }

    public void execute(LoginInputData input){
        String username = input.getUsername();
        String password = input.getPassword();

        // Checks if username is in the data or not.
        if (dataAccess.existsByName(username)){
            // Gets the User data by the same username. Compares the passwords and if they are equal, Prepare success
            if (password.equals(dataAccess.get(username).getPassword())){
                // Gets the user from the data access and sets data access current to that user.
                // Then, prepare success view.
                User thisUser = dataAccess.get(username);
                dataAccess.setCurrentUsername(username);

                // Create a new output data.
                LoginOutputData outputData = new LoginOutputData(username);
                outputBoundary.prepareSuccessView(outputData);
            }
            // The password is wrong. Prepare fail view.
            else{
                outputBoundary.prepareFailView("The Username or Password is incorrect.");
            }
        }

        // The username is not in the data: Prepare fail view.
        else{
            outputBoundary.prepareFailView("The Username or Password is incorrect.");
        }
    }
}

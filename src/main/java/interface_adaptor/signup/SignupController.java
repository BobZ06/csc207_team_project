package interface_adaptor.Signup;

import sign_up.SignupInputBoundary;
import sign_up.SignupInputData;

public class SignupController {
    private final SignupInputBoundary interactor;

    public SignupController(SignupInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String username, String password) {
        interactor.execute(new SignupInputData(username, password));
    }
}

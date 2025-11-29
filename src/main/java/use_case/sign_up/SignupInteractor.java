package use_case.sign_up;

import entity.User;
import use_case.log_in.LoginDataAccessInterface;

public class SignupInteractor implements SignupInputBoundary {

    private final LoginDataAccessInterface userData;
    private final SignupOutputBoundary presenter;

    public SignupInteractor(LoginDataAccessInterface userData, SignupOutputBoundary presenter) {
        this.userData = userData;
        this.presenter = presenter;
    }

    @Override
    public void execute(SignupInputData inputData) {
        String username = inputData.getUsername();
        String password = inputData.getPassword();

        if (username == null || username.isBlank()) {
            presenter.prepareFailView("Username cannot be empty.");
            return;
        }
        if (password == null || password.isBlank()) {
            presenter.prepareFailView("Password cannot be empty.");
            return;
        }

        if (userData.existsByName(username)) {
            presenter.prepareFailView("Username already exists.");
            return;
        }

        userData.save(new User(username, password));
        presenter.prepareSuccessView(new SignupOutputData(username));
    }
}

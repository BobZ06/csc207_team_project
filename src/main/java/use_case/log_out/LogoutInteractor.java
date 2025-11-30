package use_case.log_out;

public class LogoutInteractor implements LogoutInputBoundary{
    private LogoutDataAccessInterface userDataAccessObject;
    private LogoutOutputBoundary outputBoundary;

    public LogoutInteractor(LogoutDataAccessInterface userDataAccessInterface,
                            LogoutOutputBoundary logoutOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.outputBoundary = logoutOutputBoundary;
    }


    @Override
    public void execute() {
        final String username = userDataAccessObject.getCurrentUsername();
        userDataAccessObject.setCurrentUsername(null);
        final LogoutOutputData logoutOutputData = new LogoutOutputData(username);
        outputBoundary.prepareSuccessView(logoutOutputData);
    }
}
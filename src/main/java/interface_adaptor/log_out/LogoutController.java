package interface_adaptor.log_out;

import use_case.log_out.LogoutInputBoundary;

public class LogoutController {
    final private LogoutInputBoundary inputBoundary;

    public LogoutController(LogoutInputBoundary logoutUseCaseInteractor) {
        this.inputBoundary = logoutUseCaseInteractor;
    }
    public void execute() {
        inputBoundary.execute();
    }
}

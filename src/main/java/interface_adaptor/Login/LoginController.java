package interface_adaptor.Login;
import log_in.LoginInputData;
import log_in.LoginInputBoundary;

public class LoginController {
    private LoginInputBoundary inputBoundary;
    public LoginController(LoginInputBoundary input){
        this.inputBoundary = input;
    }
    public void execute(String username, String password){
        LoginInputData inputData = new LoginInputData(username, password);
        inputBoundary.execute(inputData);
    }
}
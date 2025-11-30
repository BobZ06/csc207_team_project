package interface_adaptor.log_in;
import use_case.log_in.LoginInputData;
import use_case.log_in.LoginInputBoundary;

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
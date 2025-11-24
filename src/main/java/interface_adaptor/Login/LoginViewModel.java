package interface_adaptor.Login;
import interface_adaptor.ViewModel;

public class LoginViewModel extends ViewModel<LoginState>{
    public LoginViewModel(){
        super("Login");
        setState(new LoginState());
    }
}

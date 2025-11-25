package interface_adaptor.Signup;

import interface_adaptor.ViewModel;

public class SignupViewModel extends ViewModel<SignupState> {

    public SignupViewModel() {
        super("signup");
        setState(new SignupState());
    }
}

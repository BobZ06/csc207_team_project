package interface_adaptor.Signup;

import interface_adaptor.ViewManagerModel;
import interface_adaptor.ViewModel;

public class SignupViewModel extends ViewModel<SignupState> {

    private ViewManagerModel viewManagerModel;

    public SignupViewModel() {
        super("signup");
        setState(new SignupState());
    }

    public void setViewManagerModel(ViewManagerModel vmm) {
        this.viewManagerModel = vmm;
    }

    public ViewManagerModel getViewManagerModel() {
        return viewManagerModel;
    }
}

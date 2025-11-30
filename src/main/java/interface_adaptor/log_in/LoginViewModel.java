package interface_adaptor.log_in;

import interface_adaptor.ViewManagerModel;
import interface_adaptor.ViewModel;

public class LoginViewModel extends ViewModel<LoginState>{

    private ViewManagerModel viewManagerModel;

    public LoginViewModel(){
        super("Login");
        setState(new LoginState());
    }

    public void setViewManagerModel(ViewManagerModel vmm) {
        this.viewManagerModel = vmm;
    }

    public ViewManagerModel getViewManagerModel() {
        return viewManagerModel;
    }
}

package interface_adaptor.menu;

import interface_adaptor.ViewModel;

public class MenuViewModel extends ViewModel<MenuState> {
    public MenuViewModel(){
        super("Menu");
        setState(new MenuState());
    }
}

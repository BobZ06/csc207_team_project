package interface_adaptor;

public class BlankViewModel extends ViewModel<BlackState>{
    public BlankViewModel(){
        super("BlankState");
        setState(new BlackState());
    }
}

package interface_adaptor.ViewRatings;

import interface_adaptor.ViewModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ViewRatingsViewModel extends ViewModel {
    private ViewRatingsState state = new ViewRatingsState();

    public ViewRatingsViewModel() {
        super("view ratings");
    }

    public void setState(ViewRatingsState state) {
        this.state = state;
    }

    public ViewRatingsState getState() {
        return state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
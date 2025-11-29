package interface_adaptor.Menu;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import interface_adaptor.Menu.ViewMenuController;

public class MenuView extends JPanel implements PropertyChangeListener {


    private final String viewName = "MenuView";
    private final MenuViewModel viewModel;

    private final JLabel restaurantName = new JLabel();
    private final JLabel address = new JLabel();
    private final JLabel rating = new JLabel();
    private final JLabel username = new JLabel();

    private final JList<String> menuList = new JList<>();
    private final JScrollPane scrollPane = new JScrollPane(menuList);

    private StarRateController starRateController;
    private MenuSearchController searchController;
    private ViewMenuController viewMenuController;

    public MenuView(MenuViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(restaurantName);
        add(address);
        add(rating);
        add(username);
        add(scrollPane);
    }

    public String getViewName() {
        return viewName;
    }

    public void setStarRateController(StarRateController controller) {
        this.starRateController = controller;
    }

    public void setMenuSearchController(MenuSearchController controller) {
        this.searchController = controller;
    }

    public void setViewMenuController(ViewMenuController controller) {
        this.viewMenuController = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        var state = viewModel.getState();

        restaurantName.setText("Restaurant: " + state.getName());
        address.setText("Address: " + state.getAddress());
        rating.setText("Rating: " + state.getRating());
        username.setText("User: " + state.getUsername());

        var menu = state.getMenuList();
        DefaultListModel<String> items = new DefaultListModel<>();

        if (menu != null) {
            menu.forEach(item ->
                    items.addElement(item.getName() + " $" + item.getPrice())
            );
        }

        menuList.setModel(items);
    }
}
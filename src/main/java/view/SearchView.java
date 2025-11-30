package view;

import interface_adaptor.RestaurantSearch.RestaurantSearchController;
import interface_adaptor.RestaurantSearch.RestaurantSearchState;
import interface_adaptor.RestaurantSearch.RestaurantSearchViewModel;
import entity.Restaurant;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class SearchView extends JPanel implements PropertyChangeListener {

    private final RestaurantSearchViewModel viewModel;
    private RestaurantSearchController controller;

    private final JTextField addressField = new JTextField(20);
    private final JTextField termField = new JTextField(20);
    private final JButton searchButton = new JButton("Search");

    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> resultsList = new JList<>(listModel);

    public SearchView(RestaurantSearchViewModel vm) {
        this.viewModel = vm;
        viewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(new JLabel("Address:"));
        add(addressField);

        add(new JLabel("Food Term:"));
        add(termField);

        add(searchButton);
        add(new JScrollPane(resultsList));

        searchButton.addActionListener(e ->
                controller.execute(addressField.getText(), termField.getText()));
    }

    public void setController(RestaurantSearchController c) {
        this.controller = c;
    }

    public String getViewName() { return "SearchView"; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        RestaurantSearchState state = viewModel.getState();
        listModel.clear();

        if (state.getError() != null) {
            listModel.addElement("ERROR: " + state.getError());
            return;
        }

        List<Restaurant> results = state.getResults();
        if (results != null) {
            for (Restaurant r : results) {
                listModel.addElement(r.getName() + " - " + r.getAddress());
            }
        }
    }
}

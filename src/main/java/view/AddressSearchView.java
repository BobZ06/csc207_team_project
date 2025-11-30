package view;

import interface_adaptor.RestaurantSearch.RestaurantSearchController;
import interface_adaptor.RestaurantSearch.RestaurantSearchState;
import interface_adaptor.RestaurantSearch.RestaurantSearchViewModel;
import entity.Restaurant;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class AddressSearchView extends JPanel implements PropertyChangeListener {

    private final RestaurantSearchViewModel viewModel;
    private RestaurantSearchController controller;

    private final JTextField addressField = new JTextField(25);
    private final JButton searchButton = new JButton("Search");
    private final JButton menuButton = new JButton("Restaurant Menu");

    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> resultsList = new JList<>(listModel);

    public AddressSearchView(RestaurantSearchViewModel vm) {
        this.viewModel = vm;
        viewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Enter Your Address:");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(10));
        add(title);
        add(Box.createVerticalStrut(5));

        addressField.setMaximumSize(new Dimension(300, 28)); // tek satır, daha küçük
        addressField.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(addressField);
        add(Box.createVerticalStrut(10));

        JPanel buttonRow = new JPanel();
        buttonRow.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
        buttonRow.add(searchButton);
        buttonRow.add(menuButton);
        add(buttonRow);
        add(Box.createVerticalStrut(10));

        resultsList.setBorder(new LineBorder(Color.BLACK, 1));
        add(new JScrollPane(resultsList));

        searchButton.addActionListener(e ->
                controller.execute(addressField.getText(), "food"));

        menuButton.addActionListener(e -> System.out.println("Menu button clicked (attach logic)"));
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
                listModel.addElement(r.getName() + " – " + r.getAddress());
            }
        }
    }
}

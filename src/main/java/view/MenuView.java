package view;

import data_access.RestaurantSearchService;
import interface_adaptor.Menu.MenuState;
import interface_adaptor.Menu.MenuViewModel;
import interface_adaptor.Menu.StarRateController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MenuView extends JPanel implements ActionListener, PropertyChangeListener{
    private final String viewName = "menu";
    private final MenuViewModel menuViewModel;

    // Star Rate UI Items
    private final JLabel averageRatingField = new JLabel("Average Rating: 0");
    private StarRateController starRateController = null;
    private final JButton rate;
    private final JRadioButton star1, star2, star3, star4, star5;

    // Restaurant Information UI Items
    private final JLabel restaurantName;
    private final JLabel address;
    private final JList<String> menuItems = new JList<>();
    private final JScrollPane scrollPane = new JScrollPane(menuItems);

    // Use case 3 - menu search
    private interface_adaptor.Menu.MenuSearchController menuSearchController;
    private final JTextField searchField = new JTextField(15);
    private final JButton searchButton = new JButton("Search");


    public MenuView(MenuViewModel menuViewModel){
        this.menuViewModel = menuViewModel;
        this.menuViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Restaurant Menu");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        averageRatingField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Initialize the new buttons and add them into a JPanel
        final JPanel buttons = new JPanel();
        rate = new JButton("Rate");
        rate.setAlignmentX(Component.CENTER_ALIGNMENT);

        star1 = new JRadioButton("1"); star2 = new JRadioButton("2");
        star3 = new JRadioButton("3"); star4 = new JRadioButton("4"); star5 = new JRadioButton("5");
        final ButtonGroup group = new ButtonGroup();
        group.add(star1); group.add(star2); group.add(star3); group.add(star4); group.add(star5);

        buttons.add(star1); buttons.add(star2); buttons.add(star3); buttons.add(star4); buttons.add(star5);

        // Initialize the Restaurant information
        final JPanel restaurantInfo = new JPanel();
        restaurantName = new JLabel("Restaurant Name"); address = new JLabel("Address");
        restaurantInfo.add(restaurantName); restaurantInfo.add(address);

        rate.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent evt){
                        int userInputRating = 0;
                        if (star1.isSelected()){
                            userInputRating = 1;
                        }
                        else if (star2.isSelected()){
                            userInputRating = 2;
                        }
                        else if (star3.isSelected()){
                            userInputRating = 3;
                        }
                        else if (star4.isSelected()){
                            userInputRating = 4;
                        }
                        else if (star5.isSelected()){
                            userInputRating = 5;
                        }

                        final MenuState currentState = menuViewModel.getState();

                        // Important to remove this!!!


                        try {
                            starRateController.execute(userInputRating, currentState.getRestaurantId());
                        } catch (RestaurantSearchService.RestaurantSearchException e) {
                            throw new RuntimeException(e);
                        }


                    }
                }
        );

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (menuSearchController == null) {
                    System.out.println("menuSearchController is null");
                    return;
                }
                MenuState currState = menuViewModel.getState();
                String restaurantID = currState.getRestaurantId();
                String query = searchField.getText();
                System.out.println("Search clicked, restaurantId=" + restaurantID + ", query=" + query);
                menuSearchController.execute(restaurantID, query);
            }
        });

        // Search panel (Use case 3)
        final JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search item:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(restaurantInfo);
        this.add(searchPanel);
        this.add(scrollPane);
        this.add(buttons);
        this.add(averageRatingField);
        this.add(rate);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final MenuState state = (MenuState) evt.getNewValue();
        setFields(state);
    }

    private void setFields(MenuState state) {
        averageRatingField.setText("Average Rating: "+String.valueOf(state.getRating()));
        restaurantName.setText(state.getName());
        address.setText(state.getAddress());

        java.util.ArrayList<entity.MenuItem> menu = state.getMenuList();
        DefaultListModel<String> listModel = new DefaultListModel<>();

        if (menu != null) {
            for (entity.MenuItem item : menu) {
                listModel.addElement(item.getName() + " - $" + item.getPrice()
                + " | " + item.getDescription());
            }
        }
        menuItems.setModel(listModel);
    }
    public String getViewName() {
        return viewName;
    }

    public void setStarRateController(StarRateController rateController){this.starRateController = rateController;}

    public void setMenuSearchController(interface_adaptor.Menu.MenuSearchController menuSearchController) {
        this.menuSearchController = menuSearchController;
    }

}



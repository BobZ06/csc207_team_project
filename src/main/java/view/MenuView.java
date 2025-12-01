package view;

import data_access.RestaurantSearchService;
import interface_adaptor.menu.MenuState;
import interface_adaptor.menu.MenuViewModel;
import interface_adaptor.menu.StarRateController;
import interface_adaptor.menu.ViewMenuController;
import interface_adaptor.view_ratings.ViewRatingsController;
import interface_adaptor.view_ratings.ViewRatingsState;
import interface_adaptor.view_ratings.ViewRatingsViewModel;
import interface_adaptor.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MenuView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "menu";
    private final MenuViewModel menuViewModel;
    private ViewManagerModel viewManagerModel;

    // View Ratings Components
    private ViewRatingsController viewRatingsController;
    private ViewRatingsViewModel viewRatingsViewModel;
    private final JButton viewReviews;

    // Star Rate UI Items
    private final JLabel averageRatingField = new JLabel("Average Rating: 0");
    private StarRateController starRateController = null;
    private final JButton rate;
    private final JRadioButton star1, star2, star3, star4, star5;
    private final JLabel reviewErrorField = new JLabel();

    // Restaurant Information UI Items
    private final JLabel restaurantName;
    private final JLabel address;
    private final JList<String> menuItems = new JList<>();
    private final JScrollPane scrollPane = new JScrollPane(menuItems);

    // Use case 3 - menu search
    private interface_adaptor.menu.MenuSearchController menuSearchController;
    private final JTextField searchField = new JTextField(15);
    private final JButton searchButton = new JButton("Search");
    private final JButton backButton = new JButton("← Back to Search");
    private ViewMenuController viewMenuController;

    // User information
    private final JLabel username = new JLabel("Signed in as: ");

    public MenuView(MenuViewModel menuViewModel) {
        this.menuViewModel = menuViewModel;
        this.menuViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Restaurant Menu");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        averageRatingField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Initialize buttons
        final JPanel buttons = new JPanel();
        rate = new JButton("Rate");
        rate.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Initialize View Reviews Button
        viewReviews = new JButton("See Reviews");
        viewReviews.setAlignmentX(Component.CENTER_ALIGNMENT);

        star1 = new JRadioButton("1");
        star2 = new JRadioButton("2");
        star3 = new JRadioButton("3");
        star4 = new JRadioButton("4");
        star5 = new JRadioButton("5");
        final ButtonGroup group = new ButtonGroup();
        group.add(star1);
        group.add(star2);
        group.add(star3);
        group.add(star4);
        group.add(star5);

        buttons.add(star1);
        buttons.add(star2);
        buttons.add(star3);
        buttons.add(star4);
        buttons.add(star5);

        // Initialize the Restaurant information
        final JPanel restaurantInfo = new JPanel();
        restaurantName = new JLabel("Restaurant Name");
        address = new JLabel("Address");
        restaurantInfo.add(restaurantName);
        restaurantInfo.add(address);

        // Rate Button Listener
        rate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                int userInputRating = 0;
                if (star1.isSelected()) userInputRating = 1;
                else if (star2.isSelected()) userInputRating = 2;
                else if (star3.isSelected()) userInputRating = 3;
                else if (star4.isSelected()) userInputRating = 4;
                else if (star5.isSelected()) userInputRating = 5;

                final MenuState currentState = menuViewModel.getState();
                try {
                    starRateController.execute(userInputRating, currentState.getRestaurantId());
                } catch (RestaurantSearchService.RestaurantSearchException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // View Reviews Button Listener
        viewReviews.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuState currentState = menuViewModel.getState();
                String restaurantId = currentState.getRestaurantId();
                if (viewRatingsController != null && restaurantId != null && !restaurantId.isEmpty()) {
                    viewRatingsController.execute(restaurantId);
                } else {
                    JOptionPane.showMessageDialog(MenuView.this, "Cannot fetch reviews: Missing Controller or ID");
                }
            }
        });

        // Search Button Listener
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (menuSearchController == null) {
                    System.out.println("menuSearchController is null");
                    return;
                }
                MenuState currState = menuViewModel.getState();
                String restaurantName = currState.getName();
                String query = searchField.getText();
                menuSearchController.execute(restaurantName, query);
            }
        });

        // Back Button Listener
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                System.out.println("Back button clicked!");
                System.out.println("viewManagerModel is null: " + (viewManagerModel == null));
                if (viewManagerModel != null) {
                    System.out.println("Setting state to SearchView");
                    viewManagerModel.setState("SearchView");
                    viewManagerModel.firePropertyChange();
                    System.out.println("State changed");
                } else {
                    System.out.println("ViewManagerModel is NULL!");
                }
            }
        });

        // Search panel
        final JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search item:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Back button panel
        final JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.add(backButton);

        JPanel topUserPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topUserPanel.add(username);
        topUserPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Bottom buttons panel
        JPanel bottomButtons = new JPanel();
        bottomButtons.add(rate);
        bottomButtons.add(viewReviews);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(backPanel);
        this.add(title);
        this.add(topUserPanel);
        this.add(restaurantInfo);
        this.add(searchPanel);
        this.add(scrollPane);
        this.add(buttons);
        this.add(averageRatingField);
        this.add(bottomButtons);
        this.add(reviewErrorField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Handle ViewRatingsState changes
        if (evt.getNewValue() instanceof ViewRatingsState) {
            ViewRatingsState state = (ViewRatingsState) evt.getNewValue();
            if (state.getError() != null) {
                JOptionPane.showMessageDialog(this, "Error fetching reviews: " + state.getError());
            } else if (state.getReviews() != null && !state.getReviews().isEmpty()) {
                String allReviews = String.join("\n\n", state.getReviews());
                JTextArea textArea = new JTextArea(allReviews);
                textArea.setRows(10);
                textArea.setColumns(40);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                JOptionPane.showMessageDialog(this, scrollPane, "Yelp Reviews", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No reviews found for this restaurant.");
            }
        }
        // Handle MenuState changes
        else if (evt.getNewValue() instanceof MenuState) {
            final MenuState state = (MenuState) evt.getNewValue();
            setFields(state);
        }
    }

    private void setFields(MenuState state) {
        averageRatingField.setText("Average Rating: " + String.valueOf(state.getRating()));
        restaurantName.setText(state.getName());
        address.setText(state.getAddress());
        username.setText("Signed in as: " + state.getUsername());
        reviewErrorField.setText(state.getReviewError());

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

    public void setStarRateController(StarRateController rateController) {
        this.starRateController = rateController;
    }

    public void setMenuSearchController(interface_adaptor.menu.MenuSearchController menuSearchController) {
        this.menuSearchController = menuSearchController;
    }

    public void setViewMenuController(ViewMenuController viewMenuController) {
        this.viewMenuController = viewMenuController;
    }

    public void setViewRatingsController(ViewRatingsController controller) {
        this.viewRatingsController = controller;
    }

    public void setViewRatingsViewModel(ViewRatingsViewModel viewModel) {
        this.viewRatingsViewModel = viewModel;
        this.viewRatingsViewModel.addPropertyChangeListener(this);
    }

    public void setViewManagerModel(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }
}
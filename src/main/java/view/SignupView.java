package view;

import interface_adaptor.Signup.SignupController;
import interface_adaptor.Signup.SignupViewModel;
import interface_adaptor.Signup.SignupState;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SignupView extends JPanel implements PropertyChangeListener {

    private final SignupViewModel viewModel;
    private SignupController controller;

    private final JTextField usernameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);
    private final JLabel messageLabel = new JLabel("");

    public SignupView(SignupViewModel vm) {
        this.viewModel = vm;
        vm.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel title = new JLabel("Sign Up");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        userPanel.add(new JLabel("Username:"));
        userPanel.add(usernameField);

        JPanel passPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        passPanel.add(new JLabel("Password:"));
        passPanel.add(passwordField);

        JButton signupButton = new JButton("Create Account");
        signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupButton.setPreferredSize(new Dimension(150, 30));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.setPreferredSize(new Dimension(150, 30));


        signupButton.addActionListener(e -> {
            controller.execute(
                    usernameField.getText(),
                    new String(passwordField.getPassword())
            );
        });

        cancelButton.addActionListener(e -> {
            viewModel.getViewManagerModel().setState("log in");
            viewModel.getViewManagerModel().firePropertyChange();
        });

        messageLabel.setForeground(Color.RED);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(title);
        this.add(Box.createVerticalStrut(20));
        this.add(userPanel);
        this.add(passPanel);
        this.add(Box.createVerticalStrut(20));
        this.add(signupButton);
        this.add(Box.createVerticalStrut(10));
        this.add(cancelButton);
        this.add(Box.createVerticalStrut(10));
        this.add(messageLabel);
        this.add(Box.createVerticalGlue());
    }

    public void setSignupController(SignupController controller) {
        this.controller = controller;
    }

    public String getViewName() {
        return "signup";
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SignupState state = viewModel.getState();
        messageLabel.setText(state.getMessage());
    }
}

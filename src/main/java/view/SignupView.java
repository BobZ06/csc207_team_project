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

        JLabel title = new JLabel("Sign Up");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel userPanel = new JPanel();
        userPanel.add(new JLabel("Username:"));
        userPanel.add(usernameField);

        JPanel passPanel = new JPanel();
        passPanel.add(new JLabel("Password:"));
        passPanel.add(passwordField);

        JButton signupButton = new JButton("Create Account");
        signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupButton.addActionListener(e -> {
            controller.execute(
                    usernameField.getText(),
                    new String(passwordField.getPassword())
            );
        });

        messageLabel.setForeground(Color.RED);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(title);
        add(userPanel);
        add(passPanel);
        add(signupButton);
        add(messageLabel);
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

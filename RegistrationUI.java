package com.booking.ui;

import com.booking.model.User;
import com.booking.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationUI extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private UserService userService;

    public RegistrationUI() {
        setTitle("Registration");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        userService = new UserService();

        nameField = new JTextField(20);
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        registerButton = new JButton("Register");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword());

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(RegistrationUI.this, "Please fill in all fields.");
                    return;
                }

                User newUser = new User(0, name, email, password, "user"); // Assuming 'role' is set to 'user'
                boolean success = userService.registerUser(newUser);

                if (success) {
                    JOptionPane.showMessageDialog(RegistrationUI.this, "Registration successful!");
                    dispose(); // Close registration window after successful registration
                } else {
                    JOptionPane.showMessageDialog(RegistrationUI.this, "Registration failed. Please try again.");
                }
            }
        });

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(registerButton);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegistrationUI registrationUI = new RegistrationUI();
            registrationUI.setVisible(true);
        });
    }
}

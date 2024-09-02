
package com.booking.ui;

import com.booking.model.User;
import com.booking.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private UserService userService;

    public LoginUI() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        userService = new UserService();

        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword());

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginUI.this, "Please enter email and password.");
                    return;
                }

                User authenticatedUser = userService.authenticate(email, password);

                if (authenticatedUser != null) {
                    if ("admin".equals(authenticatedUser.getRole())) {
                        JOptionPane.showMessageDialog(LoginUI.this, "Admin Login successful!");
                        dispose(); // Close the login window
                        AdminUI adminUI = new AdminUI();
                        adminUI.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(LoginUI.this, "User Login successful!");
                        dispose(); // Close the login window
                        UserUI userUI = new UserUI(authenticatedUser); // Open User Dashboard
                        userUI.setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(LoginUI.this, "Invalid email or password. Please try again.");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegistration();
            }
        });

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);
    }

    private void openRegistration() {
        RegistrationUI registrationUI = new RegistrationUI();
        registrationUI.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginUI loginUI = new LoginUI();
            loginUI.setVisible(true);
        });
    }
}


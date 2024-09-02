package com.booking.ui;

import com.booking.model.User;
import com.booking.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminUI extends JFrame {
    private UserService userService = new UserService();

    private JList<User> userList;
    private DefaultListModel<User> listModel;

    public AdminUI() {
        setTitle("Admin Panel");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        add(panel);

        listModel = new DefaultListModel<>();
        userList = new JList<>(listModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(userList);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton refreshButton = new JButton("Refresh");
        JButton addButton = new JButton("Add User");
        JButton updateButton = new JButton("Update User");
        JButton deleteButton = new JButton("Delete User");

        buttonPanel.add(refreshButton);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> refreshUserList());

        addButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(AdminUI.this, "Enter user name:");
            if (name != null && !name.isEmpty()) {
                String email = JOptionPane.showInputDialog(AdminUI.this, "Enter user email:");
                if (email != null && !email.isEmpty()) {
                    String password = JOptionPane.showInputDialog(AdminUI.this, "Enter user password:");
                    if (password != null && !password.isEmpty()) {
                        User newUser = new User(0, name, email, password, "admin");
                        boolean success = userService.registerUser(newUser);
                        if (success) {
                            refreshUserList();
                            JOptionPane.showMessageDialog(AdminUI.this, "User added successfully!");
                        } else {
                            JOptionPane.showMessageDialog(AdminUI.this, "Failed to add user.");
                        }
                    }
                }
            }
        });

        updateButton.addActionListener(e -> {
            User selectedUser = userList.getSelectedValue();
            if (selectedUser != null) {
                String newName = JOptionPane.showInputDialog(AdminUI.this, "Enter new name:", selectedUser.getName());
                if (newName != null && !newName.isEmpty()) {
                    String newEmail = JOptionPane.showInputDialog(AdminUI.this, "Enter new email:", selectedUser.getEmail());
                    if (newEmail != null && !newEmail.isEmpty()) {
                        String newPassword = JOptionPane.showInputDialog(AdminUI.this, "Enter new password:", selectedUser.getPassword());
                        if (newPassword != null && !newPassword.isEmpty()) {
                            selectedUser.setName(newName);
                            selectedUser.setEmail(newEmail);
                            selectedUser.setPassword(newPassword);
                            boolean success = userService.updateUser(selectedUser);
                            if (success) {
                                refreshUserList();
                                JOptionPane.showMessageDialog(AdminUI.this, "User updated successfully!");
                            } else {
                                JOptionPane.showMessageDialog(AdminUI.this, "Failed to update user.");
                            }
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(AdminUI.this, "Please select a user to update.");
            }
        });

        deleteButton.addActionListener(e -> {
            User selectedUser = userList.getSelectedValue();
            if (selectedUser != null) {
                int option = JOptionPane.showConfirmDialog(AdminUI.this, "Are you sure you want to delete this user?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    boolean success = userService.deleteUser(selectedUser.getId());
                    if (success) {
                        refreshUserList();
                        JOptionPane.showMessageDialog(AdminUI.this, "User deleted successfully!");
                    } else {
                        JOptionPane.showMessageDialog(AdminUI.this, "Failed to delete user.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(AdminUI.this, "Please select a user to delete.");
            }
        });

        refreshUserList();
    }

    private void refreshUserList() {
        List<User> users = userService.getAllUsers();
        listModel.clear();
        for (User user : users) {
            listModel.addElement(user);
        }
    }

    public static void main(String[] args) {
        // Example of usage (typically, AdminUI is opened from LoginUI or MainAppUI)
        SwingUtilities.invokeLater(() -> {
            AdminUI adminUI = new AdminUI();
            adminUI.setVisible(true);
        });
    }
}

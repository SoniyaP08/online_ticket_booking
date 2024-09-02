package com.booking.ui;

import com.booking.model.Booking;
import com.booking.model.User;
import com.booking.service.BookingService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserUI extends JFrame {
    private BookingService bookingService = new BookingService();
    private User user;
    private JList<Booking> bookingList;
    private DefaultListModel<Booking> listModel;

    public UserUI(User user) {
        this.user = user;

        setTitle("User Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        add(panel);

        listModel = new DefaultListModel<>();
        bookingList = new JList<>(listModel);
        bookingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(bookingList);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh");
        JButton bookButton = new JButton("Book Ticket");
        JButton viewButton = new JButton("View Details");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(refreshButton);
        buttonPanel.add(bookButton);
        buttonPanel.add(viewButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> refreshBookings());
        bookButton.addActionListener(e -> {
            BookingUI bookingUI = new BookingUI(user.getId());
            bookingUI.setVisible(true);
            bookingUI.addBookingListener(this::handleBookingCreated);
        });
        viewButton.addActionListener(e -> {
            Booking selectedBooking = bookingList.getSelectedValue();
            if (selectedBooking != null) {
                // Update the status to "successful" locally
                selectedBooking.setStatus("successful");
                
                // Update the status in the database using the BookingService
                boolean success = bookingService.updateBookingStatus(selectedBooking.getId(), "successful");
                
                if (success) {
                    // Display updated details with new status
                    StringBuilder details = new StringBuilder();
                    details.append("Booking ID: ").append(selectedBooking.getId()).append("\n");
                    details.append("Type: ").append(selectedBooking.getType()).append("\n");
                    details.append("Date: ").append(selectedBooking.getDate()).append("\n");
                    details.append("Source: ").append(selectedBooking.getSource()).append("\n");
                    details.append("Destination: ").append(selectedBooking.getDestination()).append("\n");
                    details.append("Amount: $").append(selectedBooking.getAmount()).append("\n");
                    details.append("Status: ").append(selectedBooking.getStatus()).append("\n");

                    JOptionPane.showMessageDialog(UserUI.this, details.toString(), "Booking Details", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Refresh the booking list in UI
                    refreshBookings();
                } else {
                    JOptionPane.showMessageDialog(UserUI.this, "Failed to update status in database. Please try again.", "Update Failed", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(UserUI.this, "Please select a booking to view details.", "No Booking Selected", JOptionPane.WARNING_MESSAGE);
            }
        });





        refreshBookings();
    }

    private void refreshBookings() {
        listModel.clear();
        List<Booking> bookings = bookingService.getBookingsByUserId(user.getId());
        for (Booking booking : bookings) {
            listModel.addElement(booking);
        }
    }

    private void handleBookingCreated(Booking booking) {
        listModel.addElement(booking); // Add the newly created booking to the list
    }

    public static void main(String[] args) {
        // Example of usage (typically, UserUI is opened from LoginUI or MainAppUI)
        SwingUtilities.invokeLater(() -> {
            User testUser = new User(1, "John Doe", "john@example.com", "password", "customer");
            UserUI userUI = new UserUI(testUser);
            userUI.setVisible(true);
        });
    }
}

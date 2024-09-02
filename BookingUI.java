package com.booking.ui;

import javax.swing.*;

import com.booking.model.Booking;
import com.booking.service.BookingService;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class BookingUI extends JFrame {
    private List<BookingListener> bookingListeners = new ArrayList<>();
    private JComboBox<String> typeComboBox;
    private JComboBox<String> amountComboBox;

    public BookingUI(int userId) {
        setTitle("Booking Panel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close to return to main window
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JLabel titleLabel = new JLabel("Booking Panel");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        JLabel typeLabel = new JLabel("Type:");
        typeComboBox = new JComboBox<>(new String[]{"Train", "Bus", "Flight"});
        typeComboBox.addActionListener(e -> updateAmountComboBox());

        JLabel dateLabel = new JLabel("Date (yyyy-mm-dd):");
        JTextField dateField = new JTextField();
        JLabel sourceLabel = new JLabel("Source:");
        JTextField sourceField = new JTextField();
        JLabel destinationLabel = new JLabel("Destination:");
        JTextField destinationField = new JTextField();
        JLabel amountLabel = new JLabel("Amount:");
        amountComboBox = new JComboBox<>(new String[]{"100.00", "200.00", "300.00"}); // Default amounts for Train

        formPanel.add(typeLabel);
        formPanel.add(typeComboBox);
        formPanel.add(dateLabel);
        formPanel.add(dateField);
        formPanel.add(sourceLabel);
        formPanel.add(sourceField);
        formPanel.add(destinationLabel);
        formPanel.add(destinationField);
        formPanel.add(amountLabel);
        formPanel.add(amountComboBox);

        JButton bookButton = new JButton("Book");
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(bookButton, BorderLayout.SOUTH);

        bookButton.addActionListener(e -> {
            String type = (String) typeComboBox.getSelectedItem();
            String date = dateField.getText().trim();
            String source = sourceField.getText().trim();
            String destination = destinationField.getText().trim();
            String amountStr = (String) amountComboBox.getSelectedItem();

            if (type == null || date.isEmpty() || source.isEmpty() || destination.isEmpty() || amountStr == null) {
                JOptionPane.showMessageDialog(BookingUI.this, "Please fill in all fields.");
                return;
            }

            try {
                LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
                double amount = Double.parseDouble(amountStr);
                Booking booking = new Booking(0, userId, type, date, source, destination, amount, "Pending");
                boolean success = new BookingService().addBooking(booking);

                if (success) {
                    for (BookingListener listener : bookingListeners) {
                        listener.onBookingCreated(booking);
                    }
                    JOptionPane.showMessageDialog(BookingUI.this, "Booking successful!");
                    dispose(); // Close the booking window
                } else {
                    JOptionPane.showMessageDialog(BookingUI.this, "Booking failed. Please try again.");
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(BookingUI.this, "Invalid date format. Please use yyyy-mm-dd.");
            }
        });
    }

    private void updateAmountComboBox() {
        String selectedType = (String) typeComboBox.getSelectedItem();
        if (selectedType != null) {
            switch (selectedType) {
                case "Train":
                    amountComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"100.00", "200.00", "300.00"}));
                    break;
                case "Bus":
                    amountComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"50.00", "100.00", "150.00"}));
                    break;
                case "Flight":
                    amountComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"300.00", "500.00", "700.00"}));
                    break;
            }
        }
    }

    public void addBookingListener(BookingListener listener) {
        bookingListeners.add(listener);
    }

    public interface BookingListener {
        void onBookingCreated(Booking booking);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookingUI bookingUI = new BookingUI(1); // Example user ID
            bookingUI.setVisible(true);
        });
    }
}

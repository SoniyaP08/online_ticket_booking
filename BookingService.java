package com.booking.service;

import com.booking.model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/booking_system";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "password"; // Replace with your MySQL password

    public boolean addBooking(Booking booking) {
        String sql = "INSERT INTO bookings (user_id, type, date, source, destination, amount, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, booking.getUserId());
            stmt.setString(2, booking.getType());
            stmt.setDate(3, java.sql.Date.valueOf(booking.getDate()));
            stmt.setString(4, booking.getSource());
            stmt.setString(5, booking.getDestination());
            stmt.setDouble(6, booking.getAmount());
            stmt.setString(7, booking.getStatus());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Booking> getBookingsByUserId(int userId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE user_id = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = new Booking(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("type"),
                        rs.getDate("date").toString(),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getDouble("amount"),
                        rs.getString("status")
                    );
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
    public boolean updateBookingStatus(int bookingId, String newStatus) {
        String sql = "UPDATE bookings SET status = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, bookingId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

package com.booking.model;

public class Booking {
    private int id;
    private int userId;
    private String type; // Bus, Train, Flight
    private String date;
    private String source;
    private String destination;
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private double amount;
    private String status;

    // Constructors, Getters, and Setters
    public Booking(int id, int userId, String type, String date, String source, String destination, double amount, String status) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.date = date;
        this.source = source;
        this.destination = destination;
        this.amount = amount;
        this.status = status;
    }
    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", userId=" + userId +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }


}

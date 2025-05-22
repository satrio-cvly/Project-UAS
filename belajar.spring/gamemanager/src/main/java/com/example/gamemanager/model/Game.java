package com.example.gamemanager.model;

import java.util.UUID;

public class Game {
    protected String id;
    protected String nama;    
    protected String type;
    protected String device;
    protected double rating;  
    protected String status;
    protected double price;
    protected double discountRate;
    
    // Constructor
    public Game() {
        this.id = UUID.randomUUID().toString();
    }
    
    public Game(String nama, String type, String device, double rating, double price, double discountRate) {
        this();
        this.nama = nama;
        this.type = type;
        this.device = device;
        this.rating = rating;
        this.price = price;
        this.discountRate = discountRate;
        setGameStatus(rating);  
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDevice() {
        return this.device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public double getRating() {
        return this.rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
        setGameStatus(rating);
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        } else {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) { 
        if (discountRate >= 0 && discountRate <= 1) { 
            this.discountRate = discountRate;
        } else {
            throw new IllegalArgumentException("Discount rate must be between 0 and 1.");
        }
    }

    public double calculateTotalPrice() {
        double total = price * (1 - discountRate);
        return total;
    }

    protected void setGameStatus(double rating) {
        if (rating >= 4.5) {
            this.status = "Excellent";
        } else if (rating >= 4.0) {
            this.status = "Very Good";
        } else if (rating >= 3.0) {
            this.status = "Good";
        } else {
            this.status = "Average";
        }
    }

    public String getDeviceDescription() {
        switch (this.device.toLowerCase()) {
            case "android":
                return "Mobile Gaming";
            case "pc":
                return "Computer Gaming";
            case "konsole":
                return "Console Gaming";
            default:
                return "Unknown Platform";
        }
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecommendation() {
        if (this.rating >= 4.5) {
            return "Must Play!";
        } else if (this.rating >= 4.0) {
            return "Recommended!";
        } else {
            return "Consider Playing";
        }
    }
}
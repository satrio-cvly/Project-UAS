package com.example.gamemanager.model;

public class PCGame extends Game {
    private String[] systemRequirements;
    private boolean isMultiplayer;
    
    public PCGame() {
        super();
        this.device = "PC";
    }
    
    public PCGame(String nama, String type, double rating, double price, double discountRate,
                String[] systemRequirements, boolean isMultiplayer) {
        super(nama, type, "PC", rating, price, discountRate);
        this.systemRequirements = systemRequirements;
        this.isMultiplayer = isMultiplayer;
    }
    
    public String[] getSystemRequirements() {
        return systemRequirements;
    }
    
    public void setSystemRequirements(String[] systemRequirements) {
        this.systemRequirements = systemRequirements;
    }
    
    public boolean isMultiplayer() {
        return isMultiplayer;
    }
    
    public void setMultiplayer(boolean isMultiplayer) {
        this.isMultiplayer = isMultiplayer;
    }
}
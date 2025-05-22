package com.example.gamemanager.model;

public class ConsoleGame extends Game {
    private String consoleName; // PlayStation, Xbox, Nintendo
    private boolean requiresSubscription;
    
    public ConsoleGame() {
        super();
        this.device = "Konsole";
    }
    
    public ConsoleGame(String nama, String type, double rating, double price, double discountRate,
                    String consoleName, boolean requiresSubscription) {
        super(nama, type, "Konsole", rating, price, discountRate);
        this.consoleName = consoleName;
        this.requiresSubscription = requiresSubscription;
    }
    
    public String getConsoleName() {
        return consoleName;
    }
    
    public void setConsoleName(String consoleName) {
        this.consoleName = consoleName;
    }
    
    public boolean isRequiresSubscription() {
        return requiresSubscription;
    }
    
    public void setRequiresSubscription(boolean requiresSubscription) {
        this.requiresSubscription = requiresSubscription;
    }
    
    @Override
    public double calculateTotalPrice() {
        double basePrice = super.calculateTotalPrice();
        if (requiresSubscription) {
            // Add $5 subscription fee if required
            return basePrice + 5.0;
        }
        return basePrice;
    }
}
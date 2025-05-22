package com.example.gamemanager.model;

public class MobileGame extends Game {
    private String platform; // Android, iOS
    private boolean isFreeToPlay;

    public MobileGame() {
        super();
        this.device = "Android";
    }

    public MobileGame(String nama, String type, double rating, double price, double discountRate, 
                    String platform, boolean isFreeToPlay) {
        super(nama, type, "Android", rating, price, discountRate);
        this.platform = platform;
        this.isFreeToPlay = isFreeToPlay;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public boolean isFreeToPlay() {
        return isFreeToPlay;
    }

    public void setFreeToPlay(boolean isFreeToPlay) {
        this.isFreeToPlay = isFreeToPlay;
    }
}
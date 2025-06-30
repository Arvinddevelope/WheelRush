package com.arvind.wheelrush.models;
public class User {
    private String name;
    private String email;
    private int coins;
    private String referralCode;
    private String lastCheckIn;

    public User() {}

    public User(String name, String email, int coins, String referralCode) {
        this.name = name;
        this.email = email;
        this.coins = coins;
        this.referralCode = referralCode;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getCoins() { return coins; }
    public String getReferralCode() { return referralCode; }
    public String getLastCheckIn() { return lastCheckIn; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setCoins(int coins) { this.coins = coins; }
    public void setReferralCode(String referralCode) { this.referralCode = referralCode; }
    public void setLastCheckIn(String lastCheckIn) { this.lastCheckIn = lastCheckIn; }
}

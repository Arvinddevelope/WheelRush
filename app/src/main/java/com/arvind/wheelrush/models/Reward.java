package com.arvind.wheelrush.models;
public class Reward {
    private int amount;
    private String reason; // e.g. "Spin", "Check-In", "Ad"
    private String date;

    public Reward() {}

    public Reward(int amount, String reason, String date) {
        this.amount = amount;
        this.reason = reason;
        this.date = date;
    }

    public int getAmount() { return amount; }
    public String getReason() { return reason; }
    public String getDate() { return date; }

    public void setAmount(int amount) { this.amount = amount; }
    public void setReason(String reason) { this.reason = reason; }
    public void setDate(String date) { this.date = date; }
}

package com.arvind.wheelrush.models;
public class Transaction {
    private String type; // "Redeem", "Earn", etc.
    private int amount;
    private String date;
    private String status; // "Pending", "Completed"

    public Transaction() {}

    public Transaction(String type, int amount, String date, String status) {
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.status = status;
    }

    public String getType() { return type; }
    public int getAmount() { return amount; }
    public String getDate() { return date; }
    public String getStatus() { return status; }

    public void setType(String type) { this.type = type; }
    public void setAmount(int amount) { this.amount = amount; }
    public void setDate(String date) { this.date = date; }
    public void setStatus(String status) { this.status = status; }
}

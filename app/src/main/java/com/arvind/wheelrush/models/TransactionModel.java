package com.arvind.wheelrush.models;
public class TransactionModel {
    private String type;
    private int amount;
    private String date;

    public TransactionModel() {}

    public TransactionModel(String type, int amount, String date) {
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    public String getType() { return type; }
    public int getAmount() { return amount; }
    public String getDate() { return date; }
}

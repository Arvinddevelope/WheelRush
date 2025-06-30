package com.arvind.wheelrush.models;
public class Referral {
    private String referredBy;
    private String referredTo;
    private String date;

    public Referral() {}

    public Referral(String referredBy, String referredTo, String date) {
        this.referredBy = referredBy;
        this.referredTo = referredTo;
        this.date = date;
    }

    public String getReferredBy() { return referredBy; }
    public String getReferredTo() { return referredTo; }
    public String getDate() { return date; }

    public void setReferredBy(String referredBy) { this.referredBy = referredBy; }
    public void setReferredTo(String referredTo) { this.referredTo = referredTo; }
    public void setDate(String date) { this.date = date; }
}

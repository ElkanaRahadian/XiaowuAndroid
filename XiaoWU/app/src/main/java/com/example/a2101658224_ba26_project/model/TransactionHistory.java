package com.example.a2101658224_ba26_project.model;

public class TransactionHistory {
    private int transactionId;
    private int phoneId;
    private String transactionDate;

    public TransactionHistory(int transactionId, int phoneId, String transactionDate) {
        this.transactionId = transactionId;
        this.phoneId = phoneId;
        this.transactionDate = transactionDate;
    }

    public TransactionHistory(int phoneId, String transactionDate) {
        this.phoneId = phoneId;
        this.transactionDate = transactionDate;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(int phoneId) {
        this.phoneId = phoneId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
}

package com.paj.psd2.aggregator.payload;

import com.paj.psd2.aggregator.client.generated.model.Transaction;

import java.util.List;

public class AccountTransactions {

    private String ownerName;
    private String product;

    private List<Transaction> pendingTransactions;
    private List<Transaction> bookedTransactions;
    private List<Transaction> infoTransactions;

    private Double totalPendingAmount;
    private Double totalBookedAmount;
    private Double totalInfoAmount;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public List<Transaction> getPendingTransactions() {
        return pendingTransactions;
    }

    public void setPendingTransactions(List<Transaction> pendingTransactions) {
        this.pendingTransactions = pendingTransactions;
    }

    public List<Transaction> getBookedTransactions() {
        return bookedTransactions;
    }

    public void setBookedTransactions(List<Transaction> bookedTransactions) {
        this.bookedTransactions = bookedTransactions;
    }

    public List<Transaction> getInfoTransactions() {
        return infoTransactions;
    }

    public void setInfoTransactions(List<Transaction> infoTransactions) {
        this.infoTransactions = infoTransactions;
    }

    public Double getTotalPendingAmount() {
        return totalPendingAmount;
    }

    public void setTotalPendingAmount(Double totalPendingAmount) {
        this.totalPendingAmount = totalPendingAmount;
    }

    public Double getTotalBookedAmount() {
        return totalBookedAmount;
    }

    public void setTotalBookedAmount(Double totalBookedAmount) {
        this.totalBookedAmount = totalBookedAmount;
    }

    public Double getTotalInfoAmount() {
        return totalInfoAmount;
    }

    public void setTotalInfoAmount(Double totalInfoAmount) {
        this.totalInfoAmount = totalInfoAmount;
    }
}

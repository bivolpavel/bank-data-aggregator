package com.paj.psd2.aggregator.payload;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public class ProductSummaryResponse {

    private Double totalBalance;

    private Double totalSpendThisMonth;

    private double averageMonthlySpending;

    private List<AccountBalanceResponse> accountBalanceResponses;

    private Map<String, Double> monthsSpending;

    public Double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Double getTotalSpendThisMonth() {
        return totalSpendThisMonth;
    }

    public void setTotalSpendThisMonth(Double totalSpendThisMonth) {
        this.totalSpendThisMonth = totalSpendThisMonth;
    }

    public double getAverageMonthlySpending() {
        return averageMonthlySpending;
    }

    public void setAverageMonthlySpending(double averageMonthlySpending) {
        this.averageMonthlySpending = averageMonthlySpending;
    }

    public List<AccountBalanceResponse> getAccountBalanceResponses() {
        return accountBalanceResponses;
    }

    public void setAccountBalanceResponses(List<AccountBalanceResponse> accountBalanceResponses) {
        this.accountBalanceResponses = accountBalanceResponses;
    }

    public Map<String, Double> getMonthsSpending() {
        return monthsSpending;
    }

    public void setMonthsSpending(Map<String, Double> monthsSpending) {
        this.monthsSpending = monthsSpending;
    }
}

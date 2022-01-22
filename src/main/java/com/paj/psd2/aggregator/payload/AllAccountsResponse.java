package com.paj.psd2.aggregator.payload;

import java.util.List;
import java.util.Map;

public class AllAccountsResponse {

    private List<AccountBalanceResponse> accountBalanceResponses;

    private Map<String, Double> amountByCurrencies;

    public AllAccountsResponse(List<AccountBalanceResponse> accountBalanceResponses, Map<String, Double> amountByCurrencies) {
        this.accountBalanceResponses = accountBalanceResponses;
        this.amountByCurrencies = amountByCurrencies;
    }

    public List<AccountBalanceResponse> getAccountBalanceResponses() {
        return accountBalanceResponses;
    }

    public void setAccountBalanceResponses(List<AccountBalanceResponse> accountBalanceResponses) {
        this.accountBalanceResponses = accountBalanceResponses;
    }

    public Map<String, Double> getAmountByCurrencies() {
        return amountByCurrencies;
    }

    public void setAmountByCurrencies(Map<String, Double> amountByCurrencies) {
        this.amountByCurrencies = amountByCurrencies;
    }
}

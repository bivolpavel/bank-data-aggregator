package com.paj.psd2.aggregator.payload;

import java.util.ArrayList;
import java.util.List;

public class AllAccountsTransactionsResponses {

    private List<AccountTransactions> accountTransactions;

    private Double totalTransactionsAmount;

    public List<AccountTransactions> getAccountTransactions() {
        return accountTransactions;
    }

    public void setAccountTransactions(List<AccountTransactions> accountTransactions) {
        this.accountTransactions = accountTransactions;
    }

    public void addAccountTransactions(AccountTransactions ac){
        if (accountTransactions == null) {
            accountTransactions = new ArrayList<>();
        }

        accountTransactions.add(ac);
    }

    public Double getTotalTransactionsAmount() {
        return totalTransactionsAmount;
    }

    public void setTotalTransactionsAmount(Double totalTransactionsAmount) {
        this.totalTransactionsAmount = totalTransactionsAmount;
    }

}

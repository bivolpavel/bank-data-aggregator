package com.paj.psd2.aggregator.service;

import com.paj.psd2.aggregator.client.AccountsApiClient;
import com.paj.psd2.aggregator.client.generated.model.Account;
import com.paj.psd2.aggregator.client.generated.model.AccountsResponse;
import com.paj.psd2.aggregator.client.generated.model.Transaction;
import com.paj.psd2.aggregator.client.generated.model.TransactionsResponse;
import com.paj.psd2.aggregator.payload.AccountTransactions;
import com.paj.psd2.aggregator.payload.AllAccountsTransactionsResponses;
import com.paj.psd2.aggregator.web.TransactionsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class TransactionsService {

    private final Logger logger = LoggerFactory.getLogger(TransactionsController.class);

    private final AccountsApiClient accountsApiClient;

    public TransactionsService(@Qualifier("mockAccountsApiClient") AccountsApiClient accountsApiClient) {
        this.accountsApiClient = accountsApiClient;
    }

    public Optional<AllAccountsTransactionsResponses> getTransactionsInfo(){
        logger.debug("getTransactionsInfo()");

        AllAccountsTransactionsResponses allAccountsTransactionsResponses = new AllAccountsTransactionsResponses();

        try {

            Optional<AccountsResponse> accountsResponse = accountsApiClient.getAccounts();

            accountsResponse.ifPresent(ar -> {
                for (Account account : ar.getAccounts()) {
                    this.getTransactions(account)
                            .whenComplete((accountTransactions, throwable) ->
                                    allAccountsTransactionsResponses.addAccountTransactions(accountTransactions));
                }
            });

            allAccountsTransactionsResponses.getAccountTransactions().forEach(accountTransactions -> {

                //  Calculation of the total from info transactions
                if (accountTransactions.getInfoTransactions() != null) {
                    accountTransactions.setTotalInfoAmount(accountTransactions.getInfoTransactions()
                            .stream()
                            .map(transaction -> transaction.getTransactionAmount().getAmount())
                            .reduce(Double::sum)
                            .orElse(null));
                }

                //  Calculation of the total from Booked transactions
                if (accountTransactions.getBookedTransactions() != null) {
                    accountTransactions.setTotalInfoAmount(accountTransactions.getBookedTransactions()
                            .stream()
                            .map(transaction -> transaction.getTransactionAmount().getAmount())
                            .reduce(Double::sum)
                            .orElse(null));
                }

                //  Calculation of the total from Pending transactions
                if (accountTransactions.getPendingTransactions() != null) {
                    accountTransactions.setTotalInfoAmount(accountTransactions.getPendingTransactions()
                            .stream()
                            .map(transaction -> transaction.getTransactionAmount().getAmount())
                            .reduce(Double::sum)
                            .orElse(null));
                }
            });

            return Optional.of(allAccountsTransactionsResponses);
        } catch (Exception e) {
            logger.error("Exception occurred during calling get Accounts endpoint: " ,e);
            return Optional.empty();
        }
    }


    @Async
    private CompletableFuture<AccountTransactions> getTransactions(Account account) {
        logger.debug("getTransactions() for resourceId: {}", account.getResourceId());
        AccountTransactions accountTransactions = new AccountTransactions();

        Optional<TransactionsResponse> transactionsResponse = accountsApiClient
                .getAccountTransactions(account.getResourceId(), account.getCurrency());

        transactionsResponse
                .map(TransactionsResponse::getTransactions)
                .ifPresent(transactions -> {

                    accountTransactions.setOwnerName(account.getName());
                    accountTransactions.setProduct(account.getProduct());

                    if (transactions.getPending() != null && !transactions.getPending().isEmpty()) {
                        logger.debug("We have items in pending transactions list!");
                        accountTransactions.setPendingTransactions(transactions.getPending());
                        accountTransactions.setTotalPendingAmount(
                                calculateTotalAmountForTransactions(transactions.getPending()));
                    }

                    if (transactions.getBooked() != null && !transactions.getBooked().isEmpty()) {
                        logger.debug("We have items in booked transactions list!");
                        accountTransactions.setBookedTransactions(transactions.getBooked());
                        accountTransactions.setTotalBookedAmount(
                                calculateTotalAmountForTransactions(transactions.getBooked()));
                    }

                    if (transactions.getInfo() != null && !transactions.getInfo().isEmpty()) {
                        logger.debug("We have items in info transactions list!");
                        accountTransactions.setInfoTransactions(transactions.getInfo());
                        accountTransactions.setTotalInfoAmount(
                                calculateTotalAmountForTransactions(transactions.getInfo()));
                    }
                });

        return CompletableFuture.completedFuture(accountTransactions);
    }

    public Double calculateTotalAmountForTransactions(List<Transaction> transactions){
        return transactions.stream()
                .map(transaction -> transaction.getTransactionAmount().getAmount())
                .reduce(0D, Double::sum);

    }
}

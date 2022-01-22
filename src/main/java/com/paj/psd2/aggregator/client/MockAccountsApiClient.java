package com.paj.psd2.aggregator.client;

import com.paj.psd2.aggregator.client.generated.model.*;
import com.paj.psd2.aggregator.utils.FileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MockAccountsApiClient implements AccountsApiClient{
    private final Logger logger = LoggerFactory.getLogger(MockAccountsApiClient.class);

    private static final String ACCOUNTS_MOCK_FILE_LOCATION = "mock/accounts.json";
    private static final String ACCOUNT_BALANCES_MOCK_FILE_LOCATION = "mock/accountBalances.json";
    private static final String TRANSACTIONS_MOCK_FILE_LOCATION = "mock/transactions.json";

    @Override
    public Optional<AccountsResponse> getAccounts() {
        logger.debug("getAccounts()");
        AccountsResponse accountsResponse = FileReader
                .readFromFile(ACCOUNTS_MOCK_FILE_LOCATION, AccountsResponse.class);
        return Optional.of(accountsResponse);
    }

    @Override
    public Optional<BalancesResponse> getAccountsBalance(UUID accountId, String currency) {
        logger.debug("getAccounts()");
        BalancesResponse balancesResponse = FileReader
                .readFromFile(ACCOUNT_BALANCES_MOCK_FILE_LOCATION, BalancesResponse.class);
        return Optional.of(balancesResponse);
    }

    @Override
    public Optional<TransactionsResponse> getAccountTransactions(UUID accountId, String currency) {
        logger.debug("getAccountTransactions() for accountId: {}", accountId.toString());
        TransactionsResponse transactionsResponse = FileReader
                .readFromFile(TRANSACTIONS_MOCK_FILE_LOCATION, TransactionsResponse.class);
        return Optional.of(transactionsResponse);
    }

    @Override
    public Optional<CardTransactionsResponse> getAccountsCardTransactions(UUID accountId) {
        return Optional.empty();
    }
}

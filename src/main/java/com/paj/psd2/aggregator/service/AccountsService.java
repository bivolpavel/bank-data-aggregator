package com.paj.psd2.aggregator.service;

import com.paj.psd2.aggregator.client.AccountsApiClient;
import com.paj.psd2.aggregator.client.AccountsApiClientWrapper;
import com.paj.psd2.aggregator.client.generated.model.Account;
import com.paj.psd2.aggregator.mapping.AccountBalanceMapper;
import com.paj.psd2.aggregator.payload.AccountBalanceResponse;
import com.paj.psd2.aggregator.payload.AllAccountsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class AccountsService {

    private final Logger logger = LoggerFactory.getLogger(AccountsApiClientWrapper.class);

    private final AccountsApiClient accountsApiClient;

    private final AccountBalanceMapper accountBalanceMapper;

    public AccountsService(AccountsApiClient accountsApiClient, AccountBalanceMapper accountBalanceMapper) {
        this.accountsApiClient = accountsApiClient;
        this.accountBalanceMapper = accountBalanceMapper;
    }

    public Optional<AllAccountsResponse> getAllAccountBalances() {
        logger.debug("getAllAccountBalances()");
        List<AccountBalanceResponse> accountBalanceResponseList = new ArrayList<>();

        try {
            accountsApiClient.getAccounts().ifPresent(accountsResponse -> {
                for (Account account : accountsResponse.getAccounts()) {
                    this.getAccountBalance(account)
                            .whenComplete((accountBalanceResponse, throwable) -> {
                                if (accountBalanceResponse != null) {
                                    accountBalanceResponseList.add(accountBalanceResponse);
                                    logger.debug("Add to accountBalanceResponseList new entry with resurceId: {}",
                                            accountBalanceResponse.getResourceId());
                                }
                            });
                }
            });
        } catch (Exception e) {
            logger.error("Exception occurred during calling getAccountBalances service: {}", e.getMessage());
            return Optional.empty();
        }

        Map<String, Double> totalAmounts = calculateTotalAmountByCurrency(accountBalanceResponseList);
        logTotalAmountsPerCurrency(totalAmounts);

        return Optional.of(new AllAccountsResponse(accountBalanceResponseList, totalAmounts));
    }


    @Async
    private CompletableFuture<AccountBalanceResponse> getAccountBalance(Account account) {
        logger.debug("getAccountBalance() for resourceId: {}", account.getResourceId());
        return accountsApiClient
                .getAccountsBalance(account.getResourceId(), account.getCurrency())
                .filter(Objects::nonNull)
                .map(br -> accountBalanceMapper
                        .accountAndBalanceToAccountBalanceResponse(account, br.getBalances().get(0)))
                .map(CompletableFuture::completedFuture)
                .orElse(CompletableFuture.completedFuture(null));
    }

    //Grouping by currency, summing the amount and collecting in a map
    private Map<String, Double> calculateTotalAmountByCurrency(List<AccountBalanceResponse> accountBalanceResponseList){
         return accountBalanceResponseList.stream()
                .collect(Collectors.groupingBy(AccountBalanceResponse::getCurrency,
                        Collectors.summingDouble(AccountBalanceResponse::getAmount)));
    }

    private void logTotalAmountsPerCurrency(Map<String, Double> totalAmounts){
        totalAmounts.forEach((key, value) -> logger.debug("Currency: {} - total amount: {}", key, value));
    }

}

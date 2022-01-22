package com.paj.psd2.aggregator.service;

import com.paj.psd2.aggregator.client.generated.model.Amount;
import com.paj.psd2.aggregator.client.generated.model.Transaction;
import com.paj.psd2.aggregator.payload.AccountBalanceResponse;
import com.paj.psd2.aggregator.payload.AllAccountsResponse;
import com.paj.psd2.aggregator.payload.AllAccountsTransactionsResponses;
import com.paj.psd2.aggregator.payload.ProductSummaryResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ProductSummaryService {

    private final TransactionsService transactionsService;

    private final AccountsService accountsService;

    public ProductSummaryService(TransactionsService transactionsService, AccountsService accountsService) {
        this.transactionsService = transactionsService;
        this.accountsService = accountsService;
    }

    public ProductSummaryResponse getProductSummary(String userId) {

        Optional<AllAccountsResponse> accountsResponse = accountsService.getAllAccountBalances();
        Optional<AllAccountsTransactionsResponses> transactionsInfo = transactionsService.getTransactionsInfo();

        Map<String, Double> amountPerMonths = getMonthlySpending(transactionsInfo);

        ProductSummaryResponse productSummaryResponse = new ProductSummaryResponse();
        productSummaryResponse.setTotalBalance(getTotalBalance(accountsResponse));
        productSummaryResponse.setAverageMonthlySpending(getAverageMonthlySpending(amountPerMonths));
        productSummaryResponse.setTotalSpendThisMonth(getThisMonthSpending(transactionsInfo));

        productSummaryResponse.setMonthsSpending(amountPerMonths);
        accountsResponse.ifPresent(allAccountsResponse ->
                productSummaryResponse.setAccountBalanceResponses(
                        allAccountsResponse.getAccountBalanceResponses()));

        return productSummaryResponse;
    }

    private Double getTotalBalance(Optional<AllAccountsResponse> accountsResponse){
        return accountsResponse
                .stream()
                .flatMap(allAccountsResponse -> allAccountsResponse.getAccountBalanceResponses().stream())
                .map(AccountBalanceResponse::getAmount)
                .reduce(Double::sum).orElse(0d);
    }


    private boolean isTransactionFromThisMonth(OffsetDateTime transactionExecutionDate){
        OffsetDateTime currentDateTime = OffsetDateTime.now();
        return transactionExecutionDate.getYear() == currentDateTime.getYear() &&
                transactionExecutionDate.getMonth().compareTo(currentDateTime.getMonth()) == 0;
    }

    private Double getThisMonthSpending(Optional<AllAccountsTransactionsResponses> transactionsInfo){
        Double thisMonthSpending = transactionsInfo
                .stream()
                .flatMap(allAccountsTransactionsResponses -> allAccountsTransactionsResponses.getAccountTransactions().stream())
                .map(accountTransactions -> {

                    Double totalInfoTransactionsThisMonth = Optional
                            .ofNullable(accountTransactions.getInfoTransactions())
                            .stream()
                            .flatMap(Collection::stream)
                            .filter(transaction -> isTransactionFromThisMonth(transaction.getExecutionDateTime()))
                            .map(transaction -> transaction.getTransactionAmount().getAmount())
                            .reduce(Double::sum).orElse(0d);

                    Double totalBookedTransactionsThisMonth = Optional
                            .ofNullable(accountTransactions.getBookedTransactions())
                            .stream()
                            .flatMap(Collection::stream)
                            .filter(transaction -> isTransactionFromThisMonth(transaction.getExecutionDateTime()))
                            .map(transaction -> transaction.getTransactionAmount().getAmount())
                            .reduce(Double::sum).orElse(0d);

                    Double totalPendingTransactionsThisMonth = Optional
                            .ofNullable(accountTransactions.getPendingTransactions())
                            .stream()
                            .flatMap(Collection::stream)
                            .filter(transaction -> isTransactionFromThisMonth(transaction.getExecutionDateTime()))
                            .map(transaction -> transaction.getTransactionAmount().getAmount())
                            .reduce(Double::sum).orElse(0d);

                    return totalInfoTransactionsThisMonth + totalBookedTransactionsThisMonth + totalPendingTransactionsThisMonth;
                })
                .reduce(Double::sum)
                .orElse(0d);

        return BigDecimal.valueOf(thisMonthSpending)
                .setScale(2, RoundingMode.HALF_EVEN)
                .doubleValue();
    }

    private Map<String, Double> getMonthlySpending(Optional<AllAccountsTransactionsResponses> transactionsInfo){

        Map<String, Double> monthlySpending = new LinkedHashMap<>();

        List<Transaction> transactions = transactionsInfo
                .stream()
                .flatMap(allAccountsTransactionsResponses -> allAccountsTransactionsResponses.getAccountTransactions().stream())
                .flatMap(accountTransactions -> {

                    List<Transaction> aggregatedTransactions = new ArrayList<>();

                    Optional.ofNullable(accountTransactions.getInfoTransactions())
                            .ifPresent(aggregatedTransactions::addAll);

                    Optional.ofNullable(accountTransactions.getBookedTransactions())
                            .ifPresent(aggregatedTransactions::addAll);

                    Optional.ofNullable(accountTransactions.getPendingTransactions())
                            .ifPresent(aggregatedTransactions::addAll);

                    return aggregatedTransactions.stream();
                })
                .sorted((o1, o2) -> OffsetDateTime.timeLineOrder().compare(o1.getExecutionDateTime(), o2.getExecutionDateTime()))
                .peek(transaction -> {
                    Amount amount = new Amount();
                    amount.setAmount(transaction.getTransactionAmount().getAmount() * (-1));
                    amount.setCurrency(transaction.getTransactionAmount().getCurrency()) ;
                    transaction.setTransactionAmount(amount);
                })
                .collect(Collectors.toList());

        transactions.forEach(transaction -> {
            monthlySpending.computeIfPresent(
                    transaction.getExecutionDateTime().getMonth().name(),
                    (s, aDouble) -> Double.sum(aDouble, BigDecimal.valueOf(transaction.getTransactionAmount().getAmount())
                            .setScale(2, RoundingMode.HALF_EVEN)
                            .doubleValue()));

            monthlySpending.putIfAbsent(
                    transaction.getExecutionDateTime().getMonth().name(),
                    BigDecimal.valueOf(transaction.getTransactionAmount().getAmount())
                            .setScale(2, RoundingMode.HALF_EVEN)
                            .doubleValue());
        });

        return monthlySpending;
    }

    private Double getAverageMonthlySpending(Map<String, Double> amountPerMonts){
        return amountPerMonts.values()
                .stream()
                .reduce(Double::sum)
                .map(amount -> BigDecimal.valueOf(amount)
                        .divide(BigDecimal.valueOf(amountPerMonts.size()), 2)
                        .setScale(2, RoundingMode.HALF_EVEN))
                .orElse(BigDecimal.ZERO)
                .doubleValue();
    }
}

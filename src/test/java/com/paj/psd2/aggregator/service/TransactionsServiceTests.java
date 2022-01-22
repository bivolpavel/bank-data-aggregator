package com.paj.psd2.aggregator.service;

import com.paj.psd2.aggregator.client.AccountsApiClient;
import com.paj.psd2.aggregator.client.generated.model.AccountsResponse;
import com.paj.psd2.aggregator.client.generated.model.TransactionsResponse;
import com.paj.psd2.aggregator.payload.AllAccountsTransactionsResponses;
import com.paj.psd2.aggregator.utils.FileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClientException;

import java.util.Optional;

@SpringBootTest
public class TransactionsServiceTests {

    private static final String ACCOUNTS_MOCK_FILE_LOCATION = "mock/accounts.json";
    private static final String TRANSACTIONS_MOCK_FILE_LOCATION = "mock/transactions.json";

    @Mock
    private AccountsApiClient accountsApiClient;

    private TransactionsService transactionsService;

    @BeforeEach
    public void setUp() {
        transactionsService = new TransactionsService(accountsApiClient);
    }

    @Test
    public void testGetTransactionInfo_whenSuccessfulClientCall() {
        AccountsResponse returnAccountsResponse = FileReader
                .readFromFile(ACCOUNTS_MOCK_FILE_LOCATION, AccountsResponse.class);
        Mockito.when(accountsApiClient.getAccounts())
                .thenReturn(Optional.ofNullable(returnAccountsResponse));

        TransactionsResponse returnTransactionResponse = FileReader
                .readFromFile(TRANSACTIONS_MOCK_FILE_LOCATION, TransactionsResponse.class);

        Mockito.when(accountsApiClient.getAccountTransactions(Mockito.any(), Mockito.any()))
                .thenReturn(Optional.ofNullable(returnTransactionResponse));

        Optional<AllAccountsTransactionsResponses> allAccountsTransactionsResponses = transactionsService
                .getTransactionsInfo();

        Assertions.assertTrue(allAccountsTransactionsResponses.isPresent());
        Assertions.assertEquals(3, allAccountsTransactionsResponses.get().getAccountTransactions().size());
        Assertions.assertEquals(1, allAccountsTransactionsResponses.get().getAccountTransactions().get(0)
                .getBookedTransactions().size());
        Assertions.assertEquals(5, allAccountsTransactionsResponses.get().getAccountTransactions().get(0)
                .getPendingTransactions().size());

        Assertions.assertEquals("Popescu Corina", allAccountsTransactionsResponses.get().getAccountTransactions().get(0)
                .getOwnerName());
        Assertions.assertEquals("Cont Curent", allAccountsTransactionsResponses.get().getAccountTransactions().get(0)
                .getProduct());

        Assertions.assertEquals("Ilie Bogdan", allAccountsTransactionsResponses.get().getAccountTransactions().get(1)
                .getOwnerName());
        Assertions.assertEquals("Cont Curent", allAccountsTransactionsResponses.get().getAccountTransactions().get(1)
                .getProduct());

        Assertions.assertEquals("Popescu Corina", allAccountsTransactionsResponses.get().getAccountTransactions().get(2)
                .getOwnerName());
        Assertions.assertEquals("Card de Credit", allAccountsTransactionsResponses.get().getAccountTransactions().get(2)
                .getProduct());
    }

    @Test
    public void testGetTransactionInfo_whenClientThrowException() {
        Mockito.when(accountsApiClient.getAccounts())
                .thenThrow(new RestClientException("Error"));

        Optional<AllAccountsTransactionsResponses> allAccountsTransactionsResponses = transactionsService
                .getTransactionsInfo();

        Assertions.assertFalse(allAccountsTransactionsResponses.isPresent());
    }
}

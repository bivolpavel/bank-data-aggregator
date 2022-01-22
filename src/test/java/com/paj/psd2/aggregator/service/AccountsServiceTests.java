package com.paj.psd2.aggregator.service;

import com.paj.psd2.aggregator.client.AccountsApiClient;
import com.paj.psd2.aggregator.client.generated.model.AccountsResponse;
import com.paj.psd2.aggregator.client.generated.model.BalancesResponse;
import com.paj.psd2.aggregator.mapping.AccountBalanceMapper;
import com.paj.psd2.aggregator.mapping.AccountBalanceMapperImpl;
import com.paj.psd2.aggregator.payload.AllAccountsResponse;
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
public class AccountsServiceTests {

    private static final String ACCOUNTS_MOCK_FILE_LOCATION = "mock/accounts.json";
    private static final String ACCOUNT_BALANCES_MOCK_FILE_LOCATION = "mock/accountBalances.json";


    @Mock
    private AccountsApiClient accountsApiClient;

    private AccountBalanceMapper accountBalanceMapper;

    private AccountsService accountsService;

    @BeforeEach
    public void setUp() {
        accountBalanceMapper = new AccountBalanceMapperImpl();
        accountsService = new AccountsService(accountsApiClient, accountBalanceMapper);
    }

    @Test
    public void testGetAllAccountBalances_whenSuccessfulClientCall() {
        AccountsResponse returnAccountsResponse = FileReader
                .readFromFile(ACCOUNTS_MOCK_FILE_LOCATION, AccountsResponse.class);
        Mockito.when(accountsApiClient.getAccounts())
                .thenReturn(Optional.ofNullable(returnAccountsResponse));

        BalancesResponse returnBalancesResponse = FileReader
                .readFromFile(ACCOUNT_BALANCES_MOCK_FILE_LOCATION, BalancesResponse.class);
        Mockito.when(accountsApiClient.getAccountsBalance(Mockito.any(), Mockito.anyString()))
                .thenReturn(Optional.ofNullable(returnBalancesResponse));

        Optional<AllAccountsResponse> allAccountsResponse = accountsService.getAllAccountBalances();

        Assertions.assertTrue(allAccountsResponse.isPresent());
        Assertions.assertNotNull(allAccountsResponse);
        Assertions.assertEquals(3, allAccountsResponse.get().getAccountBalanceResponses().size());
        Assertions.assertEquals(1, allAccountsResponse.get().getAmountByCurrencies().entrySet().size());
        Assertions.assertEquals("RON", allAccountsResponse.get().getAmountByCurrencies()
                .entrySet()
                .stream()
                .findFirst().get().getKey());
        Assertions.assertEquals( 3221.58, allAccountsResponse.get().getAmountByCurrencies()
                .entrySet()
                .stream()
                .findFirst().get().getValue());
    }



    @Test
    public void testGetAllAccountBalances_whenGetAccountsThrowException() {
        Mockito.when(accountsApiClient.getAccounts())
                .thenThrow(new RestClientException("Error"));

        BalancesResponse returnBalancesResponse = FileReader
                .readFromFile(ACCOUNT_BALANCES_MOCK_FILE_LOCATION, BalancesResponse.class);
        Mockito.when(accountsApiClient.getAccountsBalance(Mockito.any(), Mockito.anyString()))
                .thenReturn(Optional.ofNullable(returnBalancesResponse));

        Optional<AllAccountsResponse> allAccountsResponse = accountsService.getAllAccountBalances();

        Assertions.assertFalse(allAccountsResponse.isPresent());

    }


    @Test
    public void testGetAllAccountBalances_whenGetBalancesThrowException() {
        AccountsResponse returnAccountsResponse = FileReader
                .readFromFile(ACCOUNTS_MOCK_FILE_LOCATION, AccountsResponse.class);
        Mockito.when(accountsApiClient.getAccounts())
                .thenReturn(Optional.ofNullable(returnAccountsResponse));

        Mockito.when(accountsApiClient.getAccountsBalance(Mockito.any(), Mockito.anyString()))
                .thenThrow(new RestClientException("Error"));

        Optional<AllAccountsResponse> allAccountsResponse = accountsService.getAllAccountBalances();

        Assertions.assertFalse(allAccountsResponse.isPresent());
    }
}

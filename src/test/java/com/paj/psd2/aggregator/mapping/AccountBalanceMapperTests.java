package com.paj.psd2.aggregator.mapping;

import com.paj.psd2.aggregator.client.generated.model.Account;
import com.paj.psd2.aggregator.client.generated.model.Amount;
import com.paj.psd2.aggregator.client.generated.model.Balance;
import com.paj.psd2.aggregator.payload.AccountBalanceResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class AccountBalanceMapperTests {

    AccountBalanceMapper accountBalanceMapper = new AccountBalanceMapperImpl();

    @Test
    public void testAccountAndBalanceToAccountBalanceResponse(){

        Account account = new Account();
        account.setIban("NL69INGB0123456789");
        account.setCurrency("EUR");
        account.product("Current Account");
        account.resourceId(UUID.fromString("7de0041d-4f25-4b6c-a885-0bbeb1eab220"));
        account.setName("A. Van Dijk");

        Balance balance = new Balance();
        Amount amount = new Amount();
        amount.setAmount(50D);
        balance.setBalanceAmount(amount );

        AccountBalanceResponse accountBalanceResponse = accountBalanceMapper
                .accountAndBalanceToAccountBalanceResponse(account, balance);

        Assertions.assertNotNull(accountBalanceResponse);
        Assertions.assertEquals(account.getIban(), accountBalanceResponse.getIban());
        Assertions.assertEquals(account.getCurrency(), accountBalanceResponse.getCurrency());
        Assertions.assertEquals(account.getProduct(), accountBalanceResponse.getProduct());
        Assertions.assertEquals(account.getResourceId(), accountBalanceResponse.getResourceId());
        Assertions.assertEquals(account.getName(), accountBalanceResponse.getOwnerName());
        Assertions.assertEquals(balance.getBalanceAmount().getAmount(), accountBalanceResponse.getAmount());
    }
}

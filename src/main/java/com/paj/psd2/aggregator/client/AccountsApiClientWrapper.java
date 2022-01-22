package com.paj.psd2.aggregator.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paj.psd2.aggregator.client.generated.RFC3339DateFormat;
import com.paj.psd2.aggregator.client.generated.api.AccountBalanceApi;
import com.paj.psd2.aggregator.client.generated.api.AccountDetailsApi;
import com.paj.psd2.aggregator.client.generated.api.AccountTransactionsApi;
import com.paj.psd2.aggregator.client.generated.api.CardAccountTransactionsApi;
import com.paj.psd2.aggregator.client.generated.model.AccountsResponse;
import com.paj.psd2.aggregator.client.generated.model.BalancesResponse;
import com.paj.psd2.aggregator.client.generated.model.CardTransactionsResponse;
import com.paj.psd2.aggregator.client.generated.model.TransactionsResponse;
import com.paj.psd2.aggregator.session.AccessTokenContext;
import com.paj.psd2.aggregator.utils.Constants;
import com.paj.psd2.aggregator.utils.Currency;
import net.danlucian.psd2.ing.security.ClientSecrets;
import net.danlucian.psd2.ing.security.SecurityUtil;
import net.danlucian.psd2.ing.time.DateUtil;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.time.OffsetDateTime;
import java.util.*;
import org.slf4j.Logger;

import static net.danlucian.psd2.ing.security.SecurityUtil.generateSignature;

@Component
@Primary
public class AccountsApiClientWrapper implements AccountsApiClient{

    private final Logger logger = LoggerFactory.getLogger(AccountsApiClientWrapper.class);

    private final ClientSecrets clientSecrets;

    private final AccountBalanceApi accountBalanceApi;

    private final AccountDetailsApi accountDetailsApi;

    private final AccountTransactionsApi transactionsApi;

    private final CardAccountTransactionsApi cardAccountTransactionsApi;

    private final DateFormat dateFormat;

    public AccountsApiClientWrapper(ClientSecrets clientSecrets, AccountBalanceApi accountBalanceApi,
                                    AccountDetailsApi accountDetailsApi, AccountTransactionsApi transactionsApi,
                                    CardAccountTransactionsApi cardAccountTransactionsApi) {
        this.clientSecrets = clientSecrets;
        this.accountBalanceApi = accountBalanceApi;
        this.accountDetailsApi = accountDetailsApi;
        this.transactionsApi = transactionsApi;
        this.cardAccountTransactionsApi = cardAccountTransactionsApi;
        this.dateFormat = new RFC3339DateFormat();
        this.dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /*
    *   Provides all granted accounts and will provide account details for the list of accounts.
    *   If there are no granted accounts at all, then it will return a 404.
    */
    @Override
    public Optional<AccountsResponse> getAccounts(){
        logger.debug("getAccounts()");

        try {
            final String digest = SecurityUtil.generateDigestAndConvert("");
            final String signature = getSignature("get", "/v3/accounts", digest);
            AccountsResponse accountsResponse = accountDetailsApi
                    .v3AccountsGet(UUID.randomUUID(), getBearer(), signature, digest, OffsetDateTime.now());

            logger.debug(new ObjectMapper().writeValueAsString(accountsResponse));
            return Optional.of(accountsResponse);
        } catch (Exception e) {
            logger.error("Exception occurred during calling getAccounts service: {}", e.getMessage());
            return Optional.empty();
        }
    }

    /*
     *   Provides balance information for a given account.
     */
    @Override
    public Optional<BalancesResponse> getAccountsBalance(UUID accountId, String currency){
        logger.debug("getAccountsBalance() for accountId: {}", accountId.toString());
     try {
         final String digest = SecurityUtil.generateDigestAndConvert("");
         final String signature = getSignature("get", "/v3/accounts/"+ accountId.toString()
                 +"/balances?balanceTypes=" + accountBalanceApi.getApiClient().parameterToString(getBalancesTypes())
                 + "&currency=EUR" , digest);

         BalancesResponse balancesResponse = accountBalanceApi
                 .v3AccountsAccountIdBalancesGet(accountId, UUID.randomUUID(), getBearer(), signature, digest,
                         OffsetDateTime.now(), getBalancesTypes(), Currency.EUR.getName());
         logger.debug(new ObjectMapper().writeValueAsString(balancesResponse));
         return Optional.of(balancesResponse);
     } catch (Exception e) {
         logger.error("Exception occurred during calling getAccountsBalances service: {}", e.getMessage());
         return Optional.empty();
     }
    }


    /*
     *   Provides transaction information for a given account.
     */
    @Override
    public Optional<TransactionsResponse> getAccountTransactions(UUID accountId, String currency){
        logger.debug("getAccountTransactions() for accountId: {}", accountId.toString());
        try {
            final String digest = SecurityUtil.generateDigestAndConvert("");

            OffsetDateTime dateFrom = degFromDate();
            OffsetDateTime dateTo = OffsetDateTime.now();

            final String signature = getSignature("get", "/v2/accounts/" + accountId.toString() +
                    "/transactions?dateFrom=2018-06-01T09:00:00Z&dateTo=2018-06-30T18:00:00Z&currency=" +
                            currency +"&limit=5", digest);

            TransactionsResponse transactionsResponse = transactionsApi
                    .v2AccountsAccountIdTransactionsGet(accountId, UUID.randomUUID(), getBearer(), signature, digest,
                            OffsetDateTime.now(), "2018-06-01T09:00:00Z", "2018-06-30T18:00:00Z",
                            currency, 5);

            logger.debug(new ObjectMapper().writeValueAsString(transactionsResponse));
            return Optional.of(transactionsResponse);
        } catch (Exception e) {
            logger.error("Exception occurred during calling getAccountsTransactions service: {}", e.getMessage());
            return Optional.empty();
        }
    }


    /*
     *   Provides transaction information for a given card account.
     */
    @Override
    public Optional<CardTransactionsResponse> getAccountsCardTransactions(UUID accountId){
        logger.debug("getAccountsCardTransactions() for accountId: {}", accountId.toString());

        try {
            OffsetDateTime dateFrom = degFromDate();
            OffsetDateTime dateTo = OffsetDateTime.now();

            final String digest = SecurityUtil.generateDigestAndConvert("");
            final String signature = getSignature("get", "/v1/card-accounts/"+ accountId.toString() +
                    "/transactions?dateFrom=2018-06-01T09:00:00Z&dateTo=2021-2-15T09:00:00Z&limit=5", digest);

            CardTransactionsResponse transactionsResponse = cardAccountTransactionsApi
                    .v1CardAccountsAccountIdTransactionsGet(accountId, UUID.randomUUID(),
                    getBearer(), signature, digest, OffsetDateTime.now(), "2018-06-01T09:00:00Z",
                            "2018-06-30T18:00:00Z", 5);

            logger.debug(new ObjectMapper().writeValueAsString(transactionsResponse));
            return Optional.of(transactionsResponse);
        } catch (Exception e) {
            logger.error("Exception occurred during calling getAccountsTransactions service: {}", e.getMessage());
            return Optional.empty();
        }
    }

    private String getBearer(){
        return Constants.Authorization.BEARER_PREFIX.concat(AccessTokenContext.getCustomerAccessToken().getAccessToken());
    }

    private String getSignature(String httpVerb, String uri, String digest){
         String signature = generateSignature(clientSecrets.getClientSigningKey(),
                signingTemplate(httpVerb, uri, DateUtil.getCurrentDateAsString(), digest)
        );

        return "keyId=\"5ca1ab1e-c0ca-c01a-cafe-154deadbea75\",algorithm=\"rsa-sha256\",headers=\"(request-target) date digest\",signature=\"" + signature + "\"";
    }

    protected String signingTemplate(final String httpVerb, final String uri, final String currentDate, String digest) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        printWriter.print("(request-target): " + httpVerb + " " + uri + "\n");
        printWriter.print("date: " + currentDate + "\n");
        printWriter.print("digest: " + digest);
        return stringWriter.toString();
    }

    public static OffsetDateTime degFromDate() {
        return OffsetDateTime.ofInstant(
                new GregorianCalendar(2010, Calendar.JANUARY, 31).toInstant(),
                TimeZone.getDefault().toZoneId());
    }

    private List<String> getBalancesTypes(){
        return Collections.singletonList("expected");
    }
}

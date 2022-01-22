package com.paj.psd2.aggregator.service;

import net.danlucian.psd2.ing.flow.Flow;
import net.danlucian.psd2.ing.rpc.payload.ApplicationAccessToken;
import net.danlucian.psd2.ing.rpc.payload.PreflightUrl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.MalformedURLException;
import java.util.Optional;

@SpringBootTest
public class BankAuthorizationServiceTests {

    private static final String appAccessToken = "appAccessToken";

    private static final String URL = "preFlightUrl";

    private BankAuthorizationService bankAuthorizationService;

    @Mock
    private Flow sandBoxFlow;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        bankAuthorizationService = new BankAuthorizationService(sandBoxFlow);
    }

    @Test
    public void testGetBankAuthorizationUrl_whenClientRespondsSuccessfully(){

        ApplicationAccessToken applicationAccessToken = new ApplicationAccessToken(appAccessToken, 10000L,
                "scope", "tokenType", null, "clientId");

        Mockito.when(sandBoxFlow.getAppAccessToken())
                .thenReturn(applicationAccessToken);
        Mockito.when(sandBoxFlow.getPreflightUrl(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new PreflightUrl(URL));

        Optional<String> url = bankAuthorizationService.getBankAuthorizationUrl();

        Assertions.assertTrue(url.isPresent());
        Assertions.assertEquals(URL, url.get());
    }
}

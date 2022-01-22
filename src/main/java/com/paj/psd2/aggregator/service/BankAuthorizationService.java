package com.paj.psd2.aggregator.service;

import com.paj.psd2.aggregator.session.AccessTokenContext;
import com.paj.psd2.aggregator.utils.Constants;
import com.paj.psd2.aggregator.web.AddBankAccountController;
import net.danlucian.psd2.ing.flow.Flow;
import net.danlucian.psd2.ing.rpc.payload.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@Service
public class BankAuthorizationService {

    private final Logger logger = LoggerFactory.getLogger(BankAuthorizationService.class);

    private final URL redirectUrl;

    private final Flow sandBoxFlow;

    @Value( "${server.port}" )
    private String port;

    public BankAuthorizationService(Flow sandBoxFlow) throws MalformedURLException {
        this.sandBoxFlow = sandBoxFlow;
        redirectUrl = new URL(Constants.Authorization.REDIRECT_BACK_URL);
    }

    public Optional<String> getBankAuthorizationUrl() {
        logger.debug("Initiate authorization flow!");
        try {
            return Optional.ofNullable(sandBoxFlow.getAppAccessToken())
                    .map(applicationAccessToken -> {
                        AccessTokenContext.setApplicationAccessToken(applicationAccessToken);
                        return sandBoxFlow.getPreflightUrl(
                                applicationAccessToken,
                                redirectUrl,
                                Country.Romania).getLocation();
                    });
        } catch (Exception e) {
            logger.error("Exception occured during authorization flow: {}", e.getMessage());
            return Optional.empty();
        }
    }

    public void getCustomerAccessToken(String code, String state){
        logger.debug("Receive redirect from bank with code: {}", code);

        Optional.ofNullable(code)
                .map(c -> sandBoxFlow
                        .getCustomerAccessToken(AccessTokenContext.getApplicationAccessToken(), code))
                .ifPresent(AccessTokenContext::setCustomerAccessToken);
    }
    
}

package com.paj.psd2.aggregator.session;

import net.danlucian.psd2.ing.rpc.payload.ApplicationAccessToken;
import net.danlucian.psd2.ing.rpc.payload.CustomerAccessToken;

public class AccessTokenContext {

    private static ApplicationAccessToken applicationAccessToken;

    private static CustomerAccessToken customerAccessToken;

    public static ApplicationAccessToken getApplicationAccessToken() {
        return applicationAccessToken;
    }

    public static void setApplicationAccessToken(ApplicationAccessToken applicationAccessToken) {
        AccessTokenContext.applicationAccessToken = applicationAccessToken;
    }

    public static CustomerAccessToken getCustomerAccessToken() {
        return customerAccessToken;
    }

    public static void setCustomerAccessToken(CustomerAccessToken customerAccessToken) {
        AccessTokenContext.customerAccessToken = customerAccessToken;
    }
}

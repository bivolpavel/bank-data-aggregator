package com.paj.psd2.aggregator.utils;

public class Constants {

    public class CertificatesLocations {
        public static final String CLIENT_CERTIFICATE_LOCATION = "certificates/example_eidas_client_tls.cer";
        public static final String CLIENT_KEY_LOCATION = "certificates/example_eidas_client_tls.key";
        public static final String SIGNING_CERTIFICATE_LOCATION = "certificates/example_eidas_client_signing.cer";
        public static final String SIGNING_KEY_LOCATION = "certificates/example_eidas_client_signing.key";
    }

    public class Authorization {
        public static final String SCOPES = "customer-details%3Aprofile%3Aview+customer-details%3Aemail%3Aview+customer-details%3Aaddress%3Aview+payment-accounts%3Abalances%3Aview+payment-accounts%3Atransactions%3Aview";
        public static final String BEARER_PREFIX = "Bearer ";
        public static final String REDIRECT_BACK_URL = "http://localhost:8088/outh/redirect/url";
    }


}

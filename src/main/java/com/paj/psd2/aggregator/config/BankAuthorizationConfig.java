package com.paj.psd2.aggregator.config;

import com.paj.psd2.aggregator.utils.Constants;
import net.danlucian.psd2.ing.flow.Flow;
import net.danlucian.psd2.ing.flow.implementation.SandboxAuthorizationFlow;
import net.danlucian.psd2.ing.security.ClientSecrets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

@Configuration
public class BankAuthorizationConfig {

    @Bean
    public ClientSecrets clientSecrets() throws IOException {
        File clientCertificate = new ClassPathResource(Constants.CertificatesLocations.CLIENT_CERTIFICATE_LOCATION).getFile();
        File clientKey = new ClassPathResource(Constants.CertificatesLocations.CLIENT_KEY_LOCATION).getFile();
        File signingCertificate = new ClassPathResource(Constants.CertificatesLocations.SIGNING_CERTIFICATE_LOCATION).getFile();
        File signingKey = new ClassPathResource(Constants.CertificatesLocations.SIGNING_KEY_LOCATION).getFile();

        return new ClientSecrets(
                clientCertificate,
                clientKey,
                signingCertificate,
                signingKey);
    }

    @Bean
    public Flow sandBoxFlow(ClientSecrets clientSecrets){
        return new SandboxAuthorizationFlow(clientSecrets, Constants.Authorization.SCOPES);
    }
}

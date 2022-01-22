package com.paj.psd2.aggregator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "authentication.service")
public class AuthServiceConfig {

    String host;

    String signInEndpoint;

    String verifyCodeEndpoint;

    String createUserEndpoint;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSignInEndpoint() {
        return signInEndpoint;
    }

    public void setSignInEndpoint(String signInEndpoint) {
        this.signInEndpoint = signInEndpoint;
    }

    public String getVerifyCodeEndpoint() {
        return verifyCodeEndpoint;
    }

    public void setVerifyCodeEndpoint(String verifyCodeEndpoint) {
        this.verifyCodeEndpoint = verifyCodeEndpoint;
    }

    public String getCreateUserEndpoint() {
        return createUserEndpoint;
    }

    public void setCreateUserEndpoint(String createUserEndpoint) {
        this.createUserEndpoint = createUserEndpoint;
    }
}

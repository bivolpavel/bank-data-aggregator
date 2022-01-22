package com.paj.psd2.aggregator.service;

import com.paj.psd2.aggregator.auth.AuthenticationClient;
import com.paj.psd2.aggregator.payload.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class AuthenticationService {

    private AuthenticationClient authenticationClient;

    public AuthenticationService(AuthenticationClient authenticationClient) {
        this.authenticationClient = authenticationClient;
    }

    public JwtAuthenticationResponse authenticateUser(String username, String password){

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        return authenticationClient.authenticateUser(loginRequest);
    }

    public JwtAuthenticationResponse verifyCode(String username, String code){

        VerifyCodeRequest verifyCodeRequest = new VerifyCodeRequest();
        verifyCodeRequest.setUsername(username);
        verifyCodeRequest.setCode(code);

        return authenticationClient.verifyCode(verifyCodeRequest);
    }


    public SignupResponse createUser(@RequestParam(name = "name") String name, String username, String email, String password, boolean mfa){

        SignUpRequest verifyCodeRequest = new SignUpRequest();
        verifyCodeRequest.setName(name);
        verifyCodeRequest.setUsername(username);
        verifyCodeRequest.setPassword(password);
        verifyCodeRequest.setEmail(email);
        verifyCodeRequest.setMfa(mfa);

        return authenticationClient.createUser(verifyCodeRequest);
    }
}

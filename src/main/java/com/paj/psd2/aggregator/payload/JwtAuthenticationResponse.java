package com.paj.psd2.aggregator.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class JwtAuthenticationResponse {

    public JwtAuthenticationResponse() {
    }

    public JwtAuthenticationResponse(@NonNull String accessToken, boolean mfa) {
        this.accessToken = accessToken;
        this.mfa = mfa;
    }

    @NonNull
    private String accessToken;
    private boolean mfa;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isMfa() {
        return mfa;
    }

    public void setMfa(boolean mfa) {
        this.mfa = mfa;
    }


}

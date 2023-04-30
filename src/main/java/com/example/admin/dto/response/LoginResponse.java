package com.example.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType = "bearer";

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("issued_at")
    private long issuedAt;

    @JsonProperty("expired_in")
    private long expiredIn;

    public LoginResponse(String accessToken, long expiredIn) {
        this.accessToken = accessToken;
        this.issuedAt = System.currentTimeMillis();
        this.expiredIn = expiredIn;
    }
}

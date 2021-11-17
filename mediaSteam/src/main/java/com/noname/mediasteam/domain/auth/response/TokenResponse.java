package com.noname.mediasteam.domain.auth.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class TokenResponse implements Serializable {

    private String accessToken;

    private String tokenType = "Bearer ";

    public TokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

}

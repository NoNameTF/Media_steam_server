package com.noname.mediasteam.domain.auth.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginRequest implements Serializable {

    private String email;

    private String password;

}

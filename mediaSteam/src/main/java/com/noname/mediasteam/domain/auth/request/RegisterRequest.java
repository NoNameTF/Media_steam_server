package com.noname.mediasteam.domain.auth.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterRequest implements Serializable {

    private String name;

    private String email;

    private String password;

}

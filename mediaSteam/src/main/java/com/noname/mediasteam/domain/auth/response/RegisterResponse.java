package com.noname.mediasteam.domain.auth.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class RegisterResponse implements Serializable {

    private String redirectLocation;

}

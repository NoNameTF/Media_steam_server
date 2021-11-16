package com.noname.mediasteam.domain.user.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserResponseDto implements Serializable {

    private String provider;

    private  String name;

    private String nickname;

    private String email;

    private String picture;

}

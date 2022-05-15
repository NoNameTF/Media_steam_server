package com.noname.mediasteam.domain.post.dto.response;

import com.noname.mediasteam.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class PostUserResponseDto implements Serializable {

    private static final long serialVersionUID = -5648375760310330911L;

    private Long id;

    private String nickname;

    private String role;

    @Builder
    public PostUserResponseDto(Long id, String nickname, String role) {
        this.id = id;
        this.nickname = nickname;
        this.role = role;
    }

    public static PostUserResponseDto of(User user) {
        return PostUserResponseDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .role(user.getRole().name())
                .build();
    }

}

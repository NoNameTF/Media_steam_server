package com.noname.mediasteam.domain.user;

import com.noname.mediasteam.domain.user.dto.response.UserResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private String nickname;

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private String password;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Role role;

    @Builder
    public User(Long id, String provider, String name, String email, String picture, Role role, String password) {
        this.id = id;
        this.provider = provider;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.password = password;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
                .provider(user.getProvider())
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .picture(user.getPicture())
                .build();
    }

}

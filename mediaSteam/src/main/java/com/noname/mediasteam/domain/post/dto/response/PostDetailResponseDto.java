package com.noname.mediasteam.domain.post.dto.response;


import com.noname.mediasteam.domain.post.Post;
import com.noname.mediasteam.domain.user.User;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PostDetailResponseDto implements Serializable {

    private static final long serialVersionUID = -5916512812059712262L;

    private Long id;

    private String title;

    private String category;

    private String content;

    private LocalDateTime createdAt;

    private User createdUser;

    public static PostDetailResponseDto of(Post post) {
        return PostDetailResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .category(post.getCategory().getName())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .createdUser(post.getCreatedUser())
                .build();
    }

}

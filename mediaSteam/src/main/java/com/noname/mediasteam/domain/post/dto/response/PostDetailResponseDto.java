package com.noname.mediasteam.domain.post.dto.response;


import com.noname.mediasteam.domain.post.Post;
import com.noname.mediasteam.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostDetailResponseDto implements Serializable {

    private static final long serialVersionUID = -5916512812059712262L;

    private Long id;

    private String title;

    private String category;

    private String content;

    private LocalDateTime createdAt;

    private PostUserResponseDto createdUser;

    @Builder
    public PostDetailResponseDto(Long id, String title, String category, String content, LocalDateTime createdAt, PostUserResponseDto createdUser) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.content = content;
        this.createdAt = createdAt;
        this.createdUser = createdUser;
    }

    public static PostDetailResponseDto of(Post post) {
        return PostDetailResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .category(post.getPostCategory() != null ? post.getPostCategory().getName() : null)
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .createdUser(PostUserResponseDto.of(post.getCreatedUser()))
                .build();
    }

}

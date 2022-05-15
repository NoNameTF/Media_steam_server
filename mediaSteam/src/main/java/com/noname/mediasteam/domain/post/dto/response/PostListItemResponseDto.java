package com.noname.mediasteam.domain.post.dto.response;

import com.noname.mediasteam.domain.post.PostCategory;
import com.noname.mediasteam.domain.post.PostComment;
import com.noname.mediasteam.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class PostListItemResponseDto implements Serializable {

    private static final long serialVersionUID = -6085617776781075071L;

    private Long id;

    private String title;

    private String categoryName;

    private LocalDateTime createdAt;

    private PostUserResponseDto user;

    private Long commentCount;

    private Long heartCount;

    @Builder
    public PostListItemResponseDto(Long id, String title, String categoryName, LocalDateTime createdAt, PostUserResponseDto user, Long commentCount, Long heartCount) {
        this.id = id;
        this.title = title;
        this.categoryName = categoryName;
        this.createdAt = createdAt;
        this.user = user;
        this.commentCount = commentCount;
        this.heartCount = heartCount;
    }

    public static PostListItemResponseDto of(Long id, String title, PostCategory postCategory, LocalDateTime createdAt, User user,
                                             List<PostComment> comments
//                                             , Long heartCount
    ) {
        return PostListItemResponseDto.builder()
                .id(id)
                .title(title)
                .categoryName(postCategory != null ? postCategory.getName() : null)
                .createdAt(createdAt)
                .user(PostUserResponseDto.of(user))
                .commentCount((long) comments.size())
//                .heartCount(heartCount
                .build();
    }

}

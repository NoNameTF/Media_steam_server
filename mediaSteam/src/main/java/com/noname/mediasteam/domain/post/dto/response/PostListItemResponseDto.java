package com.noname.mediasteam.domain.post.dto.response;

import com.noname.mediasteam.domain.post.Category;
import com.noname.mediasteam.domain.post.PostComment;
import com.noname.mediasteam.domain.user.User;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PostListItemResponseDto implements Serializable {

    private static final long serialVersionUID = -6085617776781075071L;

    private Long id;

    private String title;

    private String categoryName;

    private LocalDateTime createdAt;

    private User user;

    private Long commentCount;

    private Long heartCount;

    public static PostListItemResponseDto of(Long id, String title, Category category, LocalDateTime createdAt, User user,
                                      List<PostComment> comments
//                                             , Long heartCount
    ) {
        return PostListItemResponseDto.builder()
                .id(id)
                .title(title)
                .categoryName(category.getName())
                .createdAt(createdAt)
                .user(user)
                .commentCount((long) comments.size())
//                .heartCount(heartCount
                .build();
    }

}

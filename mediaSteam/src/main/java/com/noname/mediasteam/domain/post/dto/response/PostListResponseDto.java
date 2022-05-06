package com.noname.mediasteam.domain.post.dto.response;

import com.noname.mediasteam.domain.post.Post;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PostListResponseDto implements Serializable {

    private static final long serialVersionUID = -1995648651568424429L;

    private Long totalCount;

    private Long offset;

    private Long limit;

    private List<PostListItemResponseDto> postItems;

    public static PostListResponseDto of(Long totalCount, Long offset, Long limit, List<Post> posts) {
        List<PostListItemResponseDto> postListItemResponseDtoList = null;

        if (CollectionUtils.isEmpty(posts)) {
            postListItemResponseDtoList = posts.stream()
                    .map(post ->
                            PostListItemResponseDto.of(post.getId(), post.getTitle(), post.getCategory(),
                                    post.getCreatedAt(), post.getCreatedUser(), post.getComments())
                    )
                    .collect(Collectors.toList());
        }

        return PostListResponseDto.builder()
                .totalCount(totalCount)
                .offset(offset)
                .limit(limit)
                .postItems(postListItemResponseDtoList)
                .build();
    }

}

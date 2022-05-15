package com.noname.mediasteam.domain.post.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.noname.mediasteam.domain.post.Post;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PostListResponseDto implements Serializable {

    private static final long serialVersionUID = -1995648651568424429L;

    private Long totalCount;

    private Long offset;

    private Long limit;

    private List<PostListItemResponseDto> postItems;

    @Builder
    public PostListResponseDto(Long totalCount, Long offset, Long limit, List<PostListItemResponseDto> postItems) {
        this.totalCount = totalCount;
        this.offset = offset;
        this.limit = limit;
        this.postItems = postItems;
    }

    public static PostListResponseDto of(Long totalCount, Long offset, Long limit, List<Post> posts) {
        List<PostListItemResponseDto> postListItemResponseDtoList = null;

        if (!CollectionUtils.isEmpty(posts)) {
            postListItemResponseDtoList = posts.stream()
                    .map(post ->
                            PostListItemResponseDto.of(post.getId(), post.getTitle(), post.getPostCategory(),
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

package com.noname.mediasteam.controller;

import com.noname.mediasteam.config.security.UserPrincipal;
import com.noname.mediasteam.config.security.annotation.CurrentUser;
import com.noname.mediasteam.domain.post.Post;
import com.noname.mediasteam.domain.post.dto.request.PostRequestDto;
import com.noname.mediasteam.domain.post.dto.response.PostDetailResponseDto;
import com.noname.mediasteam.domain.post.dto.response.PostListResponseDto;
import com.noname.mediasteam.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/post")
public class PostController {

    private final PostService postService;

    @GetMapping
    public PostListResponseDto getPosts(
            @RequestParam(defaultValue = "0") Long offset,
            @RequestParam(defaultValue = "10") Long limit
    ) {
        return postService.getPosts(offset, limit);
    }

    @GetMapping(value = "/{id}")
    public PostDetailResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PostMapping
    public Long insertPost(
            @RequestBody PostRequestDto postRequestDto,
            @CurrentUser UserPrincipal userPrincipal,
            @RequestHeader String authorization
    ) {
        return postService.insertPost(postRequestDto, userPrincipal);
    }

    @PutMapping(value = "/{id}")
    public Long updatePost(@PathVariable Long id,
                           @RequestBody PostRequestDto postRequestDto,
                           @CurrentUser UserPrincipal userPrincipal,
                           @RequestHeader String authorization
    ) {
        return postService.updatePost(id, postRequestDto, userPrincipal);
    }

    @DeleteMapping(value = "/{id}")
    public Long deletePost(@PathVariable Long id,
                           @CurrentUser UserPrincipal userPrincipal,
                           @RequestHeader String authorization
    ) {
        return postService.deletePost(id, userPrincipal);
    }

}

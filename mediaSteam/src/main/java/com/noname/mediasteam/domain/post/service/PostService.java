package com.noname.mediasteam.domain.post.service;

import com.noname.mediasteam.config.security.UserPrincipal;
import com.noname.mediasteam.domain.post.Post;
import com.noname.mediasteam.domain.post.dto.request.PostRequestDto;
import com.noname.mediasteam.domain.post.dto.response.PostListResponseDto;
import com.noname.mediasteam.domain.post.dto.response.PostDetailResponseDto;
import com.noname.mediasteam.domain.post.repository.PostRepository;
import com.noname.mediasteam.domain.post.repository.PostRepositorySupport;
import com.noname.mediasteam.domain.user.User;
import com.noname.mediasteam.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    private final PostRepositorySupport postRepositorySupport;

    private final UserRepository userRepository;

    public PostListResponseDto getPosts(Long offset, Long limit) {
        return postRepositorySupport.getPosts(offset, limit);
    }

    public PostDetailResponseDto getPost(Long id) {
        Post post = postRepositorySupport.getPost(id);

        validationWithSelectForPost(post);

        return PostDetailResponseDto.of(post);
    }

    @Transactional
    public Long insertPost(PostRequestDto postRequestDto, UserPrincipal userPrincipal) {
        Post post = Post.create()
                // TODO: category 작업 따로 할 예정
                .postCategory(null)
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .user(findUserById(userPrincipal.getId()))
                .build();

        return postRepository.save(post).getId();
    }

    @Transactional
    public Long updatePost(Long id, PostRequestDto postRequestDto, UserPrincipal userPrincipal) {
        Post post = postRepositorySupport.getPost(id);

        validationWithUpdateForPost(post, userPrincipal);

        post.update(postRequestDto.getTitle(), postRequestDto.getContent(), findUserById(userPrincipal.getId()));

        return postRepository.save(post).getId();
    }

    @Transactional
    public Long deletePost(Long id, UserPrincipal userPrincipal) {
        Post post = postRepositorySupport.getPost(id);

        validationWithUpdateForPost(post, userPrincipal);

        post.remove();

        return post.getId();
    }

    private void validationWithUpdateForPost(Post post, UserPrincipal userPrincipal) {
        if (!post.confirmAuthor(userPrincipal))
            throw new RuntimeException("작성자가 아닙니다.");
    }

    private void validationWithSelectForPost(Post post) {
        if (post.isBlock())
            throw new RuntimeException("관리자에 의해 제한된 게시글 입니다.");

        if (post.isRemove())
            throw new RuntimeException("삭제 된 게시글 입니다.");
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
    }

}

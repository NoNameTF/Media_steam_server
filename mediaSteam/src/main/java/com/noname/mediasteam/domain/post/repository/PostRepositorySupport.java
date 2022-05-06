package com.noname.mediasteam.domain.post.repository;

import com.noname.mediasteam.domain.post.Post;
import com.noname.mediasteam.domain.post.dto.response.PostListItemResponseDto;
import com.noname.mediasteam.domain.post.dto.response.PostListResponseDto;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.noname.mediasteam.domain.post.QPost.post;
import static com.noname.mediasteam.domain.post.QPostComment.postComment;
import static com.noname.mediasteam.domain.user.QUser.user;

@RequiredArgsConstructor
@Repository
public class PostRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public Post getPost(Long id) {
        return queryFactory.selectFrom(post)
                .innerJoin(post.createdUser, user)
                .where(post.id.eq(id))
                .fetchOne();
    }

    public PostListResponseDto getPosts(Long offset, Long limit) {
        QueryResults<Post> postQueryResults = queryFactory.selectFrom(post)
                .innerJoin(post.comments, postComment)
                .orderBy(post.id.desc())
                .offset(offset)
                .limit(limit)
                .fetchResults();

        Long queryResultsLimit = postQueryResults.getLimit();
        Long queryResultsOffset = postQueryResults.getOffset();
        Long queryResultsTotal = postQueryResults.getTotal();

        List<Post> posts = postQueryResults.getResults();

        return PostListResponseDto.of(queryResultsTotal, queryResultsOffset, queryResultsLimit, posts);
    }

}


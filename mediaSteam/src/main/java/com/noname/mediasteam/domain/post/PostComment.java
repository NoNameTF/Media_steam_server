package com.noname.mediasteam.domain.post;

import com.noname.mediasteam.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long parentId;

    private String content;

    private Boolean isBlocked;

    private Boolean isRemoved;

    @OneToOne
    @JoinColumn(name = "createdUserId")
    private User createdUser;

    @OneToOne
    @JoinColumn(name = "modifiedUserId")
    private User modifiedUser;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;

}

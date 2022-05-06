package com.noname.mediasteam.domain.post;

import com.noname.mediasteam.config.security.UserPrincipal;
import com.noname.mediasteam.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    private String title;

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

    @OneToMany(mappedBy = "post")
    private List<PostComment> comments;

    @Builder(builderMethodName = "create")
    public Post(Category category, String title, String content, User user) {
        this.category = category;
        this.title = title;
        this.content = content;
        this.isRemoved = false;
        this.isBlocked = false;
        this.createdUser = user;
        this.createdAt = LocalDateTime.now();
    }

    public Post update(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.modifiedUser = user;
        this.updatedAt = LocalDateTime.now();

        return this;
    }

    public boolean isBlock() {
        return this.getIsBlocked();
    }

    public boolean isRemove() {
        return this.getIsRemoved();
    }

    public void remove() {
        this.isRemoved = true;
    }

    public boolean confirmAuthor(UserPrincipal userPrincipal) {
        return this.createdUser.getId() == userPrincipal.getId();
    }

}

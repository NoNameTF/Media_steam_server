package com.noname.mediasteam.domain.post.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class PostRequestDto implements Serializable {

    private static final long serialVersionUID = -4858103421485645821L;

    private String title;

    private String categoryId;

    private String content;

}

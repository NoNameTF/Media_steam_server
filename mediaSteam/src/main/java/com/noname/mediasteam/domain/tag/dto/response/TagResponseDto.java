package com.noname.mediasteam.domain.tag.dto.response;

import com.noname.mediasteam.domain.tag.Tag;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class TagResponseDto implements Serializable {

    private static final long serialVersionUID = -113421807863328019L;

    private Long id;

    private String name;

    @Builder
    public TagResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static TagResponseDto of(Tag tag) {
        if (tag == null) {
            return null;
        }

        return TagResponseDto.builder()
                .id(tag.getId())
                .name("#" + tag.getName())
                .build();
    }

}

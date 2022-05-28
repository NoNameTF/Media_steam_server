package com.noname.mediasteam.controller;

import com.noname.mediasteam.domain.tag.TagService;
import com.noname.mediasteam.domain.tag.dto.response.TagResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/v1/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/name/{name}")
    public List<TagResponseDto> getTags(
            @PathVariable String name
    ) {
        return tagService.getTags(name);
    }

    @GetMapping("/{id}")
    public TagResponseDto getTag(
            @PathVariable Long id
    ) {
        return tagService.getTag(id);
    }

    @PostMapping
    public Long createTag(
            String name,
            @ApiIgnore @RequestHeader String authorization
    ) {
        return tagService.create(name);
    }

}


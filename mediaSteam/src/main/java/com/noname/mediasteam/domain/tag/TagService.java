package com.noname.mediasteam.domain.tag;

import com.noname.mediasteam.domain.tag.dto.response.TagResponseDto;
import com.noname.mediasteam.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public Long create(String name) {
        if (!StringUtils.hasText(name)) {
            throw new RuntimeException("태그명은 필수 값 입니다.");
        }

        Optional<Tag> tagOptional = tagRepository.findByName(name);

        if (tagOptional.isPresent()) {
            return tagOptional.get().getId();
        }

        return tagRepository.save(Tag.builder()
                .name(name)
                .build()).getId();
    }

    public List<TagResponseDto> getTags(String name) {
        List<Tag> tags = tagRepository.getByNameStartsWith(name);

        if (CollectionUtils.isEmpty(tags))
            return Collections.emptyList();

        return tags.stream()
                .map(TagResponseDto::of)
                .collect(Collectors.toList());
    }

    public TagResponseDto getTag(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("태그를 찾을 수 없습니다."));

        return TagResponseDto.of(tag);
    }

}

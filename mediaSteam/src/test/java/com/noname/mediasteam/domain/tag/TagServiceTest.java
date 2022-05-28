package com.noname.mediasteam.domain.tag;

import com.noname.mediasteam.domain.tag.dto.response.TagResponseDto;
import com.noname.mediasteam.domain.tag.repository.TagRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    private TagService tagService;

    @Mock
    private TagRepository tagRepository;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        tagService = new TagService(tagRepository);
    }

    @Test
    public void 태그_아이디로_조회() {
        Tag tag = Tag.builder()
                .name("안녕하세요")
                .build();

        // given
        given(tagRepository.findById(1L)).willReturn(Optional.of(tag));

        // when
        TagResponseDto tagResponseDto = tagService.getTag(1L);

        // then
        assertThat(tagResponseDto.getName(), is("#안녕하세요"));
    }

    @Test
    public void 태그_이름으로_조회() {
        Tag tag1 = Tag.builder().id(1L).name("퍄").build();

        Tag tag2 = Tag.builder().id(2L).name("퍄퍄퍄").build();

        Tag tag3 = Tag.builder().id(3L).name("퍄퍄퍄퍄").build();

        // given
        given(tagRepository.getByNameStartsWith("퍄")).willReturn(List.of(tag1, tag2, tag3));

        // when
        List<TagResponseDto> tagResponseDtos = tagService.getTags("퍄");

        // then
        assertThat(tagResponseDtos.size(), is(3));
        assertThat(tagResponseDtos.get(0).getName(), startsWith("#퍄"));
        assertThat(tagResponseDtos.get(1).getName(), startsWith("#퍄"));
        assertThat(tagResponseDtos.get(2).getName(), startsWith("#퍄"));
    }

    @Test
    public void 신규_태그_생성() {
        Tag tag1 = Tag.builder().id(1L).name("퍄").build();


        verify(tagRepository, times(1)).save(any(Tag.class));
    }

    @Test(expected = RuntimeException.class)
    public void 신규_태그_생성_비어있는_태그명() {
         when(tagService.create(""));
    }
}
package com.noname.mediasteam.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.noname.mediasteam.config.security.TokenProvider;
import com.noname.mediasteam.config.security.UserPrincipal;
import com.noname.mediasteam.domain.post.dto.response.PostDetailResponseDto;
import com.noname.mediasteam.domain.post.dto.response.PostListResponseDto;
import com.noname.mediasteam.domain.user.Role;
import com.noname.mediasteam.domain.user.User;
import com.noname.mediasteam.security.annotation.WithMockCustomUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("local")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TokenProvider tokenProvider;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private String token;

    @Before
    public void setup() {
        User user = User.builder()
                .id(1L)
                .email("test@test")
                .name("test")
                .password(new BCryptPasswordEncoder().encode("1234"))
                .role(Role.USER)
                .build();

        UserPrincipal userPrincipal = UserPrincipal.create(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, userPrincipal.getPassword(), userPrincipal.getAuthorities());

        token = "Bearer " + tokenProvider.createToken(authentication);
    }

    @Test
    public void 전체_게시글_조회() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/v1/post");

        ResultActions result = mvc.perform(request);

        result.andExpect(MockMvcResultMatchers.status().isOk());

        String responseMessage = result.andReturn().getResponse().getContentAsString(Charset.defaultCharset());

        PostListResponseDto postListResponseDto = objectMapper.readValue(responseMessage, PostListResponseDto.class);

        assertEquals(Optional.of((long) 3).get(), postListResponseDto.getTotalCount());
    }

    @Test
    public void 게시글_상세_조회() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/v1/post/1");

        ResultActions result = mvc.perform(request);

        result.andExpect(MockMvcResultMatchers.status().isOk());

        String responseMessage = result.andReturn().getResponse().getContentAsString(Charset.defaultCharset());

        PostDetailResponseDto postDetailResponseDto = objectMapper.readValue(responseMessage, PostDetailResponseDto.class);

        assertEquals(Optional.of((long) 1).get(), postDetailResponseDto.getId());
        assertEquals("테스트", postDetailResponseDto.getTitle());
        assertEquals("테스트", postDetailResponseDto.getContent());
    }

    @Test
    public void 게시글_등록() throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("title", "테스트 코드 등록");
        params.put("categoryId", "1");
        params.put("content", "테스트 코드 등록");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/v1/post")
                .header("Authorization", token)
                .content(objectMapper.writeValueAsString(params))
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions result = mvc.perform(request);

        result.andExpect(MockMvcResultMatchers.status().isOk());

        String responseMessage = result.andReturn().getResponse().getContentAsString(Charset.defaultCharset());

        Long id = objectMapper.readValue(responseMessage, Long.class);

        assertThat(0L, lessThan(id));
    }

    @Test
    public void 게시글_수정() throws Exception {
        long id = 4;

        HashMap<String, String> params = new HashMap<>();
        params.put("title", "테스트 코드 등록");
        params.put("categoryId", "1");
        params.put("content", "컨텐츠 수정");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/v1/post/{id}", id)
                .header("Authorization", token)
                .content(objectMapper.writeValueAsString(params))
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions result = mvc.perform(request);

        result.andExpect(MockMvcResultMatchers.status().isOk());

        String responseMessage = result.andReturn().getResponse().getContentAsString(Charset.defaultCharset());

        Long updatedId = objectMapper.readValue(responseMessage, Long.class);

        assertThat(id, is(updatedId));
    }

    @Test
    public void 게시글_삭제() throws Exception {
        long id = 4;

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/v1/post/{id}", id)
                .header("Authorization", token);

        ResultActions result = mvc.perform(request);

        result.andExpect(MockMvcResultMatchers.status().isOk());

        String responseMessage = result.andReturn().getResponse().getContentAsString(Charset.defaultCharset());

        Long deletedId = objectMapper.readValue(responseMessage, Long.class);

        assertThat(id, is(deletedId));
    }

}
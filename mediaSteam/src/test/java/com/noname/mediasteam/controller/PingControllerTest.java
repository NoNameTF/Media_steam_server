package com.noname.mediasteam.controller;

import com.noname.mediasteam.config.security.SecurityConfig;
import com.noname.mediasteam.config.security.TokenAuthenticationFilter;
import com.noname.mediasteam.security.annotation.WithMockCustomUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebMvcTest(
        controllers = PingController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = {SecurityConfig.class, TokenAuthenticationFilter.class}
                )
        }
)
public class PingControllerTest {

    @Autowired
    private MockMvc mvc;

    @WithMockCustomUser
    @Test
    public void 핑_퐁() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/v1/ping");

        ResultActions result = mvc.perform(request);

        result.andExpect(MockMvcResultMatchers.status().isOk());

        String responseObject = result.andReturn().getResponse().getContentAsString();

        assertEquals("pong", responseObject);
    }

}
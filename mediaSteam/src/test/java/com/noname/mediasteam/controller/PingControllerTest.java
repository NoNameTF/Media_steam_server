package com.noname.mediasteam.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class PingControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void 핑_퐁() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/v1/ping");

        ResultActions result = mvc.perform(request);

        result.andExpect(MockMvcResultMatchers.status().isOk());

        String responseObject = result.andReturn().getResponse().getContentAsString();

        assertEquals("pong", responseObject);
    }

}
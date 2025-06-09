package com.outsera.movie.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WinnerControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void shouldReturnMinAndMaxIntervalWinnersCorrectStructure() throws Exception {
        mockMvc.perform(get("/winners/min-max-interval")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.min", notNullValue()))
                .andExpect(jsonPath("$.max", notNullValue()))
                .andExpect(jsonPath("$.min").isArray())
                .andExpect(jsonPath("$.max").isArray());
    }

    @Test
    void shouldReturnMinIntervalWinnersCorrectStructure() throws Exception {
        mockMvc.perform(get("/winners/min-interval")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].producer", notNullValue()))
                .andExpect(jsonPath("$[0].interval", notNullValue()))
                .andExpect(jsonPath("$[0].previousWin", notNullValue()))
                .andExpect(jsonPath("$[0].followingWin", notNullValue()));
    }

    @Test
    void shouldReturnMaxIntervalWinnersCorrectStructure() throws Exception {
        mockMvc.perform(get("/winners/max-interval")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].producer", notNullValue()))
                .andExpect(jsonPath("$[0].interval", notNullValue()))
                .andExpect(jsonPath("$[0].previousWin", notNullValue()))
                .andExpect(jsonPath("$[0].followingWin", notNullValue()));
    }

}

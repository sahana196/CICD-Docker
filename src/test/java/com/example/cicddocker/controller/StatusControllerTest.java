package com.example.cicddocker.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(StatusController.class)
class StatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void healthEndpoint_ShouldReturnUpStatus() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.timestamp").isNumber());
    }

    @Test
    void configEndpoint_ShouldReturnDefaultMessage() throws Exception {
        mockMvc.perform(get("/config"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello from Spring Boot"))
                .andExpect(jsonPath("$.envVarPresent").value(true));
    }

    @Test
    void healthEndpoint_ShouldReturnValidJsonStructure() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void configEndpoint_ShouldReturnValidJsonStructure() throws Exception {
        mockMvc.perform(get("/config"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.envVarPresent").exists());
    }
}

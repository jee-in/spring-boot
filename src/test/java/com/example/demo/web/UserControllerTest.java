package com.example.demo.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = UserController.class)
@ActiveProfiles("test-trace")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateUser() throws Exception {
        mockMvc.perform(post("/api/v1/users"))
            .andExpect(status().isCreated());
    }

}

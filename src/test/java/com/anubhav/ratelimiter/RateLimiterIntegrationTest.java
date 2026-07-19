package com.anubhav.ratelimiter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RateLimiterIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldAllowRequest() throws Exception {

        String clientId = "test-" + System.currentTimeMillis();

        mockMvc.perform(get("/check")
                        .param("clientId", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.allowed").value(true))
                .andExpect(header().string("X-RateLimit-Limit", "5"))
                .andExpect(header().string("X-RateLimit-Remaining", "4"))
                .andExpect(jsonPath("$.message").value("Request Allowed"));;
    }

    @Test
    void shouldRejectSixthRequest() throws Exception {

        String clientId = "test-" + System.currentTimeMillis();

        for (int i = 1; i <= 5; i++) {
            mockMvc.perform(get("/check")
                            .param("clientId", clientId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.allowed").value(true));
        }

        mockMvc.perform(get("/check")
                        .param("clientId", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.allowed").value(false))
                .andExpect(jsonPath("$.message").value("Rate Limit Exceeded"))
                .andExpect(header().exists("Retry-After"));
    }
}
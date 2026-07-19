package com.anubhav.ratelimiter.limiter;

import com.anubhav.ratelimiter.model.RateLimitResult;
import com.anubhav.ratelimiter.storage.RateLimitStorage;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import com.anubhav.ratelimiter.config.RateLimiterProperties;

class FixedWindowRateLimiterTest {

    @Mock
    private RateLimitStorage storage;

    private FixedWindowRateLimiter limiter;

    @Test
    void test() {
        when(storage.increment("anubhav")).thenReturn(1L);
    }

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        RateLimiterProperties properties = new RateLimiterProperties();
        properties.setMaxRequests(5);
        properties.setWindowSize(60000);

        limiter = new FixedWindowRateLimiter(storage, properties);
    }

    @Test
    void shouldAllowFirstRequest() {

        when(storage.increment("anubhav")).thenReturn(1L);

        RateLimitResult result = limiter.allowRequest("anubhav");

        assertTrue(result.isAllowed());

        assertEquals(4, result.getRemainingRequests());
    }
}
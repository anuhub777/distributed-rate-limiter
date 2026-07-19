package com.anubhav.ratelimiter.limiter;

import com.anubhav.ratelimiter.model.RateLimitResult;
import com.anubhav.ratelimiter.storage.RateLimitStorage;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

import com.anubhav.ratelimiter.config.RateLimiterProperties;

class FixedWindowRateLimiterTest {

    @Mock
    private RateLimitStorage storage;

    private FixedWindowRateLimiter limiter;

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

        verify(storage).setExpiration("anubhav", 60L);
    }

    @Test
    void shouldRejectRequestWhenLimitExceeded() {

        when(storage.increment("anubhav")).thenReturn(6L);
        when(storage.getTimeToLive("anubhav")).thenReturn(45L);

        RateLimitResult result = limiter.allowRequest("anubhav");

        assertFalse(result.isAllowed());
        assertEquals(0, result.getRemainingRequests());
        assertEquals(45L, result.getRetryAfter());
    }

    @Test
    void shouldNotSetExpirationAfterFirstRequest() {

        when(storage.increment("anubhav")).thenReturn(2L);

        limiter.allowRequest("anubhav");

        verify(storage, never()).setExpiration("anubhav", 60L);
    }

    @Test
    void shouldReturnZeroRetryAfterForAllowedRequest() {

        when(storage.increment("anubhav")).thenReturn(3L);

        RateLimitResult result = limiter.allowRequest("anubhav");

        assertTrue(result.isAllowed());
        assertEquals(0L, result.getRetryAfter());
    }
}
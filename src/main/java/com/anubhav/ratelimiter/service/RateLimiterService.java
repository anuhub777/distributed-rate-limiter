package com.anubhav.ratelimiter.service;

import com.anubhav.ratelimiter.limiter.FixedWindowRateLimiter;
import com.anubhav.ratelimiter.limiter.RateLimiter;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

    private final RateLimiter rateLimiter;

    public RateLimiterService() {
        this.rateLimiter = new FixedWindowRateLimiter(5, 60_000);
    }

    public boolean allowRequest(String clientId) {
        return rateLimiter.allowRequest(clientId);
    }
}
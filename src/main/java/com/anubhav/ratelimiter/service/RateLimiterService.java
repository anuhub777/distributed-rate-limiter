package com.anubhav.ratelimiter.service;

import com.anubhav.ratelimiter.limiter.FixedWindowRateLimiter;
import com.anubhav.ratelimiter.limiter.RateLimiter;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

    private final RateLimiter rateLimiter;

    public RateLimiterService(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    public boolean allowRequest(String clientId) {
        return rateLimiter.allowRequest(clientId);
    }
}
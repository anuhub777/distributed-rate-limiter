package com.anubhav.ratelimiter.limiter;

import com.anubhav.ratelimiter.model.RateLimitResult;

public class SlidingWindowRateLimiter implements RateLimiter {

    @Override
    public RateLimitResult allowRequest(String clientId) {
        return null;
    }
}
package com.anubhav.ratelimiter.service;

import com.anubhav.ratelimiter.limiter.FixedWindowRateLimiter;
import com.anubhav.ratelimiter.limiter.RateLimiter;
import com.anubhav.ratelimiter.model.RateLimitResult;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

    private final RateLimiter rateLimiter;

    public RateLimiterService(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    public RateLimitResult allowRequest(String clientId){
        return rateLimiter.allowRequest(clientId);
    }
}
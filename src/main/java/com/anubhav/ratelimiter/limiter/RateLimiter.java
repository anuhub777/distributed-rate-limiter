package com.anubhav.ratelimiter.limiter;

import com.anubhav.ratelimiter.model.RateLimitResult;

public interface RateLimiter {

    RateLimitResult allowRequest(String clientId);

}

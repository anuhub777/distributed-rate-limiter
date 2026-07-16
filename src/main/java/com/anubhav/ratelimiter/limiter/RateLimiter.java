package com.anubhav.ratelimiter.limiter;

public interface RateLimiter {

    boolean allowRequest(String clientId);

}

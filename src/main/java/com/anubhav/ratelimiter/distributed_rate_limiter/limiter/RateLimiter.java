package com.anubhav.ratelimiter.distributed_rate_limiter.limiter;

public interface RateLimiter {

    boolean allowRequest(String clientId);

}

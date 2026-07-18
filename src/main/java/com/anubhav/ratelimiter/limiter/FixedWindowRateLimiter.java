package com.anubhav.ratelimiter.limiter;

import com.anubhav.ratelimiter.config.RateLimiterProperties;
import com.anubhav.ratelimiter.storage.RateLimitStorage;
import org.springframework.stereotype.Component;

@Component
public class FixedWindowRateLimiter implements RateLimiter {

    private final RateLimitStorage storage;
    private final int maxRequests;
    private final long windowSizeInMillis;

    public FixedWindowRateLimiter(
            RateLimitStorage storage,
            RateLimiterProperties properties
    ) {
        this.storage = storage;
        this.maxRequests = properties.getMaxRequests();
        this.windowSizeInMillis = properties.getWindowSize();
    }

    @Override
    public boolean allowRequest(String clientId) {

        String key = "rate_limit:" + clientId;

        Long requestCount = storage.increment(key);

        if (requestCount == 1) {
            storage.setExpiration(key, windowSizeInMillis / 1000);
        }

        return requestCount <= maxRequests;
    }
}
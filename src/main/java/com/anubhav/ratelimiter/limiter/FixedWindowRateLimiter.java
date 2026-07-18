package com.anubhav.ratelimiter.limiter;

import com.anubhav.ratelimiter.config.RateLimiterProperties;
import com.anubhav.ratelimiter.model.RateLimitResult;
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
    public RateLimitResult allowRequest(String clientId) {

        Long requestCount = storage.increment(clientId);

        if (requestCount == 1) {
            storage.setExpiration(clientId, windowSizeInMillis / 1000);
        }

        boolean allowed = requestCount <= maxRequests;

        long remainingRequests = Math.max(0, maxRequests - requestCount);

        long retryAfter = allowed ? 0 : storage.getTimeToLive(clientId);

        return new RateLimitResult(
                allowed,
                remainingRequests,
                retryAfter
        );
    }
}
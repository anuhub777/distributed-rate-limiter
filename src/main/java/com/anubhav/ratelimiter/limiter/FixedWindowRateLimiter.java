package com.anubhav.ratelimiter.limiter;

import com.anubhav.ratelimiter.config.RateLimiterProperties;
import com.anubhav.ratelimiter.model.RateLimitInfo;
import com.anubhav.ratelimiter.storage.RateLimitStorage;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

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

        long currentTime = System.currentTimeMillis();
        RateLimitInfo rateLimitInfo = storage.get(clientId);
        if (rateLimitInfo == null) {

            storage.put(
                    clientId,
                    new RateLimitInfo(1, currentTime)
            );

            return true;
        }

        if (currentTime - rateLimitInfo.getWindowStartTime() >= windowSizeInMillis) {

            rateLimitInfo.setRequestCount(1);
            rateLimitInfo.setWindowStartTime(currentTime);
            return true;
        }

        if (rateLimitInfo.getRequestCount() < maxRequests) {

            rateLimitInfo.setRequestCount(
                    rateLimitInfo.getRequestCount() + 1
            );
            return true;
        }

        return false;
    }
}
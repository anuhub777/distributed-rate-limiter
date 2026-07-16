package com.anubhav.ratelimiter.limiter;

import com.anubhav.ratelimiter.model.RateLimitInfo;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class FixedWindowRateLimiter implements RateLimiter {

    private final ConcurrentHashMap<String, RateLimitInfo> clientRequests;

    private final int maxRequests;

    private final long windowSizeInMillis;

    public FixedWindowRateLimiter() {
        this.clientRequests = new ConcurrentHashMap<>();
        this.maxRequests = 5;
        this.windowSizeInMillis = 60_000;
    }

    @Override
    public boolean allowRequest(String clientId) {

        long currentTime = System.currentTimeMillis();
        RateLimitInfo rateLimitInfo = clientRequests.get(clientId);
        if (rateLimitInfo == null) {

            clientRequests.put(
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
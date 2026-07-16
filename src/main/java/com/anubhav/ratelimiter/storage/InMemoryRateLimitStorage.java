package com.anubhav.ratelimiter.storage;

import com.anubhav.ratelimiter.model.RateLimitInfo;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryRateLimitStorage implements RateLimitStorage {

    private final ConcurrentHashMap<String, RateLimitInfo> storage =
            new ConcurrentHashMap<>();

    @Override
    public RateLimitInfo get(String clientId) {
        return storage.get(clientId);
    }

    @Override
    public void put(String clientId, RateLimitInfo rateLimitInfo) {
        storage.put(clientId, rateLimitInfo);
    }
}
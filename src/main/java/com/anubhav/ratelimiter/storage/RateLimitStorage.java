package com.anubhav.ratelimiter.storage;

import com.anubhav.ratelimiter.model.RateLimitInfo;

public interface RateLimitStorage {

    RateLimitInfo get(String clientId);

    void put(String clientId, RateLimitInfo rateLimitInfo);
}
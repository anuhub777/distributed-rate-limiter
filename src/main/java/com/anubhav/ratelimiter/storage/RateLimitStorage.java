package com.anubhav.ratelimiter.storage;

public interface RateLimitStorage {

    Long increment(String clientId);

    void setExpiration(String clientId, long seconds);

    Long getTimeToLive(String clientId);
}
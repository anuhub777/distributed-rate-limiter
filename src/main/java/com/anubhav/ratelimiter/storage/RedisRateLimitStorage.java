package com.anubhav.ratelimiter.storage;

import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@Primary
public class RedisRateLimitStorage implements RateLimitStorage {

    private static final String KEY_PREFIX = "rate_limit:";
    private final StringRedisTemplate redisTemplate;


    private String buildKey(String clientId) {
        return KEY_PREFIX + clientId;
    }

    public RedisRateLimitStorage(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Long increment(String clientId) {
        return redisTemplate.opsForValue().increment(buildKey(clientId));
    }

    @Override
    public void setExpiration(String clientId, long seconds) {
        redisTemplate.expire(buildKey(clientId), Duration.ofSeconds(seconds));
    }

    @Override
    public Long getTimeToLive(String clientId) {
        return redisTemplate.getExpire(buildKey(clientId));
    }
}
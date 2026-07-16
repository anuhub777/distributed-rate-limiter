package com.anubhav.ratelimiter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "rate.limiter")
public class RateLimiterProperties {

    private int maxRequests;

    private long windowSize;
}
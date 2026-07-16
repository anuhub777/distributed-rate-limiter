package com.anubhav.ratelimiter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RateLimitInfo {

    private int requestCount;
    private long windowStartTime;
}

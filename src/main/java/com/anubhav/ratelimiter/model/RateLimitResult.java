package com.anubhav.ratelimiter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RateLimitResult {

    private boolean allowed;

    private long remainingRequests;

    private long retryAfter;

}
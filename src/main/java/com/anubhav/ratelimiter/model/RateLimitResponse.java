package com.anubhav.ratelimiter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RateLimitResponse {

    private boolean allowed;
    private String message;
}
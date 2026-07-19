package com.anubhav.ratelimiter.controller;

import com.anubhav.ratelimiter.config.RateLimiterProperties;
import com.anubhav.ratelimiter.model.RateLimitResponse;
import com.anubhav.ratelimiter.model.RateLimitResult;
import com.anubhav.ratelimiter.service.RateLimiterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimiterController {

    private final RateLimiterService rateLimiterService;
    private final RateLimiterProperties properties;

    public RateLimiterController(
            RateLimiterService rateLimiterService,
            RateLimiterProperties properties) {

        this.rateLimiterService = rateLimiterService;
        this.properties = properties;
    }

    @GetMapping("/check")
    public ResponseEntity<RateLimitResponse> checkRateLimit(
            @RequestParam String clientId) {

        RateLimitResult result = rateLimiterService.allowRequest(clientId);

        RateLimitResponse response = new RateLimitResponse(
                result.isAllowed(),
                result.isAllowed() ? "Request Allowed" : "Rate Limit Exceeded",
                result.getRemainingRequests(),
                result.getRetryAfter()
        );

        return ResponseEntity
                .ok()
                .header(
                        "X-RateLimit-Limit",
                        String.valueOf(properties.getMaxRequests())
                )
                .header("X-RateLimit-Remaining",
                        String.valueOf(result.getRemainingRequests()))
                .header("Retry-After",
                        String.valueOf(result.getRetryAfter()))
                .body(response);
    }
}
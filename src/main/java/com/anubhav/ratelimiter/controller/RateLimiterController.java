package com.anubhav.ratelimiter.controller;

import com.anubhav.ratelimiter.model.RateLimitResponse;
import com.anubhav.ratelimiter.model.RateLimitResult;
import com.anubhav.ratelimiter.service.RateLimiterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimiterController {

    private final RateLimiterService rateLimiterService;

    public RateLimiterController(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @GetMapping("/check")
    public RateLimitResponse checkRateLimit(@RequestParam String clientId) {

        RateLimitResult result = rateLimiterService.allowRequest(clientId);

        return new RateLimitResponse(
                result.isAllowed(),
                result.isAllowed()
                        ? "Request Allowed"
                        : "Rate Limit Exceeded",
                result.getRemainingRequests(),
                result.getRetryAfter()
        );
    }
}
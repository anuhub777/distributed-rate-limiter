package com.anubhav.ratelimiter.controller;

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
    public String checkRateLimit(@RequestParam String clientId) {

        boolean allowed = rateLimiterService.allowRequest(clientId);

        if (allowed) {
            return "Request Allowed";
        }

        return "Rate Limit Exceeded";
    }
}
package com.anubhav.ratelimiter.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI rateLimiterOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Distributed Rate Limiter API")
                        .version("1.0")
                        .description("A Redis-backed distributed fixed window rate limiter built with Spring Boot.")
                        .contact(new Contact()
                                .name("Anubhav Verma")
                                .email("anubhav15151591@gmail.com")
                                .url("https://github.com/anuhub777")));
    }
}
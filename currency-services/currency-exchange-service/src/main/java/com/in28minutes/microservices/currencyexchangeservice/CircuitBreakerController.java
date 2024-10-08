package com.in28minutes.microservices.currencyexchangeservice;


import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@RestController
public class CircuitBreakerController {
    private final Logger logger = Logger.getLogger(CircuitBreakerController.class.getName());

    @GetMapping("/sample-api")
//    @Retry(name="sample-api", fallbackMethod = "defaultFailResponse")
//    @CircuitBreaker(name="default", fallbackMethod = "defaultFailResponse")
//    @RateLimiter(name="sample-api")
    @Bulkhead(name="sample-api")
    public String sampleApi() {
        logger.info("Sample API call received");

//        RestTemplate restTemplate = new RestTemplate();
//        return restTemplate.getForEntity("http://localhost:8080/some-dummy-url", String.class).getBody();

        return "Sample API";
    }

    public String defaultFailResponse(Exception e) {
        return "Service failed to reply";
    }
}

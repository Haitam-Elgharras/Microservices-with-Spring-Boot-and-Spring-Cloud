package com.in28minutes.microservices.limitsservice.controller;

import com.in28minutes.microservices.limitsservice.config.Configuration;
import com.in28minutes.microservices.limitsservice.doa.entities.Limits;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LimitsController {
    private final Configuration config;

    @GetMapping("/limits")
    public Limits retrieveLimits() {
        return new Limits(config.getMinimum(),config.getMaximum());
    }
}

package com.in28minutes.microservices.currencyexchangeservice;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
public class CurrencyExchangeController {
    private Environment environment;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
        String port = environment.getProperty("local.server.port");
        return new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(65), port);
    }

}

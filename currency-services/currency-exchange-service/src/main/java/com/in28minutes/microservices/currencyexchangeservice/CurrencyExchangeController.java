package com.in28minutes.microservices.currencyexchangeservice;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CurrencyExchangeController {
    private final Environment environment;
    private final CurrencyExchangeRepository repository;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
        logger.info("retrieveExchangeValue called with {} to {}", from, to);

        String port = environment.getProperty("local.server.port");
        CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);

        if (currencyExchange == null) {
            throw new RuntimeException("Unable to find data for " + from + " to " + to);
        }

        currencyExchange.setEnvironment(port);

        return currencyExchange;
    }

}

package com.in28minutes.limitsservice.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/*
    This class is used to read the properties from the application.properties file
    The properties are read using the @ConfigurationProperties annotation
    the prefix is the prefix of the properties that are to be read
*/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties("limits-service")
public class Configuration {
    private int minimum;
    private int maximum;
}

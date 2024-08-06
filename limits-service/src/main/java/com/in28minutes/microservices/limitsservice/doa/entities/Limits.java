package com.in28minutes.microservices.limitsservice.doa.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Limits {
    int minimum;
    int maximum;
}
package com.javaacademy.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@Getter
@Setter
@ConfigurationProperties("app.economic")
public class EconomicProperty {
    private String currency;
    private BigDecimal rateKwh;
}

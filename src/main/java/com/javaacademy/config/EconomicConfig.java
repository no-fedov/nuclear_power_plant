package com.javaacademy.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(EconomicProperty.class)
public class EconomicConfig {
}

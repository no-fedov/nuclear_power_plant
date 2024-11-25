package com.javaacademy;

import com.javaacademy.station.NuclearStation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Runner {
    private static final int NUMBER_WORKING_YEARS = 3;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Runner.class, args);
        NuclearStation nuclearStation = context.getBean(NuclearStation.class);
        nuclearStation.start(NUMBER_WORKING_YEARS);
    }
}

package com.javaacademy.integration;

import com.javaacademy.NuclearStation;
import com.javaacademy.department.reactor.ReactorDepartment;
import com.javaacademy.department.security.SecurityDepartment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles("france")
@SpringBootTest
public class NuclearStationIntegrationTests {
    @Autowired
    private NuclearStation nuclearStation;

    @SpyBean
    private SecurityDepartment securityDepartment;

    @SpyBean
    private ReactorDepartment reactorDepartment;

    @Test
    @DisplayName("Реактор запускали 365 раз, 362 успешных запуска")
    public void runWorkStationOnYear() {
        int yearCount = 1;
        int dayCount = 365;
        int exceptionCount = 3;
        nuclearStation.start(yearCount);
        verify(reactorDepartment, times(dayCount)).run();
        verify(reactorDepartment, times(dayCount - exceptionCount)).stop();
        verify(securityDepartment, times(exceptionCount)).addAccident();
    }
}

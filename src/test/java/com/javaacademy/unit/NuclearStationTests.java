package com.javaacademy.unit;

import com.javaacademy.NuclearStation;
import com.javaacademy.config.EconomicProperty;
import com.javaacademy.department.economic.EconomicDepartment;
import com.javaacademy.department.reactor.ReactorDepartment;
import com.javaacademy.department.security.SecurityDepartment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class NuclearStationTests {
    @Autowired
    private NuclearStation nuclearStation;

    @MockBean
    private ReactorDepartment reactorDepartment;

    @MockBean
    private SecurityDepartment securityDepartment;

    @MockBean
    private EconomicDepartment economicDepartment;

    @MockBean
    private EconomicProperty economicProperty;

    @Test
    @DisplayName("Запуск реактора на год")
    public void runNuclearStation() {
        when(reactorDepartment.run()).thenReturn(ZERO);
        when(economicDepartment.getEconomicProperty()).thenReturn(new EconomicProperty());
        assertDoesNotThrow(() -> nuclearStation.startYear());
        verify(securityDepartment, times(1)).reset();
    }

    @Test
    @DisplayName("Запуск станции на 5 лет")
    public void runNuclearStation5Years() {
        int years = 5;
        when(reactorDepartment.run()).thenReturn(ZERO);
        when(economicDepartment.getEconomicProperty()).thenReturn(new EconomicProperty());
        assertDoesNotThrow(() -> nuclearStation.start(years));
        verify(securityDepartment, times(years)).reset();
    }

    @Test
    @DisplayName("Станция фиксирует инцидент")
    public void addAccident() {
        assertDoesNotThrow(() -> nuclearStation.incrementAccident(1));
    }
}

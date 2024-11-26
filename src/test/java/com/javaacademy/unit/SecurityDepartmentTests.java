package com.javaacademy.unit;

import com.javaacademy.NuclearStation;
import com.javaacademy.department.security.SecurityDepartment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class SecurityDepartmentTests {
    @Autowired
    private SecurityDepartment securityDepartment;

    @MockBean
    private NuclearStation nuclearStation;

    @Test
    @DisplayName("Фиксирует инцидент")
    public void incrementAccidentSuccess() {
        securityDepartment.addAccident();
        assertEquals(1, securityDepartment.getCountAccidents());
    }

    @Test
    @DisplayName("Обнуление счетчика инцидентов и передача данных станции")
    public void resetAccidentCounterAndTransmissionDataToStation() {
        securityDepartment.addAccident();
        securityDepartment.reset();
        assertEquals(0, securityDepartment.getCountAccidents());
        verify(nuclearStation, times(1)).incrementAccident(anyInt());
    }
}

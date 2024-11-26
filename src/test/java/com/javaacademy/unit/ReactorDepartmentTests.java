package com.javaacademy.unit;

import com.javaacademy.NuclearStation;
import com.javaacademy.department.reactor.ReactorDepartment;
import com.javaacademy.department.security.SecurityDepartment;
import com.javaacademy.exception.NuclearFuelIsEmptyException;
import com.javaacademy.exception.ReactorWorkException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ReactorDepartmentTests {
    @Autowired
    private ReactorDepartment reactorDepartment;

    @MockBean
    private SecurityDepartment securityDepartment;

    @MockBean
    private NuclearStation nuclearStation;

    @Test
    @DisplayName("Успешный запуск реатора")
    public void runReactorSuccess() {
        BigDecimal result = reactorDepartment.run();
        assertTrue(reactorDepartment.isWorks());
        assertEquals(0, result.compareTo(BigDecimal.valueOf(10_000_000)));
    }

    @Test
    @DisplayName("Ошибка при повторном запуске реактора")
    public void runReactorThrowReactorWorkException() {
        reactorDepartment.run();
        assertThrows(ReactorWorkException.class, () -> reactorDepartment.run());
        verify(securityDepartment, times(1)).addAccident();
    }

    @Test
    @DisplayName("Ошибка при запуске реактора без топлива")
    public void runReactorWithoutFuel() {
        int launchCountBeforeException = 99;
        AtomicInteger numberLaunch = new AtomicInteger(0);
        long currentLaunchCount = Stream.generate(() -> numberLaunch.addAndGet(1))
                .limit(launchCountBeforeException)
                .peek(launch -> {
                    reactorDepartment.run();
                    reactorDepartment.stop();
                })
                .count();
        assertEquals(launchCountBeforeException, currentLaunchCount);
        assertThrows(NuclearFuelIsEmptyException.class, () -> reactorDepartment.run());
        verify(securityDepartment, times(1)).addAccident();
    }

    @Test
    @DisplayName("Успешная остановка реактора")
    public void stopReactorSuccess() {
        reactorDepartment.run();
        reactorDepartment.stop();
        assertFalse(reactorDepartment.isWorks());
    }

    @Test
    @DisplayName("Ошибка при повторной остановке реактора")
    public void stopReactorAfterStopReactor() {
        assertFalse(reactorDepartment.isWorks());
        assertThrows(ReactorWorkException.class, () -> reactorDepartment.stop());
    }
}


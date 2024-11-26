package com.javaacademy.department.reactor;

import com.javaacademy.department.security.SecurityDepartment;
import com.javaacademy.exception.NuclearFuelIsEmptyException;
import com.javaacademy.exception.ReactorWorkException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

@Slf4j
@Component
public class ReactorDepartment {
    private static final BigDecimal ENERGY_GENERATED_IN_ONE_DAY = valueOf(10_000_000);
    private static final int NUMBER_LAUNCHES_BEFORE_FUEL_OVERLOAD = 100;

    @Getter
    private boolean isWorks;
    private int launchCounter;

    @Setter
    @Autowired
    private SecurityDepartment securityDepartment;

    /**
     * Запускает реактор
     * @return количество выработанной энергии в кВт/ч
     */
    public BigDecimal run() {
        if (isWorks) {
            log.warn("Попытка повторно запустить реактор");
            this.securityDepartment.addAccident();
            throw new ReactorWorkException("Реактор уже работает");
        }
        launchCounter++;
        if (launchCounter % NUMBER_LAUNCHES_BEFORE_FUEL_OVERLOAD == 0) {
            log.warn("Закончилось топливо");
            this.securityDepartment.addAccident();
            throw new NuclearFuelIsEmptyException("Закончилось топливо");
        }
        this.isWorks = true;
        return ENERGY_GENERATED_IN_ONE_DAY;
    }

    /**
     * Останавливает реактор
     */
    public void stop() {
        if (!isWorks) {
            log.warn("Попытка повторно остановить реактор");
            this.securityDepartment.addAccident();
            throw new ReactorWorkException("Реактор уже выключен");
        }
        this.isWorks = false;
    }
}

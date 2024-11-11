package com.javaacademy.department;

import com.javaacademy.exception.NuclearFuelIsEmptyException;
import com.javaacademy.exception.ReactorWorkException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReactorDepartment {
    private static final int ENERGY_GENERATED_IN_ONE_DAY = 10;
    private static final int NUMBER_LAUNCHES_BEFORE_FUEL_OVERLOAD = 100;
    private boolean isWorks;
    private int launchCounter;

    /**
     * Включает реактор
     * @return количество выработанной энергии
     */
    public int run() {
        if (isWorks) {
            log.warn("Попытка повторно запустить реактор");
            throw new ReactorWorkException("Реактор уже работает");
        }
        launchCounter++;
        if (launchCounter % NUMBER_LAUNCHES_BEFORE_FUEL_OVERLOAD == 0) {
            log.warn("Закончилось топливо");
            throw new NuclearFuelIsEmptyException("Закончилось топливо");
        }
        this.isWorks = true;
        return ENERGY_GENERATED_IN_ONE_DAY;
    }

    /**
     * Выключает реактор
     */
    public void stop() {
        if (!isWorks) {
            log.warn("Попытка повторно остановить реактор");
            throw new ReactorWorkException("Реактор уже выключен");
        }
        this.isWorks = false;
    }
}

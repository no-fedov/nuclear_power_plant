package com.javaacademy.department.security;

import com.javaacademy.NuclearStation;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Отдел безопасности
 */
@Component
public class SecurityDepartment {
    private final NuclearStation nuclearStation;
    private int accidentCountPeriod;

    public SecurityDepartment(@Lazy NuclearStation nuclearStation) {
        this.nuclearStation = nuclearStation;
    }

    /**
     * Засчитывает инцидент
     */
    public void addAccident() {
        accidentCountPeriod++;
    }

    /**
     * Передает данные о случившихся инцидентах на "Атомную станцию" и сбрасывает счетчик в отделе безопасности
     */
    public void reset() {
        this.nuclearStation.incrementAccident(accidentCountPeriod);
        this.accidentCountPeriod = 0;
    }

    public int getCountAccidents() {
        return accidentCountPeriod;
    }
}

package com.javaacademy.department;

import com.javaacademy.station.NuclearStation;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Отдел безопасности
 */
@Component
public class SecurityDepartment {
    private int accidentCountPeriod;
    private final NuclearStation nuclearStation;

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

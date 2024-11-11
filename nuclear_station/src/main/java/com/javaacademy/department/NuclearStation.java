package com.javaacademy.department;

import com.javaacademy.exception.NuclearFuelIsEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NuclearStation {
    private static final int DAYS = 365;

    private final ReactorDepartment reactorDepartment;
    private int generatedEnergy;

    public NuclearStation(ReactorDepartment reactorDepartment) {
        this.reactorDepartment = reactorDepartment;
    }

    public void startYear() {
        log.info("Атомная станция начала работу");
        int generatedEnergyForYear = 0;
        int counter = 0;
        while (counter < DAYS) {
            counter++;
            try {
                generatedEnergyForYear += reactorWorkOneDay();
            } catch (NuclearFuelIsEmptyException e) {
                log.warn("Внимание! Происходят работы на атомной станции! Электричества нет!");
            }
        }
        generatedEnergy += generatedEnergyForYear;
        log.info("Атомная станция закончила работу. За год выработано {} миллионов киловатт/часов.",
                generatedEnergyForYear);
    }

    public void start(int year) {
        while (year > 0) {
            startYear();
            year--;
        }
    }

    private int reactorWorkOneDay() {
        int energy = reactorDepartment.run();
        reactorDepartment.stop();
        return energy;
    }
}

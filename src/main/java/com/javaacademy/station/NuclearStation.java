package com.javaacademy.station;

import com.javaacademy.department.ReactorDepartment;
import com.javaacademy.department.SecurityDepartment;
import com.javaacademy.exception.NuclearFuelIsEmptyException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

@Slf4j
@Component
public class NuclearStation {
    private static final int DAYS = 365;

    private BigDecimal generatedEnergy = ZERO;

    private int accidentCountAllTime;
    private final ReactorDepartment reactorDepartment;

    @Autowired
    @Setter
    private SecurityDepartment securityDepartment;

    public NuclearStation(ReactorDepartment reactorDepartment) {
        this.reactorDepartment = reactorDepartment;
    }

    public void startYear() {
        log.info("Атомная станция начала работу");
        BigDecimal generatedEnergyForYear = ZERO;
        int counter = 0;
        while (counter < DAYS) {
            counter++;
            try {
                generatedEnergyForYear = generatedEnergyForYear.add(reactorWorkOneDay());
            } catch (NuclearFuelIsEmptyException e) {
                log.warn("Внимание! Происходят работы на атомной станции! Электричества нет!");
            }
        }
        generatedEnergy = generatedEnergy.add(generatedEnergyForYear);
        log.info("Атомная станция закончила работу. За год выработано {} киловатт/часов.",
                generatedEnergyForYear);
        log.info("Количество инцидентов за год: {}", securityDepartment.getCountAccidents());
        securityDepartment.reset();
    }

    public void start(int year) {
        while (year > 0) {
            startYear();
            --year;
        }
        log.info("Количество инцидентов за всю работу станции: {}", accidentCountAllTime);
    }

    public void incrementAccident(int count) {
        this.accidentCountAllTime += count;
    }

    private BigDecimal reactorWorkOneDay() {
        BigDecimal energy = reactorDepartment.run();
        reactorDepartment.stop();
        return energy;
    }
}

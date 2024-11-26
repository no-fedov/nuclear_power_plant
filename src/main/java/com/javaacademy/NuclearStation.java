package com.javaacademy;

import com.javaacademy.department.economic.EconomicDepartment;
import com.javaacademy.department.reactor.ReactorDepartment;
import com.javaacademy.department.security.SecurityDepartment;
import com.javaacademy.exception.NuclearFuelIsEmptyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

@Slf4j
@Component
@RequiredArgsConstructor
public class NuclearStation {
    private static final int DAYS = 365;

    private final ReactorDepartment reactorDepartment;
    private final SecurityDepartment securityDepartment;
    private final EconomicDepartment economicDepartment;

    private BigDecimal generatedEnergy = ZERO;
    private int accidentCountAllTime;

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
        log.info("Доход за год составил: {} {}",
                economicDepartment.computeYearIncomes(generatedEnergyForYear.longValue()),
                economicDepartment.getEconomicProperty().getCurrency());
    }

    public void start(int year) {
        log.info("Действие происходит в стране: {}", economicDepartment.getCountry());
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

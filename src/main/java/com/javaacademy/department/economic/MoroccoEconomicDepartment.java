package com.javaacademy.department.economic;

import com.javaacademy.config.EconomicProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

@Profile("morocco")
@Component
public class MoroccoEconomicDepartment extends EconomicDepartment {
    private static final long LIMIT_ENERGY_CONST_PRICE = 5_000_000_000L;
    private static final BigDecimal PRICE_ON_INCREASED_INCOME = valueOf(6);

    public MoroccoEconomicDepartment(EconomicProperty economicProperty, @Value("${app.country}") String country) {
        super(economicProperty, country);
    }

    @Override
    public BigDecimal computeYearIncomes(long countElectricity) {
        BigDecimal resultIncomes = ZERO;

        if (countElectricity >= LIMIT_ENERGY_CONST_PRICE) {
            resultIncomes = resultIncomes.add(
                    economicProperty.getRateKwh()
                            .multiply(valueOf(LIMIT_ENERGY_CONST_PRICE))
            );
            countElectricity -= LIMIT_ENERGY_CONST_PRICE;
        } else {
            return economicProperty.getRateKwh()
                    .multiply(valueOf(countElectricity));
        }

        resultIncomes = resultIncomes.add(valueOf(countElectricity).multiply(PRICE_ON_INCREASED_INCOME));
        return resultIncomes;
    }
}

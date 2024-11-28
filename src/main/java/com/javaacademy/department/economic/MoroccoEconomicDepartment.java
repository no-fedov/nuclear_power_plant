package com.javaacademy.department.economic;

import com.javaacademy.config.EconomicProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

@Profile("morocco")
@Component
public class MoroccoEconomicDepartment extends EconomicDepartment {
    private static final long LIMIT_ENERGY_CONST_PRICE = 5_000_000_000L;
    private static final BigDecimal PRICE_ON_INCREASED_INCOME = valueOf(6);

    public MoroccoEconomicDepartment(EconomicProperty economicProperty,
                                     @Value("${app.country}") String country) {
        super(economicProperty, country);
    }

    @Override
    public BigDecimal computeYearIncomes(long countElectricity) {
        BigDecimal resultIncomes = ZERO;
        BigDecimal priceForEnergy = economicProperty.getRateKwh();

        if (countElectricity <= 0) {
            return resultIncomes;
        }

        if (countElectricity >= LIMIT_ENERGY_CONST_PRICE) {
            resultIncomes = resultIncomes.add(
                    calculateIncome(LIMIT_ENERGY_CONST_PRICE, priceForEnergy)
            );
            countElectricity -= LIMIT_ENERGY_CONST_PRICE;
        } else {
            return calculateIncome(countElectricity, priceForEnergy);
        }

        resultIncomes = resultIncomes.add(
                calculateIncome(countElectricity, PRICE_ON_INCREASED_INCOME)
        );
        return resultIncomes;
    }

    private BigDecimal calculateIncome(long countElectricity, BigDecimal price) {
        return valueOf(countElectricity)
                .multiply(price)
                .setScale(2, HALF_UP);
    }
}

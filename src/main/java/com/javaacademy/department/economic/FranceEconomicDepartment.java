package com.javaacademy.department.economic;

import com.javaacademy.config.EconomicProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

@Profile("france")
@Component
public class FranceEconomicDepartment extends EconomicDepartment {
    private static final long LIMIT_ENERGY_THEN_CHANGE_PRICE = 1_000_000_000L;
    private static final BigDecimal PERCENT_OF_PRICE_AFTER_DISCOUNT = valueOf(0.99);

    public FranceEconomicDepartment(EconomicProperty economicProperty,
                                    @Value("${app.country}") String country) {
        super(economicProperty, country);
    }

    @Override
    public BigDecimal computeYearIncomes(long countElectricity) {
        BigDecimal resultIncomes = ZERO;
        BigDecimal currentDiscount = ONE;

        while (countElectricity > 0) {
            if (countElectricity > LIMIT_ENERGY_THEN_CHANGE_PRICE) {
                countElectricity -= LIMIT_ENERGY_THEN_CHANGE_PRICE;
                resultIncomes = resultIncomes.add(
                        economicProperty.getRateKwh()
                                .multiply(valueOf(LIMIT_ENERGY_THEN_CHANGE_PRICE))
                                .multiply(currentDiscount)
                );
            } else {
                resultIncomes = resultIncomes.add(
                        economicProperty.getRateKwh()
                                .multiply(valueOf(countElectricity))
                                .multiply(currentDiscount)
                );
                countElectricity = 0;
            }
            currentDiscount = currentDiscount.multiply(PERCENT_OF_PRICE_AFTER_DISCOUNT);
        }
        return resultIncomes;
    }
}

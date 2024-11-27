package com.javaacademy.integration;

import com.javaacademy.department.economic.EconomicDepartment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("france")
public class FranceEconomicDepartmentIntegrationTests {
    @Autowired
    private EconomicDepartment economicDepartment;

    @Test
    @DisplayName("Расчет дохода за энергию с отрицательным значением")
    public void computeIncomesForNegativeEnergy() {
        BigDecimal resultIncomes = economicDepartment.computeYearIncomes(-1);
        assertEquals(0, resultIncomes.compareTo(ZERO));
    }

    @Test
    @DisplayName("Расчет дохода за энергию, когда цена остается фиксированной")
    public void computeIncomesWithConstPrice() {
        long energyLimitWithConstPrice = 1_000_000_000L;
        BigDecimal expectedIncomes = valueOf(500_000_000);
        BigDecimal resultIncomes = economicDepartment.computeYearIncomes(energyLimitWithConstPrice);
        assertEquals(0, expectedIncomes.compareTo(resultIncomes));
    }

    @Test
    @DisplayName("Рачсет дохода за энергию с применением скидки")
    public void computeIncomesWithDiscount() {
        long energyForSale = 1_500_000_000;
        long energyLimitWithConstPrice = 1_000_000_000L;
        double price = 0.5;
        double discount = 0.99;
        BigDecimal expectedIncomes = valueOf(energyLimitWithConstPrice)
                .multiply(valueOf(price))
                .add(valueOf(energyForSale - energyLimitWithConstPrice)
                        .multiply(valueOf(price).multiply(valueOf(discount)))
                );
        BigDecimal resultIncomes = economicDepartment.computeYearIncomes(energyForSale);
        assertEquals(0, expectedIncomes.compareTo(resultIncomes));
    }
}

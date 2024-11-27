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

@ActiveProfiles("morocco")
@SpringBootTest
public class MoroccoEconomicDepartmentIntegrationTests {

    @Autowired
    EconomicDepartment economicDepartment;

    @Test
    @DisplayName("Расчет дохода за энергию с отрицательным значением")
    public void computeIncomesForNegativeEnergy() {
        BigDecimal resultIncomes = economicDepartment.computeYearIncomes(-1);
        assertEquals(0, resultIncomes.compareTo(ZERO));
    }

    @Test
    @DisplayName("Расчет дохода за энергию, когда цена остается фиксированной")
    public void computeIncomesWithConstPrice() {
        long energyLimitWithConstPrice = 5_000_000_000L;
        BigDecimal expectedIncomes = valueOf(25_000_000_000L);
        BigDecimal resultIncomes = economicDepartment.computeYearIncomes(energyLimitWithConstPrice);
        assertEquals(0, expectedIncomes.compareTo(resultIncomes));
    }

    @Test
    @DisplayName("Рачсет дохода за энергию с применением повешенной цены")
    public void computeIncomesWithDiscount() {
        long energyForSale = 6_000_000_000L;
        long energyLimitWithConstPrice = 5_000_000_000L;
        double price = 5;
        double increasedPrice = 6;

        BigDecimal expectedIncomes = valueOf(energyLimitWithConstPrice)
                .multiply(valueOf(price))
                .add(valueOf(energyForSale - energyLimitWithConstPrice)
                        .multiply(valueOf(increasedPrice))
                );
        BigDecimal resultIncomes = economicDepartment.computeYearIncomes(energyForSale);
        assertEquals(0, expectedIncomes.compareTo(resultIncomes));
    }
}

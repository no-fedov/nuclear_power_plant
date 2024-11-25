package com.javaacademy.department.economic;

import com.javaacademy.config.EconomicProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public abstract class EconomicDepartment {
    protected final EconomicProperty economicProperty;
    protected final String country;

    public abstract BigDecimal computeYearIncomes(long countElectricity);
}

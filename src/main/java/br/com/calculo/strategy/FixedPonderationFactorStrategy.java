package br.com.calculo.strategy;

import java.math.BigDecimal;

public class FixedPonderationFactorStrategy implements PonderationFactorStrategy {

    private static final BigDecimal FIXED_FACTOR = new BigDecimal("0.5");

    @Override
    public BigDecimal factor() {
        return FIXED_FACTOR;
    }
}

package br.com.calculo.util;

import java.math.MathContext;
import java.math.RoundingMode;

public final class CalculationConstants {

    public static final int SCALE_INTERNAL = 16;
    public static final int SCALE_MONEY = 2;
    public static final int SCALE_PERCENT_PRINT = 2;
    public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    public static final MathContext MATH_CONTEXT = new MathContext(20, ROUNDING_MODE);

    private CalculationConstants() {
    }
}

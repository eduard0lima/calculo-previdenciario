package br.com.calculo.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public final class ReportFormatter {

    private static final Locale LOCALE_BR = new Locale("pt", "BR");

    private ReportFormatter() {
    }

    public static String formatPercent(BigDecimal decimalValue) {
        NumberFormat percent = NumberFormat.getPercentInstance(LOCALE_BR);
        percent.setMinimumFractionDigits(CalculationConstants.SCALE_PERCENT_PRINT);
        percent.setMaximumFractionDigits(CalculationConstants.SCALE_PERCENT_PRINT);
        return percent.format(decimalValue);
    }

    public static String formatMoney(BigDecimal value) {
        NumberFormat currency = NumberFormat.getCurrencyInstance(LOCALE_BR);
        currency.setMinimumFractionDigits(CalculationConstants.SCALE_MONEY);
        currency.setMaximumFractionDigits(CalculationConstants.SCALE_MONEY);
        return currency.format(value);
    }
}

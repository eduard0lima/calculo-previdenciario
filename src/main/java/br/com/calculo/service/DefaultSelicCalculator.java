package br.com.calculo.service;

import br.com.calculo.domain.SelicInput;
import br.com.calculo.domain.SelicResult;
import br.com.calculo.strategy.CurrentMonthExclusionStrategy;
import br.com.calculo.strategy.MonthCountStrategy;
import br.com.calculo.strategy.PonderationFactorStrategy;
import br.com.calculo.util.CalculationConstants;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.Objects;

public class DefaultSelicCalculator implements SelicCalculator {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
    private static final BigDecimal TWELVE = new BigDecimal("12");
    private static final BigDecimal ONE = BigDecimal.ONE;
    private static final BigDecimal ZERO = BigDecimal.ZERO;

    private final MonthCountStrategy monthCountStrategy;
    private final CurrentMonthExclusionStrategy currentMonthExclusionStrategy;
    private final PonderationFactorStrategy ponderationFactorStrategy;

    public DefaultSelicCalculator(MonthCountStrategy monthCountStrategy,
                                  CurrentMonthExclusionStrategy currentMonthExclusionStrategy,
                                  PonderationFactorStrategy ponderationFactorStrategy) {
        this.monthCountStrategy = Objects.requireNonNull(monthCountStrategy, "monthCountStrategy é obrigatório");
        this.currentMonthExclusionStrategy = Objects.requireNonNull(currentMonthExclusionStrategy, "currentMonthExclusionStrategy é obrigatório");
        this.ponderationFactorStrategy = Objects.requireNonNull(ponderationFactorStrategy, "ponderationFactorStrategy é obrigatório");
    }

    @Override
    public SelicResult calculate(SelicInput input) {
        Objects.requireNonNull(input, "input é obrigatório");

        int mesesTotais = monthCountStrategy.countDistinctMonths(input.getDataInicial(), input.getDataFinal());
        int anoCorrente = input.getDataAcordo().getYear();

        int mesesAtuais = countMonthsInCurrentYear(input, anoCorrente);
        int mesesAnteriores = mesesTotais - mesesAtuais;

        BigDecimal bdMesesTotais = BigDecimal.valueOf(mesesTotais);
        BigDecimal percentualMesesAnteriores = BigDecimal.valueOf(mesesAnteriores)
                .divide(bdMesesTotais, CalculationConstants.SCALE_INTERNAL, CalculationConstants.ROUNDING_MODE);
        BigDecimal percentualMesesAtuais = ONE.subtract(percentualMesesAnteriores);

        BigDecimal valorRefMesesAnteriores = input.getValorTotal().multiply(percentualMesesAnteriores, CalculationConstants.MATH_CONTEXT);
        BigDecimal valorRefMesesAtuais = input.getValorTotal().subtract(valorRefMesesAnteriores, CalculationConstants.MATH_CONTEXT);

        int mesesEfetivos = currentMonthExclusionStrategy.applyExclusion(mesesTotais, input.getDataFinal(), input.getDataAcordo());
        BigDecimal taxaPeriodoLinear = input.getSelicAnualPercent()
                .divide(ONE_HUNDRED, CalculationConstants.SCALE_INTERNAL, CalculationConstants.ROUNDING_MODE)
                .multiply(BigDecimal.valueOf(mesesEfetivos), CalculationConstants.MATH_CONTEXT)
                .divide(TWELVE, CalculationConstants.SCALE_INTERNAL, CalculationConstants.ROUNDING_MODE);

        BigDecimal taxaPeriodoAjustada = taxaPeriodoLinear.multiply(ponderationFactorStrategy.factor(), CalculationConstants.MATH_CONTEXT);

        BigDecimal percentualSelic = ZERO;
        if (taxaPeriodoAjustada.compareTo(ZERO) > 0) {
            percentualSelic = taxaPeriodoAjustada.divide(ONE.add(taxaPeriodoAjustada), CalculationConstants.SCALE_INTERNAL, RoundingMode.HALF_UP);
        }

        BigDecimal valorSelic = input.getValorTotal().multiply(percentualSelic, CalculationConstants.MATH_CONTEXT);
        BigDecimal percentualJuros = ZERO;
        BigDecimal valorJuros = ZERO;
        BigDecimal valorPrincipal = input.getValorTotal().subtract(valorSelic, CalculationConstants.MATH_CONTEXT).subtract(valorJuros, CalculationConstants.MATH_CONTEXT);

        return new SelicResult(
                mesesTotais,
                mesesEfetivos,
                mesesAnteriores,
                mesesAtuais,
                percentualMesesAnteriores,
                percentualMesesAtuais,
                valorRefMesesAnteriores,
                valorRefMesesAtuais,
                taxaPeriodoLinear,
                taxaPeriodoAjustada,
                percentualSelic,
                percentualJuros,
                valorSelic,
                valorJuros,
                valorPrincipal
        );
    }

    private int countMonthsInCurrentYear(SelicInput input, int anoCorrente) {
        YearMonth inicio = YearMonth.from(input.getDataInicial());
        YearMonth fim = YearMonth.from(input.getDataFinal());
        int count = 0;
        for (YearMonth cursor = inicio; !cursor.isAfter(fim); cursor = cursor.plusMonths(1)) {
            if (cursor.getYear() == anoCorrente) {
                count++;
            }
        }
        return count;
    }
}

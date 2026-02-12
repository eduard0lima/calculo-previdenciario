package br.com.calculo.service;

import br.com.calculo.domain.SelicInput;
import br.com.calculo.domain.SelicResult;
import br.com.calculo.strategy.DistinctTouchedMonthCountStrategy;
import br.com.calculo.strategy.FixedPonderationFactorStrategy;
import br.com.calculo.strategy.OpenCurrentMonthExclusionStrategy;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultSelicCalculatorTest {

    private final SelicCalculator calculator = new DefaultSelicCalculator(
            new DistinctTouchedMonthCountStrategy(),
            new OpenCurrentMonthExclusionStrategy(),
            new FixedPonderationFactorStrategy()
    );

    @Test
    void deveBaterGabaritoExcel() {
        SelicInput input = new SelicInput(
                LocalDate.of(2019, 2, 1),
                LocalDate.of(2026, 2, 4),
                LocalDate.of(2026, 2, 4),
                new BigDecimal("8500.00"),
                new BigDecimal("15.00")
        );

        SelicResult result = calculator.calculate(input);

        assertEquals(85, result.getMesesTotais());
        assertEquals(2, result.getMesesAtuais());
        assertEquals(83, result.getMesesAnteriores());
        assertEquals(84, result.getMesesEfetivos());

        assertEquals(new BigDecimal("0.9764705882352941"), result.getPercentualMesesAnteriores());
        assertEquals(new BigDecimal("0.0235294117647059"), result.getPercentualMesesAtuais());
        assertEquals(new BigDecimal("1.0500000000000000"), result.getTaxaPeriodoLinear());
        assertEquals(new BigDecimal("0.52500000000000000"), result.getTaxaPeriodoAjustada());
        assertEquals(new BigDecimal("0.3442622950819672"), result.getPercentualSelic());

        assertEquals(new BigDecimal("2926.23"), result.getValorSelic().setScale(2, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("5573.77"), result.getValorPrincipal().setScale(2, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("0"), result.getPercentualJuros());
        assertEquals(new BigDecimal("0"), result.getValorJuros());
    }

    @Test
    void deveRetornarZeroSelicQuandoPeriodoDentroMesCorrente() {
        SelicInput input = new SelicInput(
                LocalDate.of(2026, 2, 1),
                LocalDate.of(2026, 2, 15),
                LocalDate.of(2026, 2, 20),
                new BigDecimal("1000.00"),
                new BigDecimal("15.00")
        );

        SelicResult result = calculator.calculate(input);

        assertEquals(1, result.getMesesTotais());
        assertEquals(0, result.getMesesEfetivos());
        assertEquals(BigDecimal.ZERO.setScale(16), result.getPercentualSelic().setScale(16));
    }
}

package br.com.calculo.strategy;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MonthCountStrategyTest {

    private final MonthCountStrategy strategy = new DistinctTouchedMonthCountStrategy();

    @Test
    void deveContarMesesDistintosTocados() {
        int meses = strategy.countDistinctMonths(
                LocalDate.of(2025, 6, 30),
                LocalDate.of(2026, 2, 1)
        );

        assertEquals(9, meses);
    }

    @Test
    void deveLancarExcecaoQuandoDataFinalAnteriorADataInicial() {
        assertThrows(IllegalArgumentException.class,
                () -> strategy.countDistinctMonths(LocalDate.of(2026, 2, 1), LocalDate.of(2026, 1, 31)));
    }
}

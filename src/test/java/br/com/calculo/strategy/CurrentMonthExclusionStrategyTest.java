package br.com.calculo.strategy;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrentMonthExclusionStrategyTest {

    private final CurrentMonthExclusionStrategy strategy = new OpenCurrentMonthExclusionStrategy();

    @Test
    void deveExcluirMesCorrenteQuandoDataFinalNoMesAtual() {
        int mesesEfetivos = strategy.applyExclusion(
                85,
                LocalDate.of(2026, 2, 4),
                LocalDate.of(2026, 2, 20)
        );

        assertEquals(84, mesesEfetivos);
    }

    @Test
    void naoDeveExcluirQuandoDataFinalAntesDoInicioDoMesAtual() {
        int mesesEfetivos = strategy.applyExclusion(
                10,
                LocalDate.of(2026, 1, 31),
                LocalDate.of(2026, 2, 20)
        );

        assertEquals(10, mesesEfetivos);
    }

    @Test
    void deveGarantirNaoNegativoQuandoPeriodoInteiroNoMesAtual() {
        int mesesEfetivos = strategy.applyExclusion(
                0,
                LocalDate.of(2026, 2, 15),
                LocalDate.of(2026, 2, 20)
        );

        assertEquals(0, mesesEfetivos);
    }
}

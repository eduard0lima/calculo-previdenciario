package br.com.calculo.strategy;

import java.time.LocalDate;

public interface CurrentMonthExclusionStrategy {
    int applyExclusion(int mesesTotais, LocalDate dataFinal, LocalDate dataAcordo);
}

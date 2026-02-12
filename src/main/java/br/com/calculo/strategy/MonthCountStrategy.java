package br.com.calculo.strategy;

import java.time.LocalDate;

public interface MonthCountStrategy {
    int countDistinctMonths(LocalDate dataInicial, LocalDate dataFinal);
}

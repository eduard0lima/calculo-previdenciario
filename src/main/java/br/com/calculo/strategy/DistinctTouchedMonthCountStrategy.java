package br.com.calculo.strategy;

import java.time.LocalDate;
import java.time.YearMonth;

public class DistinctTouchedMonthCountStrategy implements MonthCountStrategy {

    @Override
    public int countDistinctMonths(LocalDate dataInicial, LocalDate dataFinal) {
        if (dataFinal.isBefore(dataInicial)) {
            throw new IllegalArgumentException("dataFinal não pode ser anterior à dataInicial");
        }
        YearMonth inicio = YearMonth.from(dataInicial);
        YearMonth fim = YearMonth.from(dataFinal);
        int diffEmMeses = (fim.getYear() - inicio.getYear()) * 12 + (fim.getMonthValue() - inicio.getMonthValue());
        return diffEmMeses + 1;
    }
}

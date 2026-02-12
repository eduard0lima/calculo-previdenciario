package br.com.calculo.strategy;

import java.time.LocalDate;

public class OpenCurrentMonthExclusionStrategy implements CurrentMonthExclusionStrategy {

    @Override
    public int applyExclusion(int mesesTotais, LocalDate dataFinal, LocalDate dataAcordo) {
        LocalDate inicioMesAtual = dataAcordo.withDayOfMonth(1);
        if (!dataFinal.isBefore(inicioMesAtual)) {
            return Math.max(0, mesesTotais - 1);
        }
        return mesesTotais;
    }
}

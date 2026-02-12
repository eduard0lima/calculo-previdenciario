package br.com.calculo.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public final class SelicInput {

    private final LocalDate dataInicial;
    private final LocalDate dataFinal;
    private final LocalDate dataAcordo;
    private final BigDecimal valorTotal;
    private final BigDecimal selicAnualPercent;

    public SelicInput(LocalDate dataInicial,
                      LocalDate dataFinal,
                      LocalDate dataAcordo,
                      BigDecimal valorTotal,
                      BigDecimal selicAnualPercent) {
        this.dataInicial = Objects.requireNonNull(dataInicial, "dataInicial é obrigatória");
        this.dataFinal = Objects.requireNonNull(dataFinal, "dataFinal é obrigatória");
        this.dataAcordo = Objects.requireNonNull(dataAcordo, "dataAcordo é obrigatória");
        this.valorTotal = Objects.requireNonNull(valorTotal, "valorTotal é obrigatório");
        this.selicAnualPercent = Objects.requireNonNull(selicAnualPercent, "selicAnualPercent é obrigatória");

        if (dataFinal.isBefore(dataInicial)) {
            throw new IllegalArgumentException("dataFinal não pode ser anterior à dataInicial");
        }
        if (valorTotal.signum() < 0) {
            throw new IllegalArgumentException("valorTotal não pode ser negativo");
        }
        if (selicAnualPercent.signum() < 0) {
            throw new IllegalArgumentException("selicAnualPercent não pode ser negativo");
        }
    }

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public LocalDate getDataAcordo() {
        return dataAcordo;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public BigDecimal getSelicAnualPercent() {
        return selicAnualPercent;
    }
}

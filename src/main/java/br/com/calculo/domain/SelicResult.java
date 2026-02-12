package br.com.calculo.domain;

import java.math.BigDecimal;

public final class SelicResult {

    private final int mesesTotais;
    private final int mesesEfetivos;
    private final int mesesAnteriores;
    private final int mesesAtuais;
    private final BigDecimal percentualMesesAnteriores;
    private final BigDecimal percentualMesesAtuais;
    private final BigDecimal valorRefMesesAnteriores;
    private final BigDecimal valorRefMesesAtuais;
    private final BigDecimal taxaPeriodoLinear;
    private final BigDecimal taxaPeriodoAjustada;
    private final BigDecimal percentualSelic;
    private final BigDecimal percentualJuros;
    private final BigDecimal valorSelic;
    private final BigDecimal valorJuros;
    private final BigDecimal valorPrincipal;

    public SelicResult(int mesesTotais,
                       int mesesEfetivos,
                       int mesesAnteriores,
                       int mesesAtuais,
                       BigDecimal percentualMesesAnteriores,
                       BigDecimal percentualMesesAtuais,
                       BigDecimal valorRefMesesAnteriores,
                       BigDecimal valorRefMesesAtuais,
                       BigDecimal taxaPeriodoLinear,
                       BigDecimal taxaPeriodoAjustada,
                       BigDecimal percentualSelic,
                       BigDecimal percentualJuros,
                       BigDecimal valorSelic,
                       BigDecimal valorJuros,
                       BigDecimal valorPrincipal) {
        this.mesesTotais = mesesTotais;
        this.mesesEfetivos = mesesEfetivos;
        this.mesesAnteriores = mesesAnteriores;
        this.mesesAtuais = mesesAtuais;
        this.percentualMesesAnteriores = percentualMesesAnteriores;
        this.percentualMesesAtuais = percentualMesesAtuais;
        this.valorRefMesesAnteriores = valorRefMesesAnteriores;
        this.valorRefMesesAtuais = valorRefMesesAtuais;
        this.taxaPeriodoLinear = taxaPeriodoLinear;
        this.taxaPeriodoAjustada = taxaPeriodoAjustada;
        this.percentualSelic = percentualSelic;
        this.percentualJuros = percentualJuros;
        this.valorSelic = valorSelic;
        this.valorJuros = valorJuros;
        this.valorPrincipal = valorPrincipal;
    }

    public int getMesesTotais() { return mesesTotais; }
    public int getMesesEfetivos() { return mesesEfetivos; }
    public int getMesesAnteriores() { return mesesAnteriores; }
    public int getMesesAtuais() { return mesesAtuais; }
    public BigDecimal getPercentualMesesAnteriores() { return percentualMesesAnteriores; }
    public BigDecimal getPercentualMesesAtuais() { return percentualMesesAtuais; }
    public BigDecimal getValorRefMesesAnteriores() { return valorRefMesesAnteriores; }
    public BigDecimal getValorRefMesesAtuais() { return valorRefMesesAtuais; }
    public BigDecimal getTaxaPeriodoLinear() { return taxaPeriodoLinear; }
    public BigDecimal getTaxaPeriodoAjustada() { return taxaPeriodoAjustada; }
    public BigDecimal getPercentualSelic() { return percentualSelic; }
    public BigDecimal getPercentualJuros() { return percentualJuros; }
    public BigDecimal getValorSelic() { return valorSelic; }
    public BigDecimal getValorJuros() { return valorJuros; }
    public BigDecimal getValorPrincipal() { return valorPrincipal; }
}

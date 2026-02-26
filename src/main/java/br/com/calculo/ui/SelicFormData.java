package br.com.calculo.ui;

public final class SelicFormData {

    private final String dataInicial;
    private final String dataFinal;
    private final String dataAcordo;
    private final String valorTotal;
    private final String selicAnual;

    public SelicFormData(String dataInicial,
                         String dataFinal,
                         String dataAcordo,
                         String valorTotal,
                         String selicAnual) {
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.dataAcordo = dataAcordo;
        this.valorTotal = valorTotal;
        this.selicAnual = selicAnual;
    }

    public String getDataInicial() {
        return dataInicial;
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public String getDataAcordo() {
        return dataAcordo;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public String getSelicAnual() {
        return selicAnual;
    }
}

package br.com.calculo;

import br.com.calculo.domain.SelicInput;
import br.com.calculo.domain.SelicResult;
import br.com.calculo.service.DefaultSelicCalculator;
import br.com.calculo.service.SelicCalculator;
import br.com.calculo.strategy.DistinctTouchedMonthCountStrategy;
import br.com.calculo.strategy.FixedPonderationFactorStrategy;
import br.com.calculo.strategy.OpenCurrentMonthExclusionStrategy;
import br.com.calculo.util.ReportFormatter;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        // Inputs editáveis
        LocalDate dataInicial = LocalDate.of(2019, 2, 1);
        LocalDate dataFinal = LocalDate.of(2026, 2, 4);
        LocalDate dataAcordo = LocalDate.of(2026, 2, 4);
        BigDecimal valorTotal = new BigDecimal("8500.00");
        BigDecimal selicAnualPercent = new BigDecimal("15.00");

        SelicInput input = new SelicInput(dataInicial, dataFinal, dataAcordo, valorTotal, selicAnualPercent);

        SelicCalculator calculator = new DefaultSelicCalculator(
                new DistinctTouchedMonthCountStrategy(),
                new OpenCurrentMonthExclusionStrategy(),
                new FixedPonderationFactorStrategy()
        );

        SelicResult result = calculator.calculate(input);

        System.out.println("=== Relatório Estimativo SELIC Embutida ===");
        System.out.println("Inputs:");
        System.out.println("- Data Inicial (DIB): " + dataInicial);
        System.out.println("- Data Final (DIP): " + dataFinal);
        System.out.println("- Data Acordo: " + dataAcordo);
        System.out.println("- Valor Total: " + ReportFormatter.formatMoney(valorTotal));
        System.out.println("- Selic Anual: " + ReportFormatter.formatPercent(selicAnualPercent.divide(new BigDecimal("100"))));

        System.out.println("\nMeses:");
        System.out.println("- Meses Totais: " + result.getMesesTotais());
        System.out.println("- Meses Efetivos: " + result.getMesesEfetivos());
        System.out.println("- Meses Anteriores: " + result.getMesesAnteriores());
        System.out.println("- Meses Atuais: " + result.getMesesAtuais());

        System.out.println("\nPercentuais por meses:");
        System.out.println("- Percentual Meses Anteriores: " + ReportFormatter.formatPercent(result.getPercentualMesesAnteriores()));
        System.out.println("- Percentual Meses Atuais: " + ReportFormatter.formatPercent(result.getPercentualMesesAtuais()));

        System.out.println("\nTaxas do período:");
        System.out.println("- Taxa Período Linear: " + ReportFormatter.formatPercent(result.getTaxaPeriodoLinear()));
        System.out.println("- Taxa Período Ajustada: " + ReportFormatter.formatPercent(result.getTaxaPeriodoAjustada()));

        System.out.println("\nDecomposição final:");
        System.out.println("- Percentual SELIC: " + ReportFormatter.formatPercent(result.getPercentualSelic()));
        System.out.println("- Valor SELIC: " + ReportFormatter.formatMoney(result.getValorSelic()));
        System.out.println("- Percentual Juros: " + ReportFormatter.formatPercent(result.getPercentualJuros()));
        System.out.println("- Valor Juros: " + ReportFormatter.formatMoney(result.getValorJuros()));
        System.out.println("- Valor Principal: " + ReportFormatter.formatMoney(result.getValorPrincipal()));
    }
}

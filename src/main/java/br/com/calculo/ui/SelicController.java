package br.com.calculo.ui;

import br.com.calculo.domain.SelicInput;
import br.com.calculo.domain.SelicResult;
import br.com.calculo.service.DefaultSelicCalculator;
import br.com.calculo.service.SelicCalculator;
import br.com.calculo.strategy.DistinctTouchedMonthCountStrategy;
import br.com.calculo.strategy.FixedPonderationFactorStrategy;
import br.com.calculo.strategy.OpenCurrentMonthExclusionStrategy;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.math.BigDecimal;
import java.time.LocalDate;

public class SelicController {

    private final JFrame frame;
    private final SelicFormPanel formPanel;
    private final SelicResultPanel resultPanel;
    private final SelicCalculator calculator;

    public SelicController(JFrame frame, SelicFormPanel formPanel, SelicResultPanel resultPanel) {
        this(frame, formPanel, resultPanel, new DefaultSelicCalculator(
                new DistinctTouchedMonthCountStrategy(),
                new OpenCurrentMonthExclusionStrategy(),
                new FixedPonderationFactorStrategy()
        ));
    }

    public SelicController(JFrame frame,
                           SelicFormPanel formPanel,
                           SelicResultPanel resultPanel,
                           SelicCalculator calculator) {
        this.frame = frame;
        this.formPanel = formPanel;
        this.resultPanel = resultPanel;
        this.calculator = calculator;
    }

    public void calculate() {
        try {
            SelicFormData data = formPanel.getFormData();

            LocalDate dataInicial = InputParser.parseDate(data.getDataInicial(), "Data Inicial (DIB)");
            LocalDate dataFinal = InputParser.parseDate(data.getDataFinal(), "Data Final (DIP)");
            LocalDate dataAcordo = InputParser.parseDate(data.getDataAcordo(), "Data do Acordo");
            BigDecimal valorTotal = InputParser.parseMoney(data.getValorTotal(), "Valor Total (R$)");
            BigDecimal selicAnual = InputParser.parsePercent(data.getSelicAnual(), "Selic Anual (%)");

            SelicInput input = new SelicInput(dataInicial, dataFinal, dataAcordo, valorTotal, selicAnual);
            SelicResult result = calculator.calculate(input);
            resultPanel.render(result);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void clear() {
        formPanel.clear();
        resultPanel.clear();
    }

    public void loadExample() {
        formPanel.fillExample();
        resultPanel.clear();
    }
}

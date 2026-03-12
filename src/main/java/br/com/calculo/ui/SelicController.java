package br.com.calculo.ui;

import br.com.calculo.domain.SelicInput;
import br.com.calculo.domain.SelicResult;
import br.com.calculo.integration.BcbSelicService;
import br.com.calculo.service.DefaultSelicCalculator;
import br.com.calculo.service.SelicCalculator;
import br.com.calculo.strategy.DistinctTouchedMonthCountStrategy;
import br.com.calculo.strategy.FixedPonderationFactorStrategy;
import br.com.calculo.strategy.OpenCurrentMonthExclusionStrategy;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class SelicController {

    private static final String STATUS_LOADING = "Consultando SELIC no Banco Central...";
    private static final String STATUS_SUCCESS = "SELIC obtida automaticamente do Banco Central.";
    private static final String STATUS_ERROR = "Não foi possível obter a SELIC automaticamente. Informe manualmente.";

    private final JFrame frame;
    private final SelicFormPanel formPanel;
    private final SelicResultPanel resultPanel;
    private final SelicCalculator calculator;
    private final BcbSelicService bcbSelicService;

    public SelicController(JFrame frame, SelicFormPanel formPanel, SelicResultPanel resultPanel) {
        this(frame, formPanel, resultPanel, new DefaultSelicCalculator(
                        new DistinctTouchedMonthCountStrategy(),
                        new OpenCurrentMonthExclusionStrategy(),
                        new FixedPonderationFactorStrategy()
                ),
                new BcbSelicService());
    }

    public SelicController(JFrame frame,
                           SelicFormPanel formPanel,
                           SelicResultPanel resultPanel,
                           SelicCalculator calculator,
                           BcbSelicService bcbSelicService) {
        this.frame = frame;
        this.formPanel = formPanel;
        this.resultPanel = resultPanel;
        this.calculator = calculator;
        this.bcbSelicService = bcbSelicService;
    }

    public void startFetchSelic() {
        LocalDate dataAcordo;
        try {
            dataAcordo = InputParser.parseDate(formPanel.getFormData().getDataAcordo(), "Data do Acordo");
        } catch (IllegalArgumentException ex) {
            formPanel.setStatus(STATUS_ERROR);
            formPanel.enableManualSelicInput();
            return;
        }

        formPanel.setStatus(STATUS_LOADING);
        formPanel.setSelicLoadingState();

        SwingWorker<Optional<BigDecimal>, Void> worker = new SwingWorker<Optional<BigDecimal>, Void>() {
            @Override
            protected Optional<BigDecimal> doInBackground() {
                return bcbSelicService.fetchSelicPercent(dataAcordo);
            }

            @Override
            protected void done() {
                try {
                    Optional<BigDecimal> result = get();
                    if (result.isPresent()) {
                        formPanel.setSelicAutoValue(result.get());
                        formPanel.setStatus(STATUS_SUCCESS);
                        return;
                    }
                } catch (Exception ex) {
                    // comportamento de erro tratado abaixo
                }

                formPanel.enableManualSelicInput();
                formPanel.setStatus(STATUS_ERROR);
                JOptionPane.showMessageDialog(
                        frame,
                        "Falha ao consultar Banco Central (timeout/erro).",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        };

        worker.execute();
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
        startFetchSelic();
    }

    public void loadExample() {
        formPanel.fillExample();
        resultPanel.clear();
    }
}

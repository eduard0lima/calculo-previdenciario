package br.com.calculo.ui;

import br.com.calculo.domain.SelicResult;
import br.com.calculo.util.ReportFormatter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class SelicResultPanel extends JPanel {

    private final Map<String, JTextField> fields = new LinkedHashMap<>();

    public SelicResultPanel() {
        setBorder(BorderFactory.createTitledBorder("Resultado"));
        setLayout(new GridBagLayout());

        addRow("Meses Totais", 0);
        addRow("Meses Efetivos", 1);
        addRow("Meses Anteriores", 2);
        addRow("Meses Atuais", 3);
        addRow("% Meses Anteriores", 4);
        addRow("% Meses Atuais", 5);
        addRow("Taxa Período Linear", 6);
        addRow("Taxa Período Ajustada", 7);
        addRow("% SELIC", 8);
        addRow("Valor SELIC (R$)", 9);
        addRow("% Juros", 10);
        addRow("Valor Juros (R$)", 11);
        addRow("Valor Principal (R$)", 12);
    }

    public void render(SelicResult result) {
        setText("Meses Totais", Integer.toString(result.getMesesTotais()));
        setText("Meses Efetivos", Integer.toString(result.getMesesEfetivos()));
        setText("Meses Anteriores", Integer.toString(result.getMesesAnteriores()));
        setText("Meses Atuais", Integer.toString(result.getMesesAtuais()));
        setText("% Meses Anteriores", ReportFormatter.formatPercent(result.getPercentualMesesAnteriores()));
        setText("% Meses Atuais", ReportFormatter.formatPercent(result.getPercentualMesesAtuais()));
        setText("Taxa Período Linear", ReportFormatter.formatPercent(result.getTaxaPeriodoLinear()));
        setText("Taxa Período Ajustada", ReportFormatter.formatPercent(result.getTaxaPeriodoAjustada()));
        setText("% SELIC", ReportFormatter.formatPercent(result.getPercentualSelic()));
        setText("Valor SELIC (R$)", ReportFormatter.formatMoney(result.getValorSelic()));
        setText("% Juros", ReportFormatter.formatPercent(result.getPercentualJuros()));
        setText("Valor Juros (R$)", ReportFormatter.formatMoney(result.getValorJuros()));
        setText("Valor Principal (R$)", ReportFormatter.formatMoney(result.getValorPrincipal()));
    }

    public void clear() {
        for (JTextField field : fields.values()) {
            field.setText("");
        }
    }

    private void addRow(String label, int row) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = row;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(4, 8, 4, 8);

        add(new JLabel(label + ":"), c);

        JTextField valueField = new JTextField(20);
        valueField.setEditable(false);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = row;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(4, 8, 4, 8);

        add(valueField, c);
        fields.put(label, valueField);
    }

    private void setText(String key, String value) {
        JTextField field = fields.get(key);
        if (field != null) {
            field.setText(value);
        }
    }
}

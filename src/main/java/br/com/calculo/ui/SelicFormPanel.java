package br.com.calculo.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;

public class SelicFormPanel extends JPanel {

    private final JTextField dataInicialField;
    private final JTextField dataFinalField;
    private final JTextField dataAcordoField;
    private final JTextField valorTotalField;
    private final JTextField selicAnualField;

    private final JButton calcularButton;
    private final JButton limparButton;
    private final JButton exemploButton;

    public SelicFormPanel() {
        setBorder(BorderFactory.createTitledBorder("Parâmetros"));
        setLayout(new GridBagLayout());

        dataInicialField = createDateField();
        dataFinalField = createDateField();
        dataAcordoField = createDateField();
        valorTotalField = new JTextField(16);
        selicAnualField = new JTextField(16);

        calcularButton = new JButton("Calcular");
        limparButton = new JButton("Limpar");
        exemploButton = new JButton("Carregar Exemplo");

        addLabeledField("Data Inicial (DIB)", dataInicialField, "Formato: dd/MM/yyyy", 0);
        addLabeledField("Data Final (DIP)", dataFinalField, "Formato: dd/MM/yyyy", 1);
        addLabeledField("Data do Acordo", dataAcordoField, "Formato: dd/MM/yyyy", 2);
        addLabeledField("Valor Total (R$)", valorTotalField, "Ex.: 8.500,00", 3);
        addLabeledField("Selic Anual (%)", selicAnualField, "Informe em percentual. Ex.: 15,00", 4);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(8, 8, 8, 8);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(calcularButton);
        buttonPanel.add(limparButton);
        buttonPanel.add(exemploButton);
        add(buttonPanel, c);
    }

    public SelicFormData getFormData() {
        return new SelicFormData(
                dataInicialField.getText(),
                dataFinalField.getText(),
                dataAcordoField.getText(),
                valorTotalField.getText(),
                selicAnualField.getText()
        );
    }

    public void clear() {
        dataInicialField.setText("");
        dataFinalField.setText("");
        dataAcordoField.setText("");
        valorTotalField.setText("");
        selicAnualField.setText("");
    }

    public void fillExample() {
        dataInicialField.setText("01/02/2019");
        dataFinalField.setText("04/02/2026");
        dataAcordoField.setText("04/02/2026");
        valorTotalField.setText("8.500,00");
        selicAnualField.setText("15,00");
    }

    public JButton getCalcularButton() {
        return calcularButton;
    }

    public JButton getLimparButton() {
        return limparButton;
    }

    public JButton getExemploButton() {
        return exemploButton;
    }

    private void addLabeledField(String labelText, JTextField field, String hint, int row) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = row;
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(4, 8, 4, 8);
        add(new JLabel(labelText + ":"), c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = row;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.insets = new Insets(4, 0, 4, 8);
        add(field, c);

        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = row;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(4, 0, 4, 8);
        add(new JLabel(hint), c);
    }

    private JTextField createDateField() {
        try {
            MaskFormatter maskFormatter = new MaskFormatter("##/##/####");
            maskFormatter.setPlaceholderCharacter('_');
            return new JFormattedTextField(maskFormatter);
        } catch (ParseException ex) {
            return new JTextField(16);
        }
    }
}

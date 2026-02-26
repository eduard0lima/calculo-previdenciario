package br.com.calculo.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class SelicAppFrame extends JFrame {

    public SelicAppFrame() {
        super("Cálculo Estimativo de SELIC Embutida");

        SelicFormPanel formPanel = new SelicFormPanel();
        SelicResultPanel resultPanel = new SelicResultPanel();
        SelicController controller = new SelicController(this, formPanel, resultPanel);

        formPanel.getCalcularButton().addActionListener(e -> controller.calculate());
        formPanel.getLimparButton().addActionListener(e -> controller.clear());
        formPanel.getExemploButton().addActionListener(e -> controller.loadExample());

        JPanel root = new JPanel(new BorderLayout(8, 8));
        root.add(formPanel, BorderLayout.NORTH);
        root.add(resultPanel, BorderLayout.CENTER);
        root.add(new FooterBannerPanel(), BorderLayout.SOUTH);

        setContentPane(root);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }
}

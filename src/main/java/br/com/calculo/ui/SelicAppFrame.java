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
        formPanel.getBuscarSelicButton().addActionListener(e -> controller.startFetchSelic());

        JPanel root = new JPanel(new BorderLayout(8, 8));
        JPanel headerPanel = new JPanel(new BorderLayout(8, 8));
        headerPanel.add(new FooterBannerPanel(), BorderLayout.NORTH);
        headerPanel.add(formPanel, BorderLayout.CENTER);

        root.add(headerPanel, BorderLayout.NORTH);
        root.add(resultPanel, BorderLayout.CENTER);

        setContentPane(root);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        controller.startFetchSelic();
    }
}

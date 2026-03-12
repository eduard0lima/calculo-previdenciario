package br.com.calculo.ui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.URL;

public class FooterBannerPanel extends JPanel {

    private static final int BANNER_WIDTH = 800;
    private static final int BANNER_HEIGHT = 155;

    public FooterBannerPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(BANNER_WIDTH, BANNER_HEIGHT));

        URL imageUrl = FooterBannerPanel.class.getResource("/images/rodape-calculadora.png");
        if (imageUrl != null) {
            add(new JLabel(new ImageIcon(imageUrl)), BorderLayout.CENTER);
        } else {
            add(new JLabel("Banner não encontrado em /images/rodape-calculadora.png"), BorderLayout.CENTER);
        }
    }
}
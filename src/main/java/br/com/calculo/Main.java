package br.com.calculo;

import br.com.calculo.ui.SelicAppFrame;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SelicAppFrame frame = new SelicAppFrame();
            frame.setVisible(true);
        });
    }
}

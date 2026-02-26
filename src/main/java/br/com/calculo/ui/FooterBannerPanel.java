package br.com.calculo.ui;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class FooterBannerPanel extends JPanel {

    private static final int BANNER_WIDTH = 800;
    private static final int BANNER_HEIGHT = 155;

    public FooterBannerPanel() {
        setPreferredSize(new Dimension(BANNER_WIDTH, BANNER_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();

        g2.setColor(new Color(17, 102, 170));
        g2.fillRect(0, 0, width, 103);

        g2.setColor(new Color(232, 232, 232));
        g2.fillRect(0, 103, width, BANNER_HEIGHT - 103);

        drawLeftSeal(g2);
        drawRightLogos(g2);

        g2.dispose();
    }

    private void drawLeftSeal(Graphics2D g2) {
        int cx = 80;
        int cy = 78;

        Color[] colors = {
                new Color(237, 28, 36),
                new Color(255, 127, 39),
                new Color(255, 242, 0),
                new Color(34, 177, 76),
                new Color(0, 162, 232),
                new Color(63, 72, 204),
                new Color(163, 73, 164),
                new Color(255, 174, 201)
        };

        for (int i = 0; i < colors.length; i++) {
            double angle = 2 * Math.PI * i / colors.length;
            int x = (int) (cx + 56 * Math.cos(angle));
            int y = (int) (cy + 42 * Math.sin(angle));
            g2.setColor(colors[i]);
            g2.fillOval(x - 5, y - 5, 10, 10);
        }

        g2.setColor(Color.WHITE);
        g2.fillOval(30, 28, 100, 100);
        g2.setColor(new Color(205, 205, 205));
        g2.setStroke(new BasicStroke(2f));
        g2.drawOval(30, 28, 100, 100);

        g2.setColor(new Color(0, 122, 191));
        g2.setFont(getFont().deriveFont(Font.BOLD, 23f));
        g2.drawString("JUSTIÇA", 41, 72);
        g2.setFont(getFont().deriveFont(Font.BOLD, 17f));
        g2.drawString("CENTRADA", 43, 92);
        g2.setFont(getFont().deriveFont(Font.PLAIN, 13f));
        g2.drawString("NAS PESSOAS", 44, 110);
    }

    private void drawRightLogos(Graphics2D g2) {
        int x = getWidth() - 330;

        g2.setColor(new Color(231, 241, 251));
        g2.fillRect(x, 18, 66, 66);
        g2.setColor(Color.WHITE);
        g2.drawRect(x, 18, 66, 66);

        g2.setColor(new Color(0, 88, 150));
        g2.setFont(getFont().deriveFont(Font.PLAIN, 21f));
        g2.drawString("P N", x + 8, 53);
        g2.drawString("U D", x + 8, 76);

        drawStarAndText(g2, x + 88, "JUSTIÇA FEDERAL", "Tribunal Regional Federal da 5ª Região");
        drawStarAndText(g2, x + 220, "JUSTIÇA FEDERAL", "Seção Judiciária do Ceará");
    }

    private void drawStarAndText(Graphics2D g2, int x, String title, String subtitle) {
        int[] xs = {x + 20, x + 24, x + 35, x + 24, x + 20, x + 16, x + 5, x + 16};
        int[] ys = {24, 34, 38, 42, 52, 42, 38, 34};

        g2.setColor(Color.WHITE);
        g2.fillPolygon(xs, ys, xs.length);

        g2.setFont(getFont().deriveFont(Font.BOLD, 21f));
        g2.drawString("✦", x + 10, 48);

        g2.setFont(getFont().deriveFont(Font.BOLD, 20f));
        g2.drawString(title, x + 42, 56);

        g2.setFont(getFont().deriveFont(Font.PLAIN, 11f));
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(subtitle, x + 42, 72 + fm.getAscent() / 4);
    }
}

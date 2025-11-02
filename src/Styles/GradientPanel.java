package Styles;

import java.awt.*;
import javax.swing.JPanel;

public class GradientPanel extends JPanel {
    private Color topColor = Color.decode("#F7F8FA");
    private Color bottomColor = Color.decode("#8C9199");
    private int cornerRadius = 0;

    public GradientPanel() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(0, 0, topColor, 0, height, bottomColor);
        g2d.setPaint(gp);
        g2d.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);
        g2d.dispose();

        super.paintComponent(g);
    }
}

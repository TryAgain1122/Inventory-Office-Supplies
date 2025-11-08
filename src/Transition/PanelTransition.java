package Transition;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelTransition {

    private Timer timer;
    private float alpha = 0f;
    private JPanel currentPanel;
    private JPanel nextPanel;
    private int delay = 15; // speed of fade
    private boolean fadeIn = true;

    public void switchPanel(JPanel container, JPanel current, JPanel next) {
        this.currentPanel = current;
        this.nextPanel = next;
        this.alpha = 0f;
        this.fadeIn = true;

        nextPanel.setVisible(true);
        nextPanel.setOpaque(false);
        container.add(nextPanel);
        container.revalidate();
        container.repaint();

        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fadeIn) {
                    alpha += 0.05f;
                    if (alpha >= 1f) {
                        alpha = 1f;
                        fadeIn = false;
                        container.remove(currentPanel);
                        currentPanel.setVisible(false);
                        currentPanel = null;
                        timer.stop();
                    }
                    nextPanel.repaint();
                }
            }
        });
        timer.start();
    }

    // Optional: override paint for fade effect
    public static class FadePanel extends JPanel {
        private float alpha = 1f;

        public void setAlpha(float alpha) {
            this.alpha = alpha;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            super.paintComponent(g2d);
            g2d.dispose();
        }
    }
}

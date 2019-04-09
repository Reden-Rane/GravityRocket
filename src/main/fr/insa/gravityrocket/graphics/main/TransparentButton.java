package fr.insa.gravityrocket.graphics.main;

import fr.insa.gravityrocket.graphics.renderer.RenderManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TransparentButton extends JButton
{

    private boolean hovered = false;

    public TransparentButton(String s) {
        super(s);
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setForeground(Color.WHITE);
        this.setFont(RenderManager.BEBAS_NEUE_FONT.deriveFont(35.0f));

        this.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                super.mouseEntered(mouseEvent);
                TransparentButton.this.hovered = true;
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                super.mouseExited(mouseEvent);
                TransparentButton.this.hovered = false;
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Color color1 = new Color(63, 138, 252, 126);
        Color color2 = new Color(146, 68, 191, 126);

        if (this.hovered) {
            color1 = color1.brighter();
            color2 = color2.brighter();
        }

        GradientPaint gradientPaint = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
        g2d.setPaint(gradientPaint);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(new Color(255, 255, 255, 155));
        g2d.drawRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

}

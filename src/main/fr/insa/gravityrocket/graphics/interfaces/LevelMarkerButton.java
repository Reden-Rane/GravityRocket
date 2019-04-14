package fr.insa.gravityrocket.graphics.interfaces;

import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.level.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LevelMarkerButton extends JButton
{

    private final Image starImage;
    private final Image starImageHovered;

    private final int   levelNumber;
    private final Level level;
    private final int   relX;
    private final int   relY;

    private boolean hovered = false;

    public LevelMarkerButton(int levelNumber, Level level, int relX, int relY) {
        this.setSize(50, 50);
        this.starImage = RenderManager.loadImage("/textures/star/star_0.png", 50, 48);
        this.starImageHovered = RenderManager.loadImage("/textures/star/star_1.png", 50, 48);
        this.levelNumber = levelNumber;
        this.level = level;
        this.relX = relX;
        this.relY = relY;
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);

        this.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                super.mousePressed(mouseEvent);
                LevelMarkerButton.this.hovered = false;
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                super.mouseEntered(mouseEvent);
                LevelMarkerButton.this.hovered = true;
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                super.mouseExited(mouseEvent);
                LevelMarkerButton.this.hovered = false;
            }
        });
    }

    public Level getLevel() {
        return level;
    }

    public int getRelativeX() {
        return relX;
    }

    public int getRelativeY() {
        return relY;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if (this.hovered) {
            graphics.drawImage(this.starImageHovered, 0, 0, this.starImageHovered.getWidth(null), this.starImageHovered.getHeight(null), null);
        } else {
            graphics.drawImage(this.starImage, 0, 0, this.starImage.getWidth(null), this.starImage.getHeight(null), null);
        }

        Graphics2D g2d = (Graphics2D) graphics;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setFont(RenderManager.BEBAS_NEUE_FONT.deriveFont(22.0f));
        g2d.setColor(hovered ? Color.WHITE : Color.BLACK);
        g2d.drawString(Integer.toString(this.levelNumber), this.starImage.getWidth(null) / 2 - 5, this.starImage.getHeight(null) / 2 + 9);
    }

}

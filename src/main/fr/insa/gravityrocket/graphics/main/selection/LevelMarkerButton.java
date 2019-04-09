package fr.insa.gravityrocket.graphics.main.selection;

import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.level.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LevelMarkerButton extends JButton
{

    private final Image starImage;

    private final Level level;

    private boolean hovered = false;

    public LevelMarkerButton(Level level) {
        this.setSize(50, 50);
        this.starImage = RenderManager.loadImage("/textures/star/star.png", 50, 48);
        this.level = level;
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);

        this.addMouseListener(new MouseAdapter()
        {
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

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        //TODO Changer l'image si bouton survolé
        graphics.drawImage(this.starImage, 0, 0, this.starImage.getWidth(null), this.starImage.getHeight(null), null);
    }

}

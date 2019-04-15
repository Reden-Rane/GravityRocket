package fr.insa.gravityrocket.graphics.renderer.entity;

import fr.insa.gravityrocket.graphics.renderer.IRenderer;
import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.entity.alien.Alien;

import java.awt.*;

public class AlienRenderer implements IRenderer<Alien>
{

    private final Image alienImage;

    public AlienRenderer() {
        this.alienImage = RenderManager.loadImage("/textures/entity/alien.png", 50, 50);
    }

    @Override
    public void render(Alien alien, Graphics2D g2d) {
        g2d.drawImage(alienImage, 0, 0, (int) alien.getWidth(), (int) alien.getHeight(), null);
    }

}

package fr.insa.gravityrocket.graphics.renderer.entity;

import fr.insa.gravityrocket.graphics.renderer.IRenderer;
import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.entity.particle.Laser;

import java.awt.*;

public class LaserRenderer implements IRenderer<Laser>
{

    private final Image laserImage;

    public LaserRenderer() {
        this.laserImage = RenderManager.loadImage("/textures/particle/laser.png", 17, 33);
    }

    @Override
    public void render(Laser laser, Graphics2D g2d) {
        g2d.drawImage(laserImage, 0, 0, (int) laser.getWidth(), (int) laser.getHeight(), null);
    }

}

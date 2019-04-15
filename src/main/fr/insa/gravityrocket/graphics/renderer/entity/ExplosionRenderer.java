package fr.insa.gravityrocket.graphics.renderer.entity;

import fr.insa.gravityrocket.graphics.renderer.IRenderer;
import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.entity.particle.Explosion;

import java.awt.*;

public class ExplosionRenderer implements IRenderer<Explosion>
{

    private final Image explosionImage;

    public ExplosionRenderer() {
        this.explosionImage = RenderManager.loadImage("/textures/particle/explosion.png", 100, 100);
    }

    @Override
    public void render(Explosion explosion, Graphics2D g2d) {

        double sizeFactor      = (Math.sin(explosion.getAge() * Math.PI / explosion.getLongevity()) + 1) / 2;
        int    explosionWidth  = (int) (this.explosionImage.getWidth(null) * sizeFactor);
        int    explosionHeight = (int) (this.explosionImage.getHeight(null) * sizeFactor);
        int    renderX         = -explosionWidth / 2;
        int    renderY         = -explosionHeight / 2;
        g2d.drawImage(this.explosionImage, renderX, renderY, explosionWidth, explosionHeight, null);
    }

}

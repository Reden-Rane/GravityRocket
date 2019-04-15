package fr.insa.gravityrocket.graphics.renderer.entity;

import fr.insa.gravityrocket.graphics.renderer.IRenderer;
import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.entity.Asteroid;

import java.awt.*;

public class AsteroidRenderer implements IRenderer<Asteroid>
{

    private final Image asteroid0;
    private final Image asteroid1;
    private final Image asteroid2;

    public AsteroidRenderer() {
        this.asteroid0 = RenderManager.loadImage("/textures/entity/asteroid/asteroid_0.png", 21, 22);
        this.asteroid1 = RenderManager.loadImage("/textures/entity/asteroid/asteroid_1.png", 63, 60);
        this.asteroid2 = RenderManager.loadImage("/textures/entity/asteroid/asteroid_2.png", 75, 50);
    }

    @Override
    public void render(Asteroid asteroid, Graphics2D g2d) {

        Image asteroidImage;

        switch (asteroid.getVariant()) {
            case ASTEROID_1:
                asteroidImage = asteroid1;
                break;
            case ASTEROID_2:
                asteroidImage = asteroid2;
                break;
            default:
                asteroidImage = asteroid0;
                break;
        }

        g2d.drawImage(asteroidImage, 0, 0, (int) asteroid.getWidth(), (int) asteroid.getHeight(), null);
    }

}

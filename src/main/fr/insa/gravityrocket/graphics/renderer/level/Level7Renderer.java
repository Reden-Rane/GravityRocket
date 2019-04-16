package fr.insa.gravityrocket.graphics.renderer.level;

import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.level.Level7;

import java.awt.*;

public class Level7Renderer extends ReachingZoneLevelRenderer<Level7>
{

    private final Image candyImage;

    public Level7Renderer() {
        this.candyImage = RenderManager.loadImage("/textures/constellation/candy.png", 250, 143);
    }

    @Override
    protected void renderZone(Level7 level, Graphics2D g2d) {
        g2d.drawImage(candyImage, 900, 900, candyImage.getWidth(null), candyImage.getHeight(null), null);
    }

}

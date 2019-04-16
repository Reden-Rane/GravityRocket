package fr.insa.gravityrocket.graphics.renderer.level;

import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.level.Level8;

import java.awt.*;

public class Level8Renderer extends ReachingZoneLevelRenderer<Level8>
{

    private final Image rhinocerosImage;

    public Level8Renderer() {
        this.rhinocerosImage = RenderManager.loadImage("/textures/constellation/rhinoceros.png", 1018 / 2, 488 / 2);
    }

    @Override
    protected void renderZone(Level8 level, Graphics2D g2d) {
        g2d.drawImage(rhinocerosImage, 1100, 600, rhinocerosImage.getWidth(null), rhinocerosImage.getHeight(null), null);
    }

}

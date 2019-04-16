package fr.insa.gravityrocket.graphics.renderer.level;

import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.level.Level5;

import java.awt.*;

public class Level5Renderer extends ReachingZoneLevelRenderer<Level5>
{

    private final Image aquariusImage;

    public Level5Renderer() {
        this.aquariusImage = RenderManager.loadImage("/textures/constellation/aquarius.png", 421, 623);
    }

    @Override
    protected void renderZone(Level5 level, Graphics2D g2d) {
        g2d.drawImage(aquariusImage, -900, -150, aquariusImage.getWidth(null), aquariusImage.getHeight(null), null);
    }

}

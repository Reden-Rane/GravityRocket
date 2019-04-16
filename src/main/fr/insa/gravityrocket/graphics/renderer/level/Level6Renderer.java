package fr.insa.gravityrocket.graphics.renderer.level;

import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.level.Level6;

import java.awt.*;

public class Level6Renderer extends ReachingZoneLevelRenderer<Level6>
{

    private final Image taurusImage;

    public Level6Renderer() {
        this.taurusImage = RenderManager.loadImage("/textures/constellation/taurus.png", 478, 543);
    }

    @Override
    protected void renderZone(Level6 level, Graphics2D g2d) {
        g2d.drawImage(taurusImage, 900, 900, taurusImage.getWidth(null), taurusImage.getHeight(null), null);
    }

}

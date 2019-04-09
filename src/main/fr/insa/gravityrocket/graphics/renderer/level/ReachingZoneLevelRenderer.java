package fr.insa.gravityrocket.graphics.renderer.level;

import fr.insa.gravityrocket.logic.level.ReachingZoneLevel;

import java.awt.*;

public class ReachingZoneLevelRenderer extends LevelRenderer<ReachingZoneLevel>
{

    @Override
    protected void renderLevel(ReachingZoneLevel level, Graphics2D g2d) {
        renderZone(level, g2d);
        super.renderLevel(level, g2d);
    }

    private void renderZone(ReachingZoneLevel level, Graphics2D g2d) {
        g2d.setColor(new Color(255, 0, 0, 120));
        g2d.fill(level.getZone());
    }

}

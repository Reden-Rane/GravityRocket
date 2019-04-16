package fr.insa.gravityrocket.graphics.renderer.level;

import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.level.ReachingZoneLevel;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class ReachingZoneLevelRenderer<L extends ReachingZoneLevel> extends LevelRenderer<L>
{

    @Override
    protected void renderLevel(L level, Graphics2D g2d) {
        renderZone(level, g2d);
        super.renderLevel(level, g2d);
    }

    @Override
    protected void renderObjective(L level, Graphics2D g2d) {

        String objectiveText = "Objectif: Rejoindre la " + level.getZoneName();
        Font   font          = RenderManager.BEBAS_NEUE_FONT.deriveFont(25.0f);

        Rectangle2D textBounds = font.getStringBounds(objectiveText, g2d.getFontRenderContext());
        int         renderX    = (int) ((getRenderManager().getScreenWidth() - textBounds.getWidth()) / 2);
        int         renderY    = 50;
        g2d.setFont(font);
        g2d.setColor(new Color(255, 185, 124));
        g2d.drawString(objectiveText, renderX, renderY);
    }

    protected void renderZone(L level, Graphics2D g2d) {
        g2d.setColor(new Color(255, 0, 0, 120));
        g2d.fill(level.getZone());
    }

}

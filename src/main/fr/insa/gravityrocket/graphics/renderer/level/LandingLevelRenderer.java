package fr.insa.gravityrocket.graphics.renderer.level;

import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.level.LandingLevel;

import java.awt.*;

public class LandingLevelRenderer extends LevelRenderer<LandingLevel>
{

    @Override
    protected void renderLevelHUD(LandingLevel level, Graphics2D g2d) {
        super.renderLevelHUD(level, g2d);
        renderAngleToTargetHUD(level, g2d);
    }

    @Override
    protected void renderSpeedHUD(LandingLevel level, Graphics g2d) {
        double speedMagnitude = level.getRocket().getSpeedMagnitude();
        int    renderX        = 40;
        int    renderY        = getRenderManager().getScreenHeight() - 80;
        g2d.setFont(RenderManager.BEBAS_NEUE_FONT.deriveFont(25.0f));
        g2d.setColor(speedMagnitude > level.getMaximumSpeed() ? Color.RED : Color.WHITE);
        g2d.drawString(String.format("Vitesse: %.2fm/s", speedMagnitude), renderX, renderY);
    }

    private void renderAngleToTargetHUD(LandingLevel level, Graphics2D g2d) {
        double angle   = level.getRocket().getAngleWithPlanet(level.getTargetedPlanet());
        int    renderX = 40;
        int    renderY = getRenderManager().getScreenHeight() - 110;
        g2d.setFont(RenderManager.BEBAS_NEUE_FONT.deriveFont(25.0f));
        g2d.setColor(angle > level.getMaximumAngle() ? Color.RED : Color.WHITE);
        g2d.drawString(String.format("Angle fusée/%s: %.1f°", level.getTargetedPlanet().getName(), Math.toDegrees(angle)), renderX, renderY);
    }

}

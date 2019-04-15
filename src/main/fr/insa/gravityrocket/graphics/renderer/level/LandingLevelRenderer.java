package fr.insa.gravityrocket.graphics.renderer.level;

import fr.insa.gravityrocket.graphics.renderer.RenderManager;
import fr.insa.gravityrocket.logic.EnumLevelState;
import fr.insa.gravityrocket.logic.entity.Entity;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.level.LandingLevel;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class LandingLevelRenderer extends LevelRenderer<LandingLevel>
{

    @Override
    protected void renderEntity(LandingLevel level, Entity entity, Graphics2D g2d) {

        if (entity == level.getTargetedPlanet() && level.getLevelState() == EnumLevelState.RUNNING) {
            renderTargetHalo(level, g2d);
        }

        super.renderEntity(level, entity, g2d);
    }

    private void renderTargetHalo(LandingLevel level, Graphics2D g2d) {
        Planet planet = level.getTargetedPlanet();

        int haloSize = (int) (level.getHaloSize() + level.getHaloSize() * 2 / 3.0 * (Math.cos(System.currentTimeMillis() / 300.0) + 1) / 2);
        int x        = (int) (planet.getXPos() - (planet.getRadius() + haloSize));
        int y        = (int) (planet.getYPos() - (planet.getRadius() + haloSize));
        int size     = (int) ((planet.getRadius() + haloSize) * 2);

        float[]             dist   = {0.5f, 1.0f};
        Color[]             colors = {new Color(137, 255, 165, 255), new Color(137, 255, 165, 0)};
        RadialGradientPaint p      = new RadialGradientPaint((int) planet.getXPos(), (int) planet.getYPos(), (float) (planet.getRadius() + haloSize), dist, colors);

        g2d.setPaint(p);
        g2d.fillOval(x, y, size, size);
    }

    @Override
    protected void renderLevelHUD(LandingLevel level, Graphics2D g2d) {
        super.renderLevelHUD(level, g2d);
        renderAngleToTargetHUD(level, g2d);

        if (!level.isRocketOutOfLevelBounds()) {
            renderObjective(level, g2d);
        }
    }

    private void renderObjective(LandingLevel level, Graphics2D g2d) {

        String objectiveText = "Objectif: Atterir sur " + level.getTargetedPlanet().getName();
        Font   font          = RenderManager.BEBAS_NEUE_FONT.deriveFont(25.0f);

        Rectangle2D textBounds = font.getStringBounds(objectiveText, g2d.getFontRenderContext());
        int         renderX    = (int) ((getRenderManager().getScreenWidth() - textBounds.getWidth()) / 2);
        int         renderY    = 50;
        g2d.setFont(font);
        g2d.setColor(new Color(255, 185, 124));
        g2d.drawString(objectiveText, renderX, renderY);
    }

    @Override
    protected void renderSpeedHUD(LandingLevel level, Graphics2D g2d) {
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

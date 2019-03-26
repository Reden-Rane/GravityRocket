package fr.insa.gravityrocket.graphics.renderer;

import fr.insa.gravityrocket.graphics.FontHelper;
import fr.insa.gravityrocket.graphics.ImageHelper;
import fr.insa.gravityrocket.logic.EnumGameOverType;
import fr.insa.gravityrocket.logic.Level;
import fr.insa.gravityrocket.logic.entity.Entity;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import static fr.insa.gravityrocket.graphics.GameView.WINDOW_HEIGHT;
import static fr.insa.gravityrocket.graphics.GameView.WINDOW_WIDTH;

public class LevelRenderer implements IRenderer<Level>
{

    private static final boolean DEBUG_MODE  = true;
    private static final Color   SPACE_COLOR = new Color(14, 21, 33);
    private final        Image   dangerImage;

    public LevelRenderer() {
        this.dangerImage = ImageHelper.loadImage("/textures/danger.png", 286, 40);
    }

    @Override
    public void render(Level level, Graphics2D g2d) {

        renderBackground(level, g2d);

        AffineTransform prevTransform = g2d.getTransform();
        applyCameraTransform(level, g2d);

        for (Entity entity : level.getEntitySet()) {
            renderEntity(entity, g2d);

            if (DEBUG_MODE) {
                renderEntityCollisionBox(entity, g2d);
            }
        }
        g2d.setTransform(prevTransform);

        renderHUD(level, g2d);
    }

    private void renderBackground(Level level, Graphics2D g2d) {
        g2d.setColor(SPACE_COLOR);
        g2d.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        //TODO Faire le rendu des étoiles (+ planètes lointaines en opacité réduite ?)
    }

    private void applyCameraTransform(Level level, Graphics2D g2d) {

        AffineTransform transform = new AffineTransform();

        double minX = level.getRocket().getXPos() - Math.max(level.getRocket().getWidth(), level.getRocket().getHeight());
        double minY = level.getRocket().getYPos() - Math.max(level.getRocket().getWidth(), level.getRocket().getHeight());
        double maxX = level.getRocket().getXPos() + Math.max(level.getRocket().getWidth(), level.getRocket().getHeight());
        double maxY = level.getRocket().getYPos() + Math.max(level.getRocket().getWidth(), level.getRocket().getHeight());

        double offsetX = 0;
        double offsetY = 0;

        if (minX < level.getPreferredView().x) {
            offsetX = level.getPreferredView().x - minX;
        } else if (maxX > level.getPreferredView().x + level.getPreferredView().width) {
            offsetX = maxX - (level.getPreferredView().x + level.getPreferredView().width);
        }

        if (minY < level.getPreferredView().y) {
            offsetY = level.getPreferredView().y - minY;
        } else if (maxY > level.getPreferredView().y + level.getPreferredView().height) {
            offsetY = maxY - (level.getPreferredView().y + level.getPreferredView().height);
        }

        double scaleX = WINDOW_WIDTH / (level.getPreferredView().width + Math.max(offsetX, offsetY) * 2);
        double scaleY = WINDOW_HEIGHT / (level.getPreferredView().height + Math.max(offsetX, offsetY) * 2);
        double scale  = Math.min(scaleX, scaleY);

        double translateX = -level.getPreferredView().x * scale + (WINDOW_WIDTH - level.getPreferredView().width * scale) / 2;
        double translateY = -level.getPreferredView().y * scale + (WINDOW_HEIGHT - level.getPreferredView().height * scale) / 2;

        transform.translate(translateX, translateY);
        transform.scale(scale, scale);
        g2d.transform(transform);
    }

    private void renderEntity(Entity entity, Graphics2D g2d) {
        int renderX = (int) (entity.getXPos() - entity.getWidth() / 2);
        int renderY = (int) (entity.getYPos() - entity.getHeight() / 2);
        getRenderManager().render(entity, renderX, renderY, entity.getWidth() / 2, entity.getHeight() / 2, entity.getRotation(), g2d);
    }

    private void renderEntityCollisionBox(Entity entity, Graphics2D g2d) {
        getRenderManager().render(entity.getCollisionBox(), g2d);
    }

    private void renderHUD(Level level, Graphics2D g2d) {

        if (level.isGameOver()) {

            float  fontHeight = 150;
            Font   font       = FontHelper.BEBAS_NEUE_FONT.deriveFont(fontHeight).deriveFont(Font.BOLD);
            double scale      = 0.9 + 0.1 * (Math.cos(System.currentTimeMillis() / 1000.0) + 1) / 2;

            Color textStartColor;
            Color textEndColor;

            Color shadowColor  = shadowColor = new Color(42, 42, 42);
            Color outlineColor = new Color(75, 75, 75);

            String text;
            String subText;

            if (level.getGameOverType() == EnumGameOverType.CRASH) {
                text = "GAME OVER";
                subText = "Vous vous êtes écrasé";
                textStartColor = Color.RED;
                textEndColor = Color.DARK_GRAY;
            } else if (level.getGameOverType() == EnumGameOverType.WRONG_PLANET) {
                text = "GAME OVER";
                subText = "Vous n'avez pas atterri sur la bonne planète";
                textStartColor = Color.RED;
                textEndColor = Color.DARK_GRAY;
            } else if (level.getGameOverType() == EnumGameOverType.OUT_OF_LEVEL) {
                text = "GAME OVER";
                subText = "Vous vous êtes trop éloigné de l'objectif";
                textStartColor = Color.RED;
                textEndColor = Color.DARK_GRAY;
            } else {
                text = "BRAVO !";
                subText = "Vous avez atteint l'objectif";
                textStartColor = Color.GREEN;
                textEndColor = new Color(0, 102, 0);
                shadowColor = new Color(42, 42, 42);
                outlineColor = new Color(75, 75, 75);
            }

            Rectangle2D textBounds    = font.getStringBounds(text, g2d.getFontRenderContext());
            Rectangle2D subTextBounds = font.getStringBounds(subText, g2d.getFontRenderContext());
            int         textX         = (int) ((WINDOW_WIDTH - textBounds.getWidth()) / 2);
            int         textY         = WINDOW_HEIGHT / 2;
            int         subTextX      = (int) ((WINDOW_WIDTH - subTextBounds.getWidth()) / 2);
            int         subTextY      = (int) (WINDOW_HEIGHT / 2 + fontHeight);

            GlyphVector glyphVector = font.createGlyphVector(g2d.getFontRenderContext(), text);
            Shape       textShape   = glyphVector.getOutline();

            AffineTransform prevTransform = g2d.getTransform();
            Stroke          prevStroke    = g2d.getStroke();

            g2d.translate(textX + textBounds.getWidth() / 2, textY - fontHeight / 2);
            g2d.scale(scale, scale);
            g2d.translate(-textBounds.getWidth() / 2, fontHeight / 2);

            int shadowX = 8;
            int shadowY = 8;

            g2d.translate(shadowX, shadowY);
            g2d.setColor(shadowColor);
            g2d.fill(textShape);
            g2d.translate(-shadowX, -shadowY);

            g2d.setColor(outlineColor);
            g2d.setStroke(new BasicStroke(8));
            g2d.draw(textShape);

            float startX = (float) textBounds.getX();
            float startY = (float) textBounds.getY();
            float endX   = startX;
            float endY   = (float) (startY + textBounds.getHeight());

            GradientPaint gradientPaint = new GradientPaint(startX, startY, textStartColor, endX, endY, textEndColor);
            g2d.setPaint(gradientPaint);
            g2d.fill(textShape);

            g2d.setTransform(prevTransform);
            g2d.setStroke(prevStroke);

        } else {
            renderTankHUD(level, g2d);

            if (level.isRocketOutOfLevelBounds()) {
                renderDangerHUD(level, g2d);
            }
        }
    }

    private void renderTankHUD(Level level, Graphics2D g2d) {
        int tankWidth        = 200;
        int tankHeight       = 30;
        int tankX            = 40;
        int tankY            = WINDOW_HEIGHT - tankHeight - 40;
        int tankStrokeWeight = 4;

        double fuelRatio      = level.getRocket().getTank().getFuelVolume() / level.getRocket().getTank().getCapacity();
        int    fuelPercentage = (int) (fuelRatio * 100);
        String percentageText = fuelPercentage + "%";

        Stroke prevStroke = g2d.getStroke();

        RoundRectangle2D.Double tankShape = new RoundRectangle2D.Double(tankX, tankY, tankWidth, tankHeight, Math.min(tankWidth, tankHeight), Math.min(tankWidth, tankHeight));
        g2d.setStroke(new BasicStroke(tankStrokeWeight));
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.draw(tankShape);
        g2d.setStroke(prevStroke);

        Shape prevClip = g2d.getClip();

        RoundRectangle2D.Double fuelShape = new RoundRectangle2D.Double(tankX + tankStrokeWeight, tankY + tankStrokeWeight, tankWidth - tankStrokeWeight * 2, tankHeight - tankStrokeWeight * 2, Math.min(tankWidth, tankHeight), Math.min(tankWidth, tankHeight));
        GradientPaint           gradient  = new GradientPaint(tankX, tankY, Color.GREEN, tankX + tankWidth, tankY, new Color(26, 133, 40));
        g2d.setClip(new Rectangle(tankX + tankStrokeWeight, tankY + tankStrokeWeight, (int) ((tankWidth - tankStrokeWeight * 2) * fuelRatio), tankHeight));
        g2d.setPaint(gradient);
        g2d.fill(fuelShape);

        g2d.setClip(prevClip);

        Font        font       = g2d.getFont().deriveFont(12.0f).deriveFont(Font.BOLD);
        Rectangle2D textBounds = font.getStringBounds(percentageText, g2d.getFontRenderContext());
        g2d.setColor(Color.WHITE);
        g2d.setFont(font);
        g2d.drawString(percentageText, tankX + (int) ((tankWidth - textBounds.getWidth()) / 2), tankY + tankHeight / 2 + 5);
    }

    private void renderDangerHUD(Level level, Graphics2D g2d) {
        Composite prevComposite = g2d.getComposite();

        double alpha             = 0.5 + 0.5 * (Math.cos(System.currentTimeMillis() / 10.0) + 1) / 2;
        int    dangerImageWidth  = dangerImage.getWidth(null);
        int    dangerImageHeight = dangerImage.getHeight(null);
        int    yOffset           = 10;

        Area dangerHUDShape = new Area(new RoundRectangle2D.Double(20, yOffset + dangerImageHeight / 2.0 - 8, (WINDOW_WIDTH - dangerImageWidth) / 2.0 - 40, 10, 10, 10));
        dangerHUDShape.add(new Area(new RoundRectangle2D.Double(20 + (WINDOW_WIDTH + dangerImageWidth) / 2.0, yOffset + dangerImageHeight / 2.0 - 8, (WINDOW_WIDTH - dangerImageWidth) / 2.0 - 40, 10, 10, 10)));
        dangerHUDShape.add(new Area(new RoundRectangle2D.Double(20, yOffset + dangerImageHeight / 2.0 - 8, 10, WINDOW_HEIGHT - 40, 10, 10)));
        dangerHUDShape.add(new Area(new RoundRectangle2D.Double(WINDOW_WIDTH - 30, yOffset + dangerImageHeight / 2.0 - 8, 10, WINDOW_HEIGHT - 40, 10, 10)));
        dangerHUDShape.add(new Area(new RoundRectangle2D.Double(20, WINDOW_HEIGHT - 28, WINDOW_WIDTH - 40, 10, 10, 10)));

        Font        dangerTextFont   = FontHelper.BEBAS_NEUE_FONT.deriveFont(18.0f).deriveFont(Font.ITALIC);
        String      dangerText       = "/!\\ VOUS VOUS ELOIGNEZ DE L'OBJECTIF /!\\";
        Rectangle2D dangerTextBounds = dangerTextFont.getStringBounds(dangerText, g2d.getFontRenderContext());

        String      countdownText       = String.format("%.2fs AVANT L'ANNULATION DE LA MISSION", level.getOutOfBoundsCountdown()).replace(",", ".");
        Font        countdownTextFont   = FontHelper.BEBAS_NEUE_FONT.deriveFont(15.0f).deriveFont(Font.BOLD);
        Rectangle2D countdownTextBounds = countdownTextFont.getStringBounds(countdownText, g2d.getFontRenderContext());

        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) alpha);

        g2d.setComposite(alphaComposite);
        g2d.drawImage(dangerImage, (WINDOW_WIDTH - dangerImageWidth) / 2, yOffset, dangerImageWidth, dangerImageHeight, null);

        g2d.setColor(Color.RED);
        g2d.fill(dangerHUDShape);

        g2d.setFont(dangerTextFont);
        g2d.drawString(dangerText, (int) ((WINDOW_WIDTH - dangerTextBounds.getWidth()) / 2), dangerImageHeight + yOffset + 25);

        g2d.setFont(countdownTextFont);
        g2d.drawString(countdownText, (int) ((WINDOW_WIDTH - countdownTextBounds.getWidth()) / 2), dangerImageHeight + yOffset + 50);

        g2d.setComposite(prevComposite);
    }

}

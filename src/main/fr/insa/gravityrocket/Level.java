package fr.insa.gravityrocket;

import fr.insa.gravityrocket.graphics.FontHelper;
import fr.insa.gravityrocket.graphics.ImageHelper;
import fr.insa.gravityrocket.logic.entity.Entity;
import fr.insa.gravityrocket.logic.entity.IDestroyable;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;
import fr.insa.gravityrocket.sound.SoundHelper;
import javafx.scene.media.MediaPlayer;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static fr.insa.gravityrocket.GravityRocket.WINDOW_HEIGHT;
import static fr.insa.gravityrocket.GravityRocket.WINDOW_WIDTH;

public class Level
{

    private final Image       dangerImage;
    private final MediaPlayer dangerSoundPlayer;

    /**
     * L'ensemble des entités présentes dans le niveau
     */
    private final Set<Entity> entitySet = new HashSet<>();
    /**
     * La vue par défaut du niveau, avec une dimension appropriée. On s'y ramène dès que la fusée est dans cette zone,
     * autrement on passe à une vue plus grande du niveau.
     */
    private final Rectangle   preferredView;
    /**
     * La limite du niveau, en dehors de laquelle le joueur perdra le niveau
     */
    private final Rectangle   bounds;
    private       Rocket      rocket;
    private       double      outOfBoundsCountdown;

    private boolean gameOver;

    public Level(Rectangle preferredView, Rectangle bounds) {
        this.preferredView = preferredView;
        this.bounds = bounds;
        this.dangerImage = ImageHelper.loadImage("/textures/danger.png", 286, 40);
        this.dangerSoundPlayer = SoundHelper.createPlayer("/sounds/alarm.wav", true);
        this.dangerSoundPlayer.setVolume(0.5);
        resetOutOfBoundsCountdown();
    }

    private void resetOutOfBoundsCountdown() {
        this.outOfBoundsCountdown = 15;
    }

    /**
     * Ajoute une entité dans le niveau
     *
     * @param entity L'entité à ajouter
     */
    public void addEntity(Entity entity) {
        this.entitySet.add(entity);

        if (entity instanceof Rocket) {
            this.rocket = (Rocket) entity;
        }
    }

    /**
     * Méthode de mise à jour du niveau, elle est appelée 20 fois par seconde et met à jour toutes les entités du
     * niveau.
     *
     * @param dt La petite variation de temps entre l'instant de la dernière mise à jour et le temps actuel
     */
    public void update(double dt) {

        Iterator<Entity> iterator = this.entitySet.iterator();

        while (iterator.hasNext()) {
            Entity entity = iterator.next();

            //Si une entité est destructible et est détruite, on la retire du niveau
            if (entity instanceof IDestroyable && ((IDestroyable) entity).isDestroyed()) {

                if (entity instanceof Rocket) {
                    this.rocket = null;
                }

                iterator.remove();
                continue;
            }

            if (!(entity instanceof Planet)) {
                applyGravitationalAcceleration(entity);
            }

            entity.update(dt);
        }

        if (this.rocket != null && rocketOutOfLevelBounds()) {
            this.dangerSoundPlayer.play();
            GravityRocket.getInstance().getMusicPlayer().pause();
            this.outOfBoundsCountdown = Math.max(0, this.outOfBoundsCountdown - dt);

            if (this.outOfBoundsCountdown == 0) {
                this.gameOver = true;
            }

        } else {
            this.dangerSoundPlayer.stop();
            GravityRocket.getInstance().getMusicPlayer().play();
            resetOutOfBoundsCountdown();
        }

    }

    private void applyGravitationalAcceleration(Entity entity) {

        double forceX = 0;
        double forceY = 0;

        for (Entity otherEntity : this.entitySet) {

            if (otherEntity == entity) {
                continue;
            }

            double distanceToOther = otherEntity.distance(entity);

            if (distanceToOther != 0) {
                forceX += entity.gravitationalForce(otherEntity) * (otherEntity.getXPos() - entity.getXPos()) / distanceToOther;
                forceY += entity.gravitationalForce(otherEntity) * (otherEntity.getYPos() - entity.getYPos()) / distanceToOther;
            }
        }

        //Application du principe fondamental de la dynamique: somme des forces = masse * acceleration => acceleration = somme des forces / masse
        entity.setXAcceleration(forceX / entity.getMass());
        entity.setYAcceleration(forceY / entity.getMass());
    }

    private boolean rocketOutOfLevelBounds() {
        return !this.bounds.contains(this.rocket.getXPos(), this.rocket.getYPos());
    }

    /**
     * Dessine le niveau à l'écran
     *
     * @param g2d L'instance de Graphics2D permettant de réaliser le rendu
     */
    public void render(Graphics2D g2d) {

        AffineTransform prevTransform = g2d.getTransform();

        applyCameraTransform(g2d);

        for (Entity entity : entitySet) {
            renderEntity(g2d, entity);
        }

        g2d.setTransform(prevTransform);

        renderHUD(g2d);
    }

    private void applyCameraTransform(Graphics2D g2d) {

        AffineTransform transform = new AffineTransform();

        double minX = this.rocket.getXPos() - Math.max(this.rocket.getWidth(), this.rocket.getHeight());
        double minY = this.rocket.getYPos() - Math.max(this.rocket.getWidth(), this.rocket.getHeight());
        double maxX = this.rocket.getXPos() + Math.max(this.rocket.getWidth(), this.rocket.getHeight());
        double maxY = this.rocket.getYPos() + Math.max(this.rocket.getWidth(), this.rocket.getHeight());

        double offsetX = 0;
        double offsetY = 0;

        if (minX < this.preferredView.x) {
            offsetX = this.preferredView.x - minX;
        } else if (maxX > this.preferredView.x + this.preferredView.width) {
            offsetX = maxX - (this.preferredView.x + this.preferredView.width);
        }

        if (minY < this.preferredView.y) {
            offsetY = this.preferredView.y - minY;
        } else if (maxY > this.preferredView.y + this.preferredView.height) {
            offsetY = maxY - (this.preferredView.y + this.preferredView.height);
        }

        double scaleX = WINDOW_WIDTH / (this.preferredView.width + Math.max(offsetX, offsetY) * 2);
        double scaleY = WINDOW_HEIGHT / (this.preferredView.height + Math.max(offsetX, offsetY) * 2);

        double scale = Math.min(scaleX, scaleY);

        double translateX = -this.preferredView.x * scale + (WINDOW_WIDTH - this.preferredView.width * scale) / 2;
        double translateY = -this.preferredView.y * scale + (WINDOW_HEIGHT - this.preferredView.height * scale) / 2;

        transform.translate(translateX, translateY);
        transform.scale(scale, scale);
        g2d.transform(transform);
    }

    private void renderEntity(Graphics2D g2d, Entity entity) {
        AffineTransform prevTransform = g2d.getTransform();

        AffineTransform transformation = new AffineTransform();
        int             renderX        = (int) (entity.getXPos() - entity.getWidth() / 2);
        int             renderY        = (int) (entity.getYPos() - entity.getHeight() / 2);

        //On translate le rendu à la position de l'entité
        transformation.translate(renderX, renderY);

        //On applique la rotation
        transformation.translate(entity.getWidth() / 2, entity.getHeight() / 2);
        transformation.rotate(entity.getRotation());
        transformation.translate(-entity.getWidth() / 2, -entity.getHeight() / 2);

        g2d.transform(transformation);
        entity.render(g2d);
        g2d.setTransform(prevTransform);
    }

    private void renderHUD(Graphics2D g2d) {

        if (this.gameOver) {

            String      gameOverText       = "GAME OVER";
            float       fontHeight         = 150;
            Font        font               = FontHelper.BEBAS_NEUE_FONT.deriveFont(fontHeight).deriveFont(Font.BOLD);
            Rectangle2D gameOverTextBounds = font.getStringBounds(gameOverText, g2d.getFontRenderContext());
            int         textX              = (int) ((WINDOW_WIDTH - gameOverTextBounds.getWidth()) / 2);
            int         textY              = WINDOW_HEIGHT / 2;

            double scale = 0.9 + 0.1 * (Math.cos(System.currentTimeMillis() / 1000.0) + 1) / 2;

            GlyphVector glyphVector = font.createGlyphVector(g2d.getFontRenderContext(), gameOverText);
            Shape       textShape   = glyphVector.getOutline();

            AffineTransform prevTransform = g2d.getTransform();
            Stroke          prevStroke    = g2d.getStroke();

            g2d.translate(textX + gameOverTextBounds.getWidth() / 2, textY - fontHeight / 2);

            g2d.scale(scale, scale);
            g2d.translate(-gameOverTextBounds.getWidth() / 2, fontHeight / 2);

            int shadowX = 8;
            int shadowY = 8;

            g2d.translate(shadowX, shadowY);
            g2d.setColor(new Color(42, 42, 42));
            g2d.fill(textShape);
            g2d.translate(-shadowX, -shadowY);

            g2d.setColor(new Color(75, 75, 75));
            g2d.setStroke(new BasicStroke(8));
            g2d.draw(textShape);

            float startX = (float) gameOverTextBounds.getX();
            float startY = (float) gameOverTextBounds.getY();
            float endX   = startX;
            float endY   = (float) (startY + gameOverTextBounds.getHeight());

            GradientPaint gradientPaint = new GradientPaint(startX, startY, Color.RED, endX, endY, Color.DARK_GRAY);
            g2d.setPaint(gradientPaint);
            g2d.fill(textShape);

            g2d.setTransform(prevTransform);
            g2d.setStroke(prevStroke);

        } else {
            if (this.rocket != null) {
                renderTankHUD(g2d);

                if (rocketOutOfLevelBounds()) {
                    renderDangerHUD(g2d);
                }
            }
        }
    }

    private void renderTankHUD(Graphics2D g2d) {
        int tankWidth        = 200;
        int tankHeight       = 30;
        int tankX            = 40;
        int tankY            = WINDOW_HEIGHT - tankHeight - 40;
        int tankStrokeWeight = 4;

        double fuelRatio      = this.rocket.getTank().getFuelVolume() / this.rocket.getTank().getCapacity();
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

    private void renderDangerHUD(Graphics2D g2d) {
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

        String      countdownText       = String.format("%.2fs AVANT L'ANNULATION DE LA MISSION", this.outOfBoundsCountdown).replace(",", ".");
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
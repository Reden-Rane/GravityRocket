package fr.insa.gravityrocket.logic;

import fr.insa.gravityrocket.GravityRocket;
import fr.insa.gravityrocket.logic.entity.Entity;
import fr.insa.gravityrocket.logic.entity.IDestroyable;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;
import fr.insa.gravityrocket.sound.SoundHelper;
import javafx.scene.media.MediaPlayer;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Level
{

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
    private       Planet      targetedPlanet;
    private       double      outOfBoundsCountdown;

    private boolean          gameOver;
    private EnumGameOverType gameOverType;

    public Level(Rectangle preferredView, Rectangle bounds) {
        this.preferredView = preferredView;
        this.bounds = bounds;
        this.dangerSoundPlayer = SoundHelper.createPlayer("/sounds/alarm.wav", true);
        this.dangerSoundPlayer.setVolume(0.5);
        resetOutOfBoundsCountdown();
    }

    private void resetOutOfBoundsCountdown() {
        //On reset le compteur à rebours à 15 secondes
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

        if (!isGameOver()) {
            Iterator<Entity> iterator = this.entitySet.iterator();

            while (iterator.hasNext()) {
                Entity entity = iterator.next();
                entity.setXAcceleration(0);
                entity.setYAcceleration(0);
                entity.update(dt);

                //Si une entité est destructible et est détruite, on la retire du niveau
                if (entity instanceof IDestroyable && ((IDestroyable) entity).isDestroyed()) {
                    iterator.remove();
                }
            }

            if (isRocketOutOfLevelBounds()) {
                this.dangerSoundPlayer.play();
                GravityRocket.getInstance().getMusicPlayer().pause();
                this.outOfBoundsCountdown = Math.max(0, this.outOfBoundsCountdown - dt);

                if (this.outOfBoundsCountdown == 0) {
                    this.dangerSoundPlayer.stop();
                    setGameOver(true, EnumGameOverType.OUT_OF_LEVEL);
                }

            } else {
                this.dangerSoundPlayer.stop();
                GravityRocket.getInstance().getMusicPlayer().play();
                resetOutOfBoundsCountdown();
            }
        }

    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isRocketOutOfLevelBounds() {
        return !getBounds().contains(getRocket().getXPos(), getRocket().getYPos());
    }

    public void setGameOver(boolean gameOver, EnumGameOverType enumGameOverType) {
        this.gameOver = gameOver;
        this.gameOverType = enumGameOverType;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Rocket getRocket() {
        return rocket;
    }

    public Set<Entity> getEntitySet() {
        return entitySet;
    }

    public Rectangle getPreferredView() {
        return preferredView;
    }

    public EnumGameOverType getGameOverType() {
        return gameOverType;
    }

    public double getOutOfBoundsCountdown() {
        return outOfBoundsCountdown;
    }

    public Planet getTargetedPlanet() {
        return targetedPlanet;
    }

    public void setTargetedPlanet(Planet targetedPlanet) {
        this.targetedPlanet = targetedPlanet;
    }

}
package fr.insa.gravityrocket.logic.level;

import fr.insa.gravityrocket.GravityRocket;
import fr.insa.gravityrocket.logic.EnumLevelState;
import fr.insa.gravityrocket.logic.SoundHandler;
import fr.insa.gravityrocket.logic.entity.Entity;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;
import javafx.scene.media.MediaPlayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class Level
{

    private final MediaPlayer dangerSoundPlayer;
    private final MediaPlayer successSoundPlayer;

    private final Image levelBackground;

    /**
     * Les entités à ajouter dans le niveau, évite les ConcurrentModificationException si on ajoute une entité pendant
     * la mise à jour d'une entité existante par exemple. On utilise une LinkedList car on y fait uniquement des
     * ajouts/suppressions donc il est préférable que ces opérations ait une complexité en O(1)
     */
    private final List<Entity> toAddEntityList = new LinkedList<>();
    /**
     * L'ensemble des entités présentes dans le niveau. On utilise une ArrayList car on parcourt souvent cette liste
     * donc il est préférable d'avoir une complexité de O(1) pour le parcours
     */
    private final List<Entity> entityList      = new ArrayList<>();
    /**
     * La vue par défaut du niveau, avec une dimension appropriée. On s'y ramène dès que la fusée est dans cette zone,
     * autrement on passe à une vue plus grande du niveau.
     */
    private final Rectangle    preferredView;
    /**
     * La limite du niveau, en dehors de laquelle le joueur perdra le niveau
     */
    private final Rectangle    bounds;
    private       Rocket       rocket;
    private       double       outOfBoundsCountdown;

    private EnumLevelState levelState = EnumLevelState.RUNNING;

    public Level(Image levelBackground, Rectangle preferredView, Rectangle bounds) {
        this.levelBackground = levelBackground;
        this.preferredView = preferredView;
        this.bounds = bounds;
        this.dangerSoundPlayer = SoundHandler.createPlayer("/sounds/alarm.wav", true);
        this.dangerSoundPlayer.setVolume(0.5);
        this.successSoundPlayer = SoundHandler.createPlayer("/sounds/success.wav", false);
        resetOutOfBoundsCountdown();
    }

    public void resetLevel() {
        this.stopAllSounds();
        this.setLevelState(EnumLevelState.RUNNING);
        this.toAddEntityList.clear();
        this.entityList.clear();
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
        this.toAddEntityList.add(entity);

        if (entity instanceof Rocket) {
            this.rocket = (Rocket) entity;
        }
    }

    public void stopAllSounds() {
        this.dangerSoundPlayer.stop();
        this.successSoundPlayer.stop();
        if (getRocket() != null) {
            this.getRocket().stopAllEngines();
        }
    }

    public void setLevelState(EnumLevelState levelState) {
        this.levelState = levelState;

        if (this.levelState == EnumLevelState.SUCCESS) {
            this.successSoundPlayer.play();
        }
    }

    /**
     * Méthode de mise à jour du niveau, elle est appelée 20 fois par seconde et met à jour toutes les entités du
     * niveau.
     *
     * @param dt La petite variation de temps entre l'instant de la dernière mise à jour et le temps actuel
     */
    public void update(double dt) {

        this.entityList.addAll(this.toAddEntityList);
        this.toAddEntityList.clear();

        Iterator<Entity> iterator = this.entityList.iterator();

        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            updateEntity(entity, dt);

            if (entity.doesRequestRemove()) {
                iterator.remove();
            }
        }

        if (isRocketOutOfLevelBounds()) {
            this.dangerSoundPlayer.play();
            GravityRocket.getInstance().getMusicPlayer().pause();
            this.outOfBoundsCountdown = Math.max(0, this.outOfBoundsCountdown - dt);

            if (this.outOfBoundsCountdown == 0) {
                this.dangerSoundPlayer.stop();
                setLevelState(EnumLevelState.OUT_OF_LEVEL);
            }

        } else {
            this.dangerSoundPlayer.stop();
            GravityRocket.getInstance().getMusicPlayer().play();
            resetOutOfBoundsCountdown();
        }

    }

    public void handleEntityCollision(Entity entity1, Entity entity2) {
        entity1.onCollisionWith(entity2);
    }

    public boolean isGameOver() {
        return getLevelState() != EnumLevelState.RUNNING;
    }

    public boolean isRocketOutOfLevelBounds() {
        return !getBounds().contains(getRocket().getXPos(), getRocket().getYPos());
    }

    public EnumLevelState getLevelState() {
        return levelState;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Rocket getRocket() {
        return rocket;
    }

    /**
     * Mets à jour l'entité passée en paramètre
     *
     * @param entity L'entité à mettre à jour
     * @param dt     Le delta de temps écoulé
     */
    private void updateEntity(Entity entity, double dt) {
        entity.setXAcceleration(0);
        entity.setYAcceleration(0);
        entity.update(dt);
    }

    public Rectangle getPreferredView() {
        return preferredView;
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public double getOutOfBoundsCountdown() {
        return outOfBoundsCountdown;
    }

    public Image getLevelBackground() {
        return levelBackground;
    }

}
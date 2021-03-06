package fr.insa.gravityrocket.logic.level;

import fr.insa.gravityrocket.GravityRocket;
import fr.insa.gravityrocket.logic.EnumLevelState;
import fr.insa.gravityrocket.logic.entity.Asteroid;
import fr.insa.gravityrocket.logic.entity.Entity;
import fr.insa.gravityrocket.logic.entity.EnumAsteroidVariant;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.item.ItemFuel;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;
import javafx.scene.media.MediaPlayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class Level
{

    private final MediaPlayer musicPlayer;
    private final Image       levelBackground;

    /**
     * Les entités à ajouter dans le niveau, évite les ConcurrentModificationException si on ajoute une entité pendant
     * la mise à jour d'une entité existante par exemple. On utilise une LinkedList car on y fait uniquement des
     * ajouts/suppressions donc il est préférable que ces opérations ait une complexité en O(1)
     */
    private final List<Entity> toAddEntityList    = new LinkedList<>();
    /**
     * Les entités à supprimer du niveau, évite les ConcurrentModificationException si on supprime une entité pendant la
     * mise à jour d'une entité existante par exemple. On utilise une LinkedList car on y fait uniquement des
     * ajouts/suppressions donc il est préférable que ces opérations ait une complexité en O(1)
     */
    private final List<Entity> toRemoveEntityList = new LinkedList<>();

    /**
     * L'ensemble des entités présentes dans le niveau. On utilise une ArrayList car on parcourt souvent cette liste
     * donc il est préférable d'avoir une complexité de O(1) pour le parcours
     */
    private final List<Entity> entityList = new ArrayList<>();
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

    /**
     * L'angle maximum que doit avoir la fusée avec la normale de la planète en atterissant, sinon elle se crash L'angle
     * est exprimé en radians
     */
    private final double maximumAngle;
    /**
     * La vitesse maximale que doit avoir la fusée en atterissant, sinon elle se crash La vitesse est exprimée en m/s
     */
    private final int    maximumSpeed;

    private EnumLevelState levelState = EnumLevelState.RUNNING;

    public Level(MediaPlayer musicPlayer, Image levelBackground, Rectangle preferredView, Rectangle bounds) {
        this(musicPlayer, levelBackground, preferredView, bounds, Math.toRadians(20), 100);
    }

    public Level(MediaPlayer musicPlayer, Image levelBackground, Rectangle preferredView, Rectangle bounds, double maximumAngle, int maximumSpeed) {
        this.musicPlayer = musicPlayer;
        this.levelBackground = levelBackground;
        this.preferredView = preferredView;
        this.bounds = bounds;
        this.maximumAngle = maximumAngle;
        this.maximumSpeed = maximumSpeed;
        resetOutOfBoundsCountdown();
    }

    /**
     * Réinitialise le niveau
     */
    public void resetLevel() {
        GravityRocket.getInstance().getSoundHandler().stopAllSounds();
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

    public void removeEntity(Entity entity) {
        this.toRemoveEntityList.add(entity);
    }

    /**
     * Méthode de mise à jour du niveau, elle est appelée 20 fois par seconde et met à jour toutes les entités du
     * niveau.
     *
     * @param dt La petite variation de temps entre l'instant de la dernière mise à jour et le temps actuel
     */
    public void update(double dt) {

        //On ajoute les entités mises dans la file d'attente, évitant ainsi les ConcurrentModificationException
        this.entityList.addAll(this.toAddEntityList);
        this.toAddEntityList.clear();

        //On met à jour toutes les entités
        for (Entity entity : this.entityList) {
            updateEntity(entity, dt);
        }

        this.entityList.removeAll(toRemoveEntityList);
        this.toRemoveEntityList.clear();

        //Si la fusée est en dehors des bordures du niveau on affiche le message de danger
        if (isRocketOutOfLevelBounds()) {
            GravityRocket.getInstance().getSoundHandler().dangerSoundPlayer.play();
            this.musicPlayer.pause();
            this.outOfBoundsCountdown = Math.max(0, this.outOfBoundsCountdown - dt);

            if (this.outOfBoundsCountdown == 0) {
                GravityRocket.getInstance().getSoundHandler().dangerSoundPlayer.stop();
                this.musicPlayer.play();
                setLevelState(EnumLevelState.OUT_OF_LEVEL);
            }
        } else {
            GravityRocket.getInstance().getSoundHandler().dangerSoundPlayer.stop();
            this.musicPlayer.play();
            resetOutOfBoundsCountdown();
        }

    }

    public void setLevelState(EnumLevelState levelState) {
        this.levelState = levelState;

        if (this.levelState != EnumLevelState.RUNNING) {
            if (getRocket() != null) {
                this.getRocket().stopAllEngines();
            }
        }

        if (this.levelState == EnumLevelState.SUCCESS) {
            GravityRocket.getInstance().getSoundHandler().successSoundPlayer.play();
        }
    }

    public void handleEntityCollision(Entity entity1, Entity entity2) {

        if (entity1 instanceof Rocket) {

            if (entity2 instanceof Planet) {

                Planet planet = (Planet) entity2;

                getRocket().stopAllEngines();

                if (canRocketLandOn(planet)) {
                    getRocket().getBoosterReactor().setActive(false);
                    getRocket().attachToPlanet(planet);

                } else {
                    getRocket().explode();
                    setLevelState(EnumLevelState.CRASH);
                }
            } else if (entity2 instanceof Asteroid) {
                getRocket().explode();
                setLevelState(EnumLevelState.CRASH);
            }

        }

        entity1.onCollisionWith(entity2);
    }

    /**
     * @return Vrai si la fusée est trop loin de la zone du niveau
     */
    public boolean isRocketOutOfLevelBounds() {
        return !getBounds().contains(getRocket().getXPos(), getRocket().getYPos());
    }

    public boolean isGameOver() {
        return getLevelState() != EnumLevelState.RUNNING;
    }

    /**
     * @param planet La planète sur laquelle on veut se poser
     *
     * @return Vrai si la fusée à un angle correct et une vitesse raisonnable pour se poser
     */
    protected boolean canRocketLandOn(Planet planet) {
        double speedMagnitude = getRocket().getSpeedMagnitude();
        return getRocket().getAngleWithPlanet(planet) < getMaximumAngle() && speedMagnitude < getMaximumSpeed();
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

    protected void addAsteroid(int radius, EnumAsteroidVariant variant, int x, int y, double v) {
        Asteroid asteroid = new Asteroid(this, radius, variant);
        asteroid.setPos(x, y);
        asteroid.setRotationSpeed(v);
        addEntity(asteroid);
    }

    protected void addFuel(int size, int volume, int x, int y) {
        ItemFuel itemFuel = new ItemFuel(this, size, volume);
        itemFuel.setPos(x, y);
        addEntity(itemFuel);
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

    public double getMaximumAngle() {
        return maximumAngle;
    }

    public int getMaximumSpeed() {
        return maximumSpeed;
    }

    public MediaPlayer getMusicPlayer() {
        return musicPlayer;
    }

}
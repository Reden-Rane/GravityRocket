package fr.insa.gravityrocket.logic.level;

import fr.insa.gravityrocket.logic.EnumLevelState;
import fr.insa.gravityrocket.logic.entity.Entity;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;
import javafx.scene.media.MediaPlayer;

import java.awt.*;

/**
 * Type de niveau où l'objectif est d'atterrir sur une planète
 */
public abstract class LandingLevel extends Level
{

    /**
     * La planète sur laquelle on doit atterir
     */
    private Planet targetedPlanet;
    /**
     * La taille du halo autour de la planète cible, utile pour le rendu afin d'indiquer la planète cible au joueur
     */
    private double haloSize = 30;

    public LandingLevel(MediaPlayer musicPlayer, Image levelBackground, Rectangle preferredView, Rectangle bounds) {
        super(musicPlayer, levelBackground, preferredView, bounds);
    }

    public LandingLevel(MediaPlayer musicPlayer, Image levelBackground, Rectangle preferredView, Rectangle bounds, double maximumAngle, int maximumSpeed) {
        super(musicPlayer, levelBackground, preferredView, bounds, maximumAngle, maximumSpeed);
    }

    @Override
    public void handleEntityCollision(Entity entity1, Entity entity2) {

        super.handleEntityCollision(entity1, entity2);

        //Si le joueur atterit sur la bonne planète, alors le niveau est réussi
        if (entity1 instanceof Rocket && entity2 instanceof Planet) {
            if (canRocketLandOn((Planet) entity2) && entity2 == getTargetedPlanet()) {
                setLevelState(EnumLevelState.SUCCESS);
            }
        }
    }

    public Planet getTargetedPlanet() {
        return targetedPlanet;
    }

    public void setTargetedPlanet(Planet targetedPlanet) {
        this.targetedPlanet = targetedPlanet;
    }

    public double getHaloSize() {
        return haloSize;
    }

    public void setTargetedPlanetHaloSize(double haloSize) {
        this.haloSize = haloSize;
    }

}

package fr.insa.gravityrocket.logic.level;

import fr.insa.gravityrocket.logic.EnumLevelState;
import fr.insa.gravityrocket.logic.entity.Entity;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;
import javafx.scene.media.MediaPlayer;

import java.awt.*;

/**
 * Type de niveau où l'objectif est de rejoindre une zone/constellation
 */
public abstract class ReachingZoneLevel extends Level
{

    /**
     * Le nom de la zone/constellation
     */
    private final String zoneName;
    /**
     * La forme de la zone
     */
    private final Shape  zone;

    public ReachingZoneLevel(String zoneName, MediaPlayer musicPlayer, Image backgroundImage, Rectangle preferredView, Rectangle bounds, Shape zone) {
        super(musicPlayer, backgroundImage, preferredView, bounds);
        this.zoneName = zoneName;
        this.zone = zone;
    }

    @Override
    public void update(double dt) {
        super.update(dt);

        //Si la fusée est dans la zone, le niveau est réussi
        if (getZone().contains(getRocket().getXPos(), getRocket().getYPos())) {
            setLevelState(EnumLevelState.SUCCESS);
        }
    }

    @Override
    public void handleEntityCollision(Entity entity1, Entity entity2) {

        if (entity1 instanceof Rocket && entity2 instanceof Planet) {

            Planet planet = (Planet) entity2;

            getRocket().stopAllEngines();

            //On peut atterir sur n'importe qu'elle planète à condition d'avoir une bonne vitesse/angle sinon on se crash
            if (canRocketLandOn(planet)) {
                getRocket().getBoosterReactor().setActive(false);
                getRocket().attachToPlanet(planet);

            } else {
                getRocket().explode();
                setLevelState(EnumLevelState.CRASH);
            }

        } else {
            super.handleEntityCollision(entity1, entity2);
        }
    }

    public Shape getZone() {
        return zone;
    }

    public String getZoneName() {
        return zoneName;
    }

}

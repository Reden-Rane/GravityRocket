package fr.insa.gravityrocket.logic.level;

import fr.insa.gravityrocket.logic.EnumLevelState;
import fr.insa.gravityrocket.logic.entity.Entity;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;

import java.awt.*;

public abstract class LandingLevel extends Level
{

    /**
     * L'angle maximum que doit avoir la fusée avec la normale de la planète en atterissant, sinon elle se crash L'angle
     * est exprimé en radians
     */
    private final double maximumAngle;
    /**
     * La vitesse maximale que doit avoir la fusée en atterissant, sinon elle se crash La vitesse est exprimée en m/s
     */
    private final int    maximumSpeed;
    private       Planet targetedPlanet;
    private       double haloSize = 30;

    public LandingLevel(Image backgroundImage, Rectangle preferredView, Rectangle bounds) {
        this(backgroundImage, preferredView, bounds, Math.toRadians(20), 100);
    }

    public LandingLevel(Image backgroundImage, Rectangle preferredView, Rectangle bounds, double maximumAngle, int maximumSpeed) {
        super(backgroundImage, preferredView, bounds);
        this.maximumAngle = maximumAngle;
        this.maximumSpeed = maximumSpeed;
    }

    @Override
    public void handleEntityCollision(Entity entity1, Entity entity2) {

        if (entity1 instanceof Rocket && entity2 instanceof Planet) {

            Planet planet = (Planet) entity2;

            getRocket().stopAllEngines();

            if (canRocketLandOn(planet)) {

                if (getTargetedPlanet() == planet) {
                    setLevelState(EnumLevelState.SUCCESS);
                }

                getRocket().getBoosterReactor().setActive(false);
                getRocket().attachToPlanet(planet);

            } else {
                getRocket().crashRocket();
                setLevelState(EnumLevelState.CRASH);
            }

        } else {
            super.handleEntityCollision(entity1, entity2);
        }
    }

    private boolean canRocketLandOn(Planet planet) {
        double speedMagnitude = getRocket().getSpeedMagnitude();
        return getRocket().getAngleWithPlanet(planet) < getMaximumAngle() && speedMagnitude < getMaximumSpeed();
    }

    public Planet getTargetedPlanet() {
        return targetedPlanet;
    }

    public double getMaximumAngle() {
        return maximumAngle;
    }

    public int getMaximumSpeed() {
        return maximumSpeed;
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
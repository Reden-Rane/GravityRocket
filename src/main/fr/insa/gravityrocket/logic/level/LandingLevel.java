package fr.insa.gravityrocket.logic.level;

import fr.insa.gravityrocket.logic.EnumLevelState;
import fr.insa.gravityrocket.logic.entity.Entity;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;

import java.awt.*;

public abstract class LandingLevel extends Level
{

    private Planet targetedPlanet;
    private double haloSize = 30;

    public LandingLevel(Image levelBackground, Rectangle preferredView, Rectangle bounds) {
        super(levelBackground, preferredView, bounds);
    }

    public LandingLevel(Image levelBackground, Rectangle preferredView, Rectangle bounds, double maximumAngle, int maximumSpeed) {
        super(levelBackground, preferredView, bounds, maximumAngle, maximumSpeed);
    }

    @Override
    public void handleEntityCollision(Entity entity1, Entity entity2) {

        super.handleEntityCollision(entity1, entity2);

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

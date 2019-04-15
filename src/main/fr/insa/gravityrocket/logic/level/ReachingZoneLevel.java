package fr.insa.gravityrocket.logic.level;

import fr.insa.gravityrocket.logic.EnumLevelState;
import fr.insa.gravityrocket.logic.entity.Entity;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;

import java.awt.*;

public abstract class ReachingZoneLevel extends Level
{

    private final Shape zone;

    public ReachingZoneLevel(Image backgroundImage, Rectangle preferredView, Rectangle bounds, Shape zone) {
        super(backgroundImage, preferredView, bounds);
        this.zone = zone;
    }

    @Override
    public void update(double dt) {
        super.update(dt);

        if (getZone().contains(getRocket().getXPos(), getRocket().getYPos())) {
            setLevelState(EnumLevelState.SUCCESS);
        }
    }

    @Override
    public void handleEntityCollision(Entity entity1, Entity entity2) {

        if (entity1 instanceof Rocket && entity2 instanceof Planet) {

            Planet planet = (Planet) entity2;

            getRocket().stopAllEngines();

            if (canRocketLandOn(planet)) {
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

    public Shape getZone() {
        return zone;
    }

}

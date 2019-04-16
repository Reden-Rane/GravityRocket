package fr.insa.gravityrocket.logic.level;

import fr.insa.gravityrocket.logic.EnumLevelState;
import fr.insa.gravityrocket.logic.entity.Entity;
import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;
import javafx.scene.media.MediaPlayer;

import java.awt.*;

public abstract class ReachingZoneLevel extends Level
{

    private final String zoneName;
    private final Shape  zone;

    public ReachingZoneLevel(String zoneName, MediaPlayer musicPlayer, Image backgroundImage, Rectangle preferredView, Rectangle bounds, Shape zone) {
        super(musicPlayer, backgroundImage, preferredView, bounds);
        this.zoneName = zoneName;
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

package fr.insa.gravityrocket.logic.level;

import fr.insa.gravityrocket.logic.EnumLevelState;

import java.awt.*;

public class ReachingZoneLevel extends Level
{

    private final Shape zone;

    public ReachingZoneLevel(Rectangle preferredView, Rectangle bounds, Shape zone) {
        super(preferredView, bounds);
        this.zone = zone;
    }

    @Override
    public void update(double dt) {
        super.update(dt);

        if (getZone().contains(getRocket().getXPos(), getRocket().getYPos())) {
            setLevelState(EnumLevelState.SUCCESS);
        }
    }

    public Shape getZone() {
        return zone;
    }

}

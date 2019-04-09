package fr.insa.gravityrocket.logic.entity;

import fr.insa.gravityrocket.logic.level.Level;

public class Asteroid extends Entity
{

    public Asteroid(Level level, double radius) {
        this(level, radius, 0, 0, 0);
    }

    public Asteroid(Level level, double radius, double posX, double posY, double rotation) {
        super(level, posX, posY, radius, radius, rotation);
    }

    @Override
    public double getMass() {
        //TODO
        return 0;
    }

    public double getRadius() {
        return getWidth() / 2;
    }

}

package fr.insa.gravityrocket.logic.entity;

import fr.insa.gravityrocket.logic.collision.CircularCollisionBox;
import fr.insa.gravityrocket.logic.collision.CollisionBox;
import fr.insa.gravityrocket.logic.level.Level;

public class Asteroid extends Entity
{

    private final EnumAsteroidVariant variant;

    public Asteroid(Level level, double radius, EnumAsteroidVariant variant) {
        this(level, radius, variant, 0, 0, 0);
    }

    public Asteroid(Level level, double radius, EnumAsteroidVariant variant, double posX, double posY, double rotation) {
        super(level, posX, posY, radius, radius, rotation);
        this.variant = variant;
    }

    @Override
    public CollisionBox computeCollisionBox() {
        return new CircularCollisionBox(getXPos(), getYPos(), getRadius());
    }

    @Override
    public boolean isAttractedBy(Entity entity) {
        return false;
    }

    @Override
    public double getMass() {
        return 5000;
    }

    public double getRadius() {
        return getWidth() / 2;
    }

    public EnumAsteroidVariant getVariant() {
        return variant;
    }

}

package fr.insa.gravityrocket.logic.entity.alien;

import fr.insa.gravityrocket.logic.level.Level;

public class WanderingAlien extends Alien
{

    private final double wanderingX;
    private final double wanderingY;

    public WanderingAlien(Level level, double shootingDistance, double wanderingX, double wanderingY) {
        this(level, shootingDistance, wanderingX, wanderingY, 0, 0, 0);
    }

    public WanderingAlien(Level level, double shootingDistance, double wanderingX, double wanderingY, double xPos, double yPos, double rotation) {
        super(level, shootingDistance, xPos, yPos, rotation);
        this.wanderingX = wanderingX;
        this.wanderingY = wanderingY;
    }

    @Override
    public void update(double dt) {
        setPos(wanderingX, wanderingY + Math.sin(System.currentTimeMillis() / 1000.0) * 10);
        super.update(dt);
    }

}

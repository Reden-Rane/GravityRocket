package fr.insa.gravityrocket.logic.entity.alien;

import fr.insa.gravityrocket.logic.entity.Planet;
import fr.insa.gravityrocket.logic.level.Level;

public class OrbitingAlien extends Alien
{

    private final Planet protectedPlanet;
    private       double orbitalAngle;
    private       double orbitalSpeed;
    private       double orbitalDistance;

    public OrbitingAlien(Level level, double shootingDistance, Planet protectedPlanet, double orbitalDistance, double orbitalSpeed) {
        this(level, shootingDistance, protectedPlanet, orbitalDistance, orbitalSpeed, 0, 0, 0);
    }

    public OrbitingAlien(Level level, double shootingDistance, Planet protectedPlanet, double orbitalDistance, double orbitalSpeed, double posX, double posY, double rotation) {
        super(level, shootingDistance, posX, posY, rotation);
        this.protectedPlanet = protectedPlanet;
        this.orbitalSpeed = orbitalSpeed;
        this.orbitalDistance = orbitalDistance;
    }

    @Override
    public void update(double dt) {
        this.orbitalAngle += this.orbitalSpeed * dt;
        double x = this.protectedPlanet.getXPos() + (this.protectedPlanet.getRadius() + this.orbitalDistance) * Math.cos(this.orbitalAngle);
        double y = this.protectedPlanet.getYPos() + (this.protectedPlanet.getRadius() + this.orbitalDistance) * Math.sin(this.orbitalAngle);
        setPos(x, y);
        setRotation(this.orbitalAngle + Math.PI / 2);

        super.update(dt);
    }

}

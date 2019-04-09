package fr.insa.gravityrocket.logic.entity;

import fr.insa.gravityrocket.logic.level.Level;

import java.awt.*;

public class Satellite extends Planet
{

    /**
     * La planète autour de laquelle le satellite orbite
     */
    private final Planet planet;

    /**
     * La distanceTo entre le satellite et la planète
     */
    private final double orbitalDistance;
    /**
     * La vitesse de rotation orbitale du satellite en rad/s
     */
    private final double orbitalSpeed;
    /**
     * L'angle du satellite dans la base locale de la planète en radians
     */
    private       double orbitalAngle;

    public Satellite(Level level, String name, Image texture, Planet planet, double orbitalDistance, double orbitalSpeed, double density, double radius) {
        super(level, name, texture, density, radius);
        this.orbitalDistance = orbitalDistance;
        this.orbitalSpeed = orbitalSpeed;
        this.planet = planet;
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        this.orbitalAngle += this.orbitalSpeed * dt;
        double x = this.planet.getXPos() + (this.planet.getRadius() + this.orbitalDistance) * Math.cos(this.orbitalAngle);
        double y = this.planet.getYPos() + (this.planet.getRadius() + this.orbitalDistance) * Math.sin(this.orbitalAngle);
        setPos(x, y);
    }

}

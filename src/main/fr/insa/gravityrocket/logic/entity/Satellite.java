package fr.insa.gravityrocket.logic.entity;

import fr.insa.gravityrocket.logic.level.Level;

import java.awt.*;

/**
 * Type d'astre orbitant autour d'un autre
 */
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

    public Satellite(Level level, String name, Image texture, double density, double radius, Planet planet, double orbitalDistance, double orbitalSpeed) {
        super(level, name, texture, density, radius);
        this.orbitalDistance = orbitalDistance;
        this.orbitalSpeed = orbitalSpeed;
        this.planet = planet;
        updateOrbitalPosition(0);
    }

    private void updateOrbitalPosition(double dt) {
        this.orbitalAngle += this.orbitalSpeed * dt;
        double x = this.planet.getXPos() + (this.planet.getRadius() + this.orbitalDistance) * Math.cos(this.orbitalAngle);
        double y = this.planet.getYPos() + (this.planet.getRadius() + this.orbitalDistance) * Math.sin(this.orbitalAngle);
        setPos(x, y);
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        updateOrbitalPosition(dt);
    }

    public double getOrbitalDistance() {
        return orbitalDistance;
    }

    public double getOrbitalSpeed() {
        return orbitalSpeed;
    }

    public double getOrbitalAngle() {
        return orbitalAngle;
    }

    public void setOrbitalAngle(double orbitalAngle) {
        this.orbitalAngle = orbitalAngle;
    }

}

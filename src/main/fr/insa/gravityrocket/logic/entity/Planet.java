package fr.insa.gravityrocket.logic.entity;

import fr.insa.gravityrocket.logic.collision.CircularCollisionBox;
import fr.insa.gravityrocket.logic.collision.CollisionBox;
import fr.insa.gravityrocket.logic.level.Level;

import java.awt.*;

/**
 * Type d'entité définissant une planète
 */
public class Planet extends Entity
{

    /**
     * Le nom de la planète
     */
    private final String name;
    /**
     * La texture de la planète
     */
    private final Image  texture;
    /**
     * La densité de la planète en kg/m³
     */
    private final double density;

    public Planet(Level level, String name, Image texture, double density, double radius) {
        this(level, name, texture, density, radius, 0, 0);
    }

    public Planet(Level level, String name, Image texture, double density, double radius, double posX, double posY) {
        super(level, posX, posY, radius * 2, radius * 2, 0);
        this.name = name;
        this.density = density;
        this.texture = texture;
    }

    public String getName() {
        return name;
    }

    public Image getTexture() {
        return texture;
    }

    @Override
    public CollisionBox computeCollisionBox() {
        return new CircularCollisionBox(getXPos(), getYPos(), getRadius());
    }

    @Override
    public double getMass() {
        return 4.0 / 3.0 * Math.PI * Math.pow(getRadius(), 3) * this.density;
    }

    @Override
    public boolean isAttractedBy(Entity entity) {
        return false;
    }

    public double getRadius() {
        return getWidth() / 2;
    }

}

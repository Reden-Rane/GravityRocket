package fr.insa.gravityrocket.logic.entity;

import fr.insa.gravityrocket.logic.collision.CircularCollisionBox;

import java.awt.*;

public class Planet extends Entity
{

    private final Image  texture;
    private final double density;

    public Planet(Image texture, double density, double radius) {
        this(texture, density, radius, 0, 0);
    }

    public Planet(Image texture, double density, double radius, double posX, double posY) {
        super(new CircularCollisionBox(radius), posX, posY, radius, radius, 0);
        this.density = density;
        this.texture = texture;
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.drawImage(this.texture, 0, 0, (int) getWidth(), (int) getHeight(), null);
    }

    @Override
    public double getMass() {
        return 4.0 / 3.0 * Math.PI * Math.pow(getRadius(), 3) * this.density;
    }

    public double getRadius() {
        return getWidth();
    }

}

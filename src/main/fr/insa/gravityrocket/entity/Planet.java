package fr.insa.gravityrocket.entity;

import fr.insa.gravityrocket.CircularCollisionBox;

import java.awt.*;

public class Planet extends Entity
{

    public Planet(double radius) {
        this(radius, 0, 0, 0);
    }

    public Planet(double radius, double posX, double posY, double rotation) {
        super(new CircularCollisionBox(radius), posX, posY, radius, radius, rotation);
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillOval(0, 0, (int) getWidth(), (int) getHeight());
    }

    @Override
    public double getMass() {
        return 5.972 * Math.pow(10, 15);
    }

}

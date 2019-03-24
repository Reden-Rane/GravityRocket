package fr.insa.gravityrocket.logic.entity;

import fr.insa.gravityrocket.logic.collision.CircularCollisionBox;

import java.awt.*;

public class Asteroid extends Entity
{

    public Asteroid(double radius) {
        this(radius, 0, 0, 0);
    }

    public Asteroid(double radius, double posX, double posY, double rotation) {
        super(new CircularCollisionBox(radius), posX, posY, 32, 32, rotation);
    }

    @Override
    public void render(Graphics2D g2d) {

    }

    @Override
    public double getMass() {
        //TODO
        return 0;
    }

}

package fr.insa.gravityrocket.model.entity;

import java.awt.*;

public class Asteroid extends Entity
{
    public Asteroid()
    {
        this(0, 0, 0);
    }

    public Asteroid(double posX, double posY, double rotation)
    {
        super(posX, posY, 32, 32, rotation);
    }

    @Override
    public void render(Graphics2D g2d)
    {

    }

    @Override
    public double getMass()
    {
        //TODO
        return 0;
    }
}

package fr.insa.gravityrocket.model.entity;

import java.awt.*;

public class Planet extends Entity
{
    public Planet(double radius)
    {
        this(radius, 0, 0, 0);
    }

    public Planet(double radius, double posX, double posY, double rotation)
    {
        super(posX, posY, radius, radius, rotation);
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

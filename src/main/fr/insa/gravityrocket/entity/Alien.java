package fr.insa.gravityrocket.entity;

import java.awt.*;

public class Alien extends Entity implements IDestroyable
{
    public Alien()
    {
        this(0, 0, 0);
    }

    public Alien(double posX, double posY, double rotation)
    {
        super(posX, posY, 16, 16, rotation);
    }

    @Override
    public void update(double dt)
    {
        super.update(dt);
        //TODO
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

    @Override
    public boolean isDestroyed()
    {
        return false;
    }
}
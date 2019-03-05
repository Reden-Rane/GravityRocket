package fr.insa.gravityrocket.model.entity;

import java.awt.*;

public class Alien extends Entity implements IDestroyable
{
    public Alien(double mass)
    {
        super(mass);
    }

    public Alien(double posX, double posY, double rotation, double mass)
    {
        super(posX, posY, rotation, mass);
    }

    @Override
    public void update(double dt)
    {
        super.update(dt);
        //TODO
    }

    @Override
    public void render(Graphics g)
    {

    }

    @Override
    public boolean isDestroyed()
    {
        return false;
    }
}
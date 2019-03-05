package fr.insa.gravityrocket.model.entity;

import java.awt.*;

public class Rocket extends Entity implements IDestroyable
{
    private int life;
    private FuelTank tank;
    private Reactor reactor;

    public Rocket()
    {
        this(0, 0, 0);
    }

    public Rocket(double posX, double posY, double rotation)
    {
        super(posX, posY, rotation, 10000);
        this.life = 10;
    }

    @Override
    public void update(double dt)
    {
        super.update(dt);

    }

    @Override
    public void render(Graphics g)
    {

    }

    @Override
    public boolean isDestroyed()
    {
        return life <= 0;
    }

    public FuelTank getTank()
    {
        return tank;
    }

    public void setTank(FuelTank tank)
    {
        this.tank = tank;
    }

    public Reactor getReactor()
    {
        return reactor;
    }

    public void setReactor(Reactor reactor)
    {
        this.reactor = reactor;
    }

    @Override
    public double getMass()
    {
        return super.getMass() + getTank().getMass() + getReactor().getMass();
    }
}
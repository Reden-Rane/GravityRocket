package fr.insa.gravityrocket.model.entity;

import fr.insa.gravityrocket.ImageHelper;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Rocket extends Entity implements IDestroyable
{
    private static Image ROCKET_IMAGE = ImageHelper.loadImage("/rocket.png");
    private static Image FLAME_IMAGE = ImageHelper.loadImage("/flame.png");

    private int life;
    private FuelTank tank;
    private Reactor reactor;

    public Rocket()
    {
        this(0, 0, 0);
    }

    public Rocket(FuelTank tank, Reactor reactor)
    {
        this(0, 0, 0);
        this.tank = tank;
        this.reactor = reactor;
    }

    public Rocket(double posX, double posY, double rotation)
    {
        super(posX, posY, 150, 360, rotation);
        this.life = 10;
    }

    @Override
    public void update(double dt)
    {
        super.update(dt);

    }

    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            getReactor().setActive(true);
        }
    }

    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            getReactor().setActive(false);
        }
    }

    @Override
    public void render(Graphics2D g2d)
    {
        g2d.drawImage(ROCKET_IMAGE, 0, 0, (int) getWidth(), (int) getHeight(), null);

        if (getReactor().isActive() && !getTank().isEmpty())
        {
            g2d.drawImage(FLAME_IMAGE, (int) (getWidth() / 2.0), (int) getHeight(), (int) (getWidth() * 2 / 3.0), (int) (getHeight() * 2 / 3.0), null);
        }
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
        return 10000 + getTank().getMass() + getReactor().getMass();
    }
}
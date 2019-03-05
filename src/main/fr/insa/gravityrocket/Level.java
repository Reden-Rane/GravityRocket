package fr.insa.gravityrocket;

import fr.insa.gravityrocket.model.entity.Entity;
import fr.insa.gravityrocket.model.entity.IDestroyable;
import fr.insa.gravityrocket.model.entity.Rocket;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Level
{
    private final Set<Entity> entitySet = new HashSet<>();
    private Rocket rocket;

    public Level()
    {

    }

    public void gameOver()
    {

    }

    public void victory()
    {

    }

    public void addEntity(Entity entity)
    {
        this.entitySet.add(entity);
    }

    public void update(double dt)
    {
        Iterator<Entity> iterator = entitySet.iterator();

        while (iterator.hasNext())
        {
            Entity entity = iterator.next();

            if (entity instanceof IDestroyable && ((IDestroyable) entity).isDestroyed())
            {
                iterator.remove();
                continue;
            }

            entity.update(dt);
        }
    }

    public void render(Graphics2D g2d)
    {
        for (Entity entity : entitySet)
        {
            g2d.translate((int) entity.getPosX(), (int) entity.getPosY());
            entity.render(g2d);
            g2d.translate((int) -entity.getPosX(), (int) -entity.getPosY());
        }
    }

    public Rocket getRocket()
    {
        return rocket;
    }

    public void setRocket(Rocket rocket)
    {
        this.entitySet.remove(this.rocket);
        this.rocket = rocket;
        this.addEntity(rocket);
    }
}
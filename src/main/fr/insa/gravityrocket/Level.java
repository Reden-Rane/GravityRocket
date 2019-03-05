package fr.insa.gravityrocket;

import fr.insa.gravityrocket.model.entity.Entity;
import fr.insa.gravityrocket.model.entity.IDestroyable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Level
{
    private final Set<Entity> entitySet = new HashSet<>();

    public Level()
    {

    }

    public void gameOver()
    {

    }

    public void victory()
    {

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

}
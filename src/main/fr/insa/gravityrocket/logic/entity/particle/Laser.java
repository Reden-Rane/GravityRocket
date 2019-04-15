package fr.insa.gravityrocket.logic.entity.particle;

import fr.insa.gravityrocket.logic.entity.Entity;
import fr.insa.gravityrocket.logic.entity.alien.Alien;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;
import fr.insa.gravityrocket.logic.level.Level;

public class Laser extends Particle
{

    public Laser(Level level, double width, double height, double longevity) {
        super(level, width, height, longevity);
    }

    @Override
    public void onCollisionWith(Entity entity) {

        if (!(entity instanceof Alien) && !(entity instanceof Laser)) {
            this.requestRemove();
        }
    }

    @Override
    public boolean isAttractedBy(Entity entity) {
        return entity instanceof Rocket;
    }

}

package fr.insa.gravityrocket.logic.entity.particle;

import fr.insa.gravityrocket.logic.entity.Entity;
import fr.insa.gravityrocket.logic.level.Level;

public class Explosion extends Particle
{

    public Explosion(Level level, double width, double height) {
        super(level, width, height, 0.5);
    }

    @Override
    public boolean isAttractedBy(Entity entity) {
        return false;
    }

}

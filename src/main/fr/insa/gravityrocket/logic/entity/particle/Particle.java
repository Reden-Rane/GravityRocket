package fr.insa.gravityrocket.logic.entity.particle;

import fr.insa.gravityrocket.logic.entity.Entity;
import fr.insa.gravityrocket.logic.level.Level;

/**
 * Particule ayant une durée de vie
 */
public abstract class Particle extends Entity
{

    /**
     * Durée de vie de la particule
     */
    private final double longevity;
    /**
     * Age de la particule, permettant de savoir si elle doit disparaitre
     */
    private       double age;

    public Particle(Level level, double width, double height, double longevity) {
        super(level, width, height);
        this.longevity = longevity;
    }

    @Override
    public void update(double dt) {
        super.update(dt);

        if (getAge() >= getLongevity()) {
            this.requestRemove();
        } else {
            this.age = Math.min(this.age + dt, this.longevity);
        }
    }

    public double getAge() {
        return age;
    }

    public double getLongevity() {
        return longevity;
    }

    @Override
    public double getMass() {
        return 1;
    }

}

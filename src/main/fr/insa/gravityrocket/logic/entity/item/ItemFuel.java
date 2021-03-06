package fr.insa.gravityrocket.logic.entity.item;

import fr.insa.gravityrocket.logic.collision.CircularCollisionBox;
import fr.insa.gravityrocket.logic.collision.CollisionBox;
import fr.insa.gravityrocket.logic.entity.Entity;
import fr.insa.gravityrocket.logic.entity.rocket.FuelTank;
import fr.insa.gravityrocket.logic.entity.rocket.Rocket;
import fr.insa.gravityrocket.logic.level.Level;

/**
 * Entité correspondant à du carburant
 */
public class ItemFuel extends Entity
{

    /**
     * La quantité de carburant que donnera l'item en étant ramassé
     */
    private final double fuelVolume;

    public ItemFuel(Level level, int size, double fuelVolume) {
        super(level, size, size);
        this.fuelVolume = fuelVolume;
    }

    @Override
    public CollisionBox computeCollisionBox() {
        return new CircularCollisionBox(getXPos(), getYPos(), getWidth() / 2);
    }

    @Override
    public void onCollisionWith(Entity entity) {
        if (entity instanceof Rocket) {
            //On remplit le réservoir de la fusée avec le volume de cet item
            ((Rocket) entity).getTank().addFuel(this.fuelVolume);
            this.requestRemove();
        }
    }

    @Override
    public boolean isAttractedBy(Entity entity) {
        return false;
    }

    @Override
    public double getMass() {
        return FuelTank.FUEL_DENSITY * this.fuelVolume + 100;
    }

}

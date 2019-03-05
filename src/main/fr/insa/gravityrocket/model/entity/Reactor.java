package fr.insa.gravityrocket.model.entity;

public class Reactor
{
    /**
     * Consommation en fuel du réacteur en litre/ticks bouh
     */
    private final double consumption;
    /**
     * La force de propulsion en N du réacteur
     */
    private final double propulsionForce;

    private boolean isActive;

    public Reactor(double consumption, double propulsionForce)
    {
        this.consumption = consumption;
        this.propulsionForce = propulsionForce;
    }

    public double getConsumption()
    {
        return consumption;
    }

    public double getPropulsionForce()
    {
        return propulsionForce;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean active)
    {
        isActive = active;
    }

    public double getMass()
    {
        return 1000;
    }

}
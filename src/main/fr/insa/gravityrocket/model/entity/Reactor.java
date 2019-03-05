package fr.insa.gravityrocket.model.entity;

public class Reactor
{
    /**
     * Consommation en fuel du réacteur en litre/ticks
     */
    private final double consumption;
    /**
     * La force de propulsion en N du réacteur
     */
    private final double propulsionForce;

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

    public double getMass()
    {
        return 1000;
    }

}